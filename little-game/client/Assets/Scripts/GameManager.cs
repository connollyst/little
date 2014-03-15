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

public class GameManager : MonoBehaviour
{

		public GameObject playerModel;
		public GameObject foodModel;
		public GameObject npcModel;
		private SmartFox server;
		private Dictionary<string, GameObject> myMobs = new Dictionary<string, GameObject> ();
		private Dictionary<string, Vector2> myMobVelocities = new Dictionary<string, Vector2> ();
		private Dictionary<string, GameObject> npcs = new Dictionary<string, GameObject> ();
		private Dictionary<string, GameObject> items = new Dictionary<string, GameObject> ();
		private Dictionary<SFSUser, GameObject> players = new Dictionary<SFSUser, GameObject> ();

		void Start ()
		{
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				server = SmartFoxConnection.Connection;
				server.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				server.AddEventListener (SFSEvent.EXTENSION_RESPONSE, OnExtensionResponse);
				server.Send (new ExtensionRequest ("ready", new SFSObject (), server.LastJoinedRoom));
		}
	
		void FixedUpdate ()
		{
				if (server != null) {
						server.ProcessEvents ();
				}
				foreach (KeyValuePair<string, GameObject> entry in myMobs) {
						string id = entry.Key;
						GameObject mob = entry.Value;
						Vector2 velocity = myMobVelocities [id];
						Debug.Log ("Applying velocity: " + velocity);
						mob.rigidbody2D.velocity = velocity;
				}
		}

		public void OnConnectionLost (BaseEvent evt)
		{
				// Reset all internal states so we kick back to login screen
				server.RemoveAllEventListeners ();
				// TODO can this be handled by the TimedUpdate?
				Application.LoadLevel ("Connector");
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				ISFSObject dt = (SFSObject)evt.Params ["params"];
				if (cmd == "spawnPlayer") {
						SpawnPlayer (dt);
				} else if (cmd == "spawnItem") {
						//SpawnItem (dt);
				} else if (cmd == "spawnNPC") {
						//SpawnNPC (dt);
				}
		}
	
		//----------------------------------------------------------
		// Private player helper methods
		//----------------------------------------------------------

		private void SpawnPlayer (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				int speed = data.GetInt ("s");
				int direction = data.GetInt ("d");
				Debug.Log ("Player @ " + x + " & " + y + " w/ speed=" + speed + " and direction=" + direction);
				Vector2 position = new Vector2 (x, y);
				Vector2 velocity = new Vector2 (speed * Mathf.Cos (direction), speed * Mathf.Sin (direction));
				Quaternion rotation = Quaternion.Euler (0, 0, 0);
				Debug.Log ("Player velocity: " + velocity);
				GameObject mob = GameObject.Instantiate (playerModel) as GameObject;
				mob.transform.position = position;
				mob.transform.rotation = rotation;
				mob.rigidbody2D.velocity = velocity;
				myMobs.Add (id, mob);
				myMobVelocities.Add (id, velocity);
		}
	
		private void SpawnItem (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				Debug.Log ("Item @ " + x + " & " + y);
				Vector2 position = new Vector2 (x, y);
				Quaternion rotation = Quaternion.Euler (0, 0, 0);
		
				GameObject item = GameObject.Instantiate (foodModel) as GameObject;
				item.transform.position = position;
				item.transform.rotation = rotation;
				items.Add (id, item);
		}
	
		private void SpawnNPC (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				Debug.Log ("NPC @ " + x + " & " + y);
				Vector2 position = new Vector2 (x, y);
				Quaternion rotation = Quaternion.Euler (0, 0, 0);
		
				GameObject npc = GameObject.Instantiate (npcModel) as GameObject;
				npc.transform.position = position;
				npc.transform.rotation = rotation;
				npcs.Add (id, npc);
		}

}
