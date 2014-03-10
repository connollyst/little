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

public class GameController : MonoBehaviour
{

		//----------------------------------------------------------
		// Setup variables
		//----------------------------------------------------------
		public GameObject playerModel;
		public GameObject foodModel;
		public LogLevel logLevel = LogLevel.ERROR;

		// Internal / private variables
		private SmartFox smartFox;
		private GameObject localPlayer;
		private MobController localPlayerController;
		private Dictionary<SFSUser, GameObject> remotePlayers = new Dictionary<SFSUser, GameObject> ();
	
		//----------------------------------------------------------
		// Unity callbacks
		//----------------------------------------------------------
		void Start ()
		{
		
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				smartFox = SmartFoxConnection.Connection;
		
				// Register callback delegates
				smartFox.AddEventListener (SFSEvent.EXTENSION_RESPONSE, OnExtensionResponse);
				smartFox.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				smartFox.AddEventListener (SFSEvent.USER_VARIABLES_UPDATE, OnUserVariableUpdate);
				smartFox.AddEventListener (SFSEvent.USER_EXIT_ROOM, OnUserExitRoom);
				smartFox.AddEventListener (SFSEvent.USER_ENTER_ROOM, OnUserEnterRoom);
		
//				smartFox.AddLogListener (logLevel, OnDebugMessage);
		
				// Start this clients avatar and get cracking!
//		int numModel = UnityEngine.Random.Range(0, 10);
//		int numMaterial = UnityEngine.Random.Range(0, 10);
//		SpawnLocalPlayer(numModel, numMaterial);
				smartFox.Send (new ExtensionRequest ("ready", new SFSObject (), smartFox.LastJoinedRoom));
		}
	
		void FixedUpdate ()
		{
				if (smartFox != null) {
						smartFox.ProcessEvents ();
			
						// If we spawned a local player, send position if movement is dirty
						if (localPlayer != null && localPlayerController != null && localPlayerController.MovementDirty) {
								List<UserVariable> userVariables = new List<UserVariable> ();
								userVariables.Add (new SFSUserVariable ("x", (double)localPlayer.transform.position.x));
								userVariables.Add (new SFSUserVariable ("y", (double)localPlayer.transform.position.y));
								userVariables.Add (new SFSUserVariable ("z", (double)localPlayer.transform.position.z));
								userVariables.Add (new SFSUserVariable ("rot", (double)localPlayer.transform.rotation.eulerAngles.y));
								smartFox.Send (new SetUserVariablesRequest (userVariables));
								localPlayerController.MovementDirty = false;
						}
				}
		}
	
		void OnApplicationQuit ()
		{
				// Before leaving, lets notify the others about this client dropping out
				RemoveLocalPlayer ();
		}
	
		//----------------------------------------------------------
		// SmartFox callbacks
		//----------------------------------------------------------
	
		public void OnUserExitRoom (BaseEvent evt)
		{
				// Someone left - lets make certain they are removed if they didnt nicely send a remove command
				SFSUser user = (SFSUser)evt.Params ["user"];		
				RemoveRemotePlayer (user);
		}
	
		public void OnUserEnterRoom (BaseEvent evt)
		{
				// User joined - and we might be standing still (not sending position info). So lets send him our position info
				if (localPlayer != null) {
						List<UserVariable> userVariables = new List<UserVariable> ();
						userVariables.Add (new SFSUserVariable ("x", (double)localPlayer.transform.position.x));
						userVariables.Add (new SFSUserVariable ("y", (double)localPlayer.transform.position.y));
						userVariables.Add (new SFSUserVariable ("z", (double)localPlayer.transform.position.z));
						userVariables.Add (new SFSUserVariable ("rot", (double)localPlayer.transform.rotation.eulerAngles.y));
						userVariables.Add (new SFSUserVariable ("model", smartFox.MySelf.GetVariable ("model").GetIntValue ()));
						userVariables.Add (new SFSUserVariable ("mat", smartFox.MySelf.GetVariable ("mat").GetIntValue ()));
						smartFox.Send (new SetUserVariablesRequest (userVariables));
				}
		}
	
		public void OnConnectionLost (BaseEvent evt)
		{
				// Reset all internal states so we kick back to login screen
				smartFox.RemoveAllEventListeners ();
				Application.LoadLevel ("Connection");
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				ISFSObject dt = (SFSObject)evt.Params ["params"];
				if (cmd == "spawnPlayer") {
						SpawnPlayer (dt);
				} else if (cmd == "spawnItem") {
						SpawnItem (dt);
				} else {
						Debug.LogError ("Unrecognized command: " + cmd);
				}
		}

		private void SpawnPlayer (ISFSObject data)
		{
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				Debug.Log ("Player @ " + x + " & " + y);
				Vector2 position = new Vector2 (x, y);
				Quaternion rotation = Quaternion.Euler (0, 0, 0);
				SpawnLocalPlayer (position, rotation);
		}

		private void SpawnItem (ISFSObject data)
		{
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				Debug.Log ("Item @ " + x + " & " + y);
				Vector2 position = new Vector2 (x, y);
				Quaternion rotation = Quaternion.Euler (0, 0, 0);

				GameObject item = GameObject.Instantiate (foodModel) as GameObject;
				item.transform.position = position;
				item.transform.rotation = rotation;
		}
		
		public void OnUserVariableUpdate (BaseEvent evt)
		{
				// When user variable is updated on any client, then this callback is being received
				// This is where most of the magic happens
		
				ArrayList changedVars = (ArrayList)evt.Params ["changedVars"];
				SFSUser user = (SFSUser)evt.Params ["user"];
		
				if (user == smartFox.MySelf)
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
	
		private void SpawnLocalPlayer (Vector2 position, Quaternion rotation)
		{
				localPlayer = GameObject.Instantiate (playerModel) as GameObject;
				localPlayer.transform.position = position;
				localPlayer.transform.rotation = rotation;
		}
	
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
				smartFox.Send (new ObjectMessageRequest (obj, smartFox.LastJoinedRoom));
		}
	
		private void RemoveRemotePlayer (SFSUser user)
		{
				if (user == smartFox.MySelf)
						return;
		
				if (remotePlayers.ContainsKey (user)) {
						Destroy (remotePlayers [user]);
						remotePlayers.Remove (user);
				}
		}

}
