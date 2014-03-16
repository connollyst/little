using UnityEngine;

using System.Collections;
using System.Collections.Generic;

using Sfs2X;
using Sfs2X.Core;
using Sfs2X.Entities;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;
using Sfs2X.Requests;
using Sfs2X.Requests.MMO;
using Sfs2X.Logging;

public class GameManager : MonoBehaviour
{

		public GameObject playerModel;
		public GameObject foodModel;
		private SmartFox server;
		private Dictionary<string, GameObject> myMobs = new Dictionary<string, GameObject> ();
		private Dictionary<string, GameObject> items = new Dictionary<string, GameObject> ();
		private Dictionary<string, GameObject> walls = new Dictionary<string, GameObject> ();
	
		void Start ()
		{
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				server = SmartFoxConnection.Connection;
				server.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				server.AddEventListener (SFSEvent.PROXIMITY_LIST_UPDATE, OnProximityListUpdate);
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

		public void OnProximityListUpdate (BaseEvent evt)
		{
				Debug.Log ("Proximity list updated");
				var addedUsers = (List<User>)evt.Params ["addedUsers"];
				var addedItems = (List<IMMOItem>)evt.Params ["addedItems"];
				var removedUsers = (List<User>)evt.Params ["removedUsers"];
				var removedItems = (List<IMMOItem>)evt.Params ["removedItems"];
				foreach (User user in addedUsers) {	
						Debug.Log ("Proximity user added: " + user);
				}
				foreach (User user in removedUsers) {
						Debug.Log ("Proximity user removed: " + user);
				}
				foreach (IMMOItem item in addedItems) {	
						string type = item.GetVariable ("type").GetStringValue ();
						if (type == "wall") {
								AddWall (item);
						} else if (type == "player") {
								AddPlayer (item);
						} else if (type == "entity") {
								AddItem (item);
						}
				}
				foreach (IMMOItem item in removedItems) {
						string type = item.GetVariable ("type").GetStringValue ();
						if (type == "player") {
								RemovePlayer (item);
						} else if (type == "entity") {
								RemoveItem (item);
						}
				}
		}
		
		private void AddWall (IMMOItem item)
		{
				int x = item.AOIEntryPoint.IntX;
				int y = item.AOIEntryPoint.IntY;
				int w = item.GetVariable ("w").GetIntValue ();
				int h = item.GetVariable ("h").GetIntValue ();
				string id = item.GetVariable ("uuid").GetStringValue ();
				if (!walls.ContainsKey (id)) {
						Debug.Log ("Creating a wall @ (" + x + "," + y + ") of " + w + "x" + h);
						walls.Add (id, GameObject.CreatePrimitive (PrimitiveType.Cube));
				}
				GameObject wall = walls [id];
				wall.transform.position = new Vector3 (x, y, 0);
				wall.transform.localScale = new Vector3 (w, h, 1);
		}
	
		private void AddItem (IMMOItem item)
		{
				float x = item.AOIEntryPoint.IntX;
				float y = item.AOIEntryPoint.IntY;
				string id = item.GetVariable ("uuid").GetStringValue ();
				if (!items.ContainsKey (id)) {
						items.Add (id, GameObject.Instantiate (foodModel) as GameObject);
				}
				GameObject gameItem = items [id];
				Controller controller = gameItem.GetComponent<Controller> ();
				controller.UUID = id;
				controller.Position (x, y);
		}

		private void RemoveItem (IMMOItem item)
		{
				string id = item.GetVariable ("uuid").GetStringValue ();
				Debug.Log ("Removing item " + id);
				items.Remove (id);
		}
		
		private void AddPlayer (IMMOItem item)
		{
				float x = item.AOIEntryPoint.IntX;
				float y = item.AOIEntryPoint.IntY;
				string id = item.GetVariable ("uuid").GetStringValue ();
				int speed = item.GetVariable ("s").GetIntValue ();
				int direction = item.GetVariable ("d").GetIntValue ();
				if (!myMobs.ContainsKey (id)) {
						myMobs.Add (id, GameObject.Instantiate (playerModel) as GameObject);
				}
				GameObject mob = myMobs [id];
				PlayerController controller = mob.GetComponent<PlayerController> ();
				controller.UUID = id;
				//controller.Speed = speed;
				//controller.Direction = direction;
				controller.Position (x, y);
		}
	
		private void RemovePlayer (IMMOItem item)
		{
				string id = item.GetVariable ("uuid").GetStringValue ();
				Debug.Log ("Removing item " + id);
				myMobs.Remove (id);
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
