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
		private SmartFox server;
		private Dictionary<string, GameObject> myMobs = new Dictionary<string, GameObject> ();
		private Dictionary<string, GameObject> items = new Dictionary<string, GameObject> ();

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
		}

		public void OnConnectionLost (BaseEvent evt)
		{
				// Reset all internal states so we kick back to login screen
				server.RemoveAllEventListeners ();
				Application.LoadLevel ("Connector");
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				ISFSObject data = (SFSObject)evt.Params ["params"];
				if (cmd == "SetItem") {
						SetItem (data);
				} else if (cmd == "SetPlayer") {
						SetPlayer (data);
				} else  if (cmd == "UpdatePlayer") {
						UpdatePlayer (data);
				}
		}

		private void SetItem (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				if (!items.ContainsKey (id)) {
						items.Add (id, GameObject.Instantiate (foodModel) as GameObject);
				}
				GameObject item = items [id];
				Controller controller = item.GetComponent<Controller> ();
				controller.UUID = id;
				controller.Position (x, y);
		}

		private void SetPlayer (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				float x = data.GetFloat ("x");
				float y = data.GetFloat ("y");
				int speed = data.GetInt ("s") / 10;
				int direction = data.GetInt ("d");
				if (!myMobs.ContainsKey (id)) {
						myMobs.Add (id, GameObject.Instantiate (playerModel) as GameObject);
				}
				GameObject mob = myMobs [id];
				PlayerController controller = mob.GetComponent<PlayerController> ();
				controller.UUID = id;
				controller.Speed = speed;
				controller.Direction = direction;
				controller.Position (x, y);
		}
	
		private void UpdatePlayer (ISFSObject data)
		{
				string id = data.GetUtfString ("id");
				int speed = data.GetInt ("s") / 10;
				int direction = data.GetInt ("d");
				GameObject mob = myMobs [id];
				PlayerController controller = mob.GetComponent<PlayerController> ();
				controller.Speed = speed;
				controller.Direction = direction;
		}
	
}
