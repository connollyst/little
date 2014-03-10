using UnityEngine;

using System.Collections;
using System.Collections.Generic;

using Sfs2X;
using Sfs2X.Core;
using Sfs2X.Entities;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;
using Sfs2X.Requests;
using Sfs2X.Logging;

public class PlayerController : MonoBehaviour
{

		public GameObject playerModel;
		private SmartFox server;
		private Dictionary<string, GameObject> myMobs = new Dictionary<string, GameObject> ();
		private Dictionary<SFSUser, GameObject> remotePlayers = new Dictionary<SFSUser, GameObject> ();

		void Start ()
		{
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				server = SmartFoxConnection.Connection;
				server.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				server.AddEventListener (SFSEvent.USER_VARIABLES_UPDATE, OnUserVariableUpdate);
				server.AddEventListener (SFSEvent.USER_ENTER_ROOM, OnUserEnterRoom);
				server.AddEventListener (SFSEvent.USER_EXIT_ROOM, OnUserExitRoom);
				server.Send (new ExtensionRequest ("ready", new SFSObject (), server.LastJoinedRoom));
		}
	
		void FixedUpdate ()
		{
				if (server != null) {
						server.ProcessEvents ();
				}
		}
	
		void OnApplicationQuit ()
		{
				// Before leaving, lets notify the others about this client dropping out
				RemoveLocalPlayer ();
		}
	
		public void OnUserExitRoom (BaseEvent evt)
		{
				SFSUser user = (SFSUser)evt.Params ["user"];		
				RemoveRemotePlayer (user);
		}
	
		public void OnUserEnterRoom (BaseEvent evt)
		{
				// Another user joined, lets send them our position info
				foreach (KeyValuePair<string, GameObject> entry in myMobs) {
						string id = entry.Key;
						GameObject myMob = entry.Value;
						List<UserVariable> userVariables = new List<UserVariable> ();
						userVariables.Add (new SFSUserVariable ("id", id));
						userVariables.Add (new SFSUserVariable ("x", (double)myMob.transform.position.x));
						userVariables.Add (new SFSUserVariable ("y", (double)myMob.transform.position.y));
						userVariables.Add (new SFSUserVariable ("rot", (double)myMob.transform.rotation.eulerAngles.y));
						server.Send (new SetUserVariablesRequest (userVariables));
				}
		}
	
		public void OnConnectionLost (BaseEvent evt)
		{
				// Reset all internal states so we kick back to login screen
				server.RemoveAllEventListeners ();
				// TODO can this be handled by the TimedUpdate?
				Application.LoadLevel ("Connection");
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				ISFSObject dt = (SFSObject)evt.Params ["params"];
				if (cmd == "spawnPlayer") {
						SpawnPlayer (dt);
				}
		}

		private void SpawnPlayer (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				Debug.Log ("Player @ " + x + " & " + y);
				Vector2 position = new Vector2 (x, y);
				Quaternion rotation = Quaternion.Euler (0, 0, 0);
				
				GameObject mob = GameObject.Instantiate (playerModel) as GameObject;
				mob.transform.position = position;
				mob.transform.rotation = rotation;
				myMobs.Add (id, mob);
		}

		public void OnUserVariableUpdate (BaseEvent evt)
		{
				// When user variable is updated on any client, then this callback is being received
				// This is where most of the magic happens
		
				ArrayList changedVars = (ArrayList)evt.Params ["changedVars"];
				SFSUser user = (SFSUser)evt.Params ["user"];
		
				if (user == server.MySelf)
						return;
		
				if (!remotePlayers.ContainsKey (user)) {
						// New client just started transmitting - lets create remote player
						Vector3 pos = new Vector3 (0, 1, 0);
						if (user.ContainsVariable ("x") && user.ContainsVariable ("y") && user.ContainsVariable ("z")) {
								pos.x = (float)user.GetVariable ("x").GetDoubleValue ();
								pos.y = (float)user.GetVariable ("y").GetDoubleValue ();
								pos.z = (float)user.GetVariable ("z").GetDoubleValue ();
						}
						float rotAngle = 0;
						if (user.ContainsVariable ("rot")) {
								rotAngle = (float)user.GetVariable ("rot").GetDoubleValue ();
						}
						SpawnRemotePlayer (user, pos, Quaternion.Euler (0, rotAngle, 0));
				}
		
				// Check if the remote user changed his position or rotation
				if (changedVars.Contains ("x") && changedVars.Contains ("y") && changedVars.Contains ("z") && changedVars.Contains ("rot")) {
						// Move the character to a new position...
						remotePlayers [user].GetComponent<SimpleRemoteInterpolation> ().SetTransform (
				new Vector3 ((float)user.GetVariable ("x").GetDoubleValue (), (float)user.GetVariable ("y").GetDoubleValue (), (float)user.GetVariable ("z").GetDoubleValue ()),
				Quaternion.Euler (0, (float)user.GetVariable ("rot").GetDoubleValue (), 0),
				true);
				}
				// Remote client got new name?
				if (changedVars.Contains ("name")) {
						remotePlayers [user].GetComponentInChildren<TextMesh> ().text = user.Name;
				}
				// Remote client selected new model?
				if (changedVars.Contains ("model")) {
						SpawnRemotePlayer (user, remotePlayers [user].transform.position, remotePlayers [user].transform.rotation);
				}
				// Remote client selected new material?
				if (changedVars.Contains ("mat")) {
//			remotePlayers[user].GetComponentInChildren<Renderer>().material = playerMaterials[ user.GetVariable("mat").GetIntValue() ];
				}
		}
	
		public void OnDebugMessage (BaseEvent evt)
		{
				string message = (string)evt.Params ["message"];
				Debug.Log (message);
		}
	
		//----------------------------------------------------------
		// Private player helper methods
		//----------------------------------------------------------

		private void SpawnRemotePlayer (SFSUser user, Vector3 position, Quaternion rotation)
		{
				// See if there already exists a model so we can destroy it first
				RemoveRemotePlayer (user);
		
				// Spawn the remote player model
				GameObject remotePlayer = GameObject.Instantiate (playerModel) as GameObject;
				remotePlayer.AddComponent<SimpleRemoteInterpolation> ();
				remotePlayer.GetComponent<SimpleRemoteInterpolation> ().SetTransform (position, rotation, false);
				remotePlayers.Add (user, remotePlayer);
		}
	
		private void RemoveLocalPlayer ()
		{
				// Someone dropped off the grid. Lets remove him
				SFSObject obj = new SFSObject ();
				obj.PutUtfString ("cmd", "rm");
				server.Send (new ObjectMessageRequest (obj, server.LastJoinedRoom));
		}
	
		private void RemoveRemotePlayer (SFSUser user)
		{
				if (user == server.MySelf)
						return;
		
				if (remotePlayers.ContainsKey (user)) {
						Destroy (remotePlayers [user]);
						remotePlayers.Remove (user);
				}
		}

}
