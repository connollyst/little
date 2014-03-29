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
				server.AddEventListener (SFSEvent.MMOITEM_VARIABLES_UPDATE, OnItemUpdate);
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
						Debug.Log ("Proximity item added: " + item);
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
						Debug.Log ("Proximity item removed: " + item);
						string type = item.GetVariable ("type").GetStringValue ();
						if (type == "player") {
								RemovePlayer (item);
						} else if (type == "entity") {
								RemoveItem (item);
						}
				}
		}

		public void OnItemUpdate (BaseEvent evt)
		{
				var changedVars = (List<string>)evt.Params ["changedVars"];
				var item = (IMMOItem)evt.Params ["mmoItem"];
				// Check if the MMOItem was moved
				if (changedVars.Contains ("x") || changedVars.Contains ("y")) {
						string type = item.GetVariable ("type").GetStringValue ();
						string id = item.GetVariable ("id").GetStringValue ();
						float x = (float)item.GetVariable ("x").GetDoubleValue ();
						float y = (float)item.GetVariable ("y").GetDoubleValue ();
						Controller controller = GetController (evt);
						if (controller != null) {
								controller.Position (x, y);
						} else {
								Debug.Log ("Cannot process update for unknown item: " + item);
						}
				}
		}

		private Controller GetController (BaseEvent evt)
		{
				var item = (IMMOItem)evt.Params ["mmoItem"];
				string id = item.GetVariable ("id").GetStringValue ();
				string type = item.GetVariable ("type").GetStringValue ();
				if (type == "wall") {
						return GetWallController (id);
				} else if (type == "player") {
						return GetPlayerController (id);
				} else if (type == "entity") {
						return GetItemController (id);
				} else {
						throw new System.ArgumentException ("Unrecognized type: " + type);
				}
		}

		private Controller GetWallController (string id)
		{
				if (walls.ContainsKey (id)) {
						return walls [id].GetComponent<Controller> ();
				} else {
						return null;
				}
		}

		private Controller GetItemController (string id)
		{
				if (items.ContainsKey (id)) {
						return items [id].GetComponent<Controller> ();
				} else {
						return null;
				}
		}

		private Controller GetPlayerController (string id)
		{
				if (myMobs.ContainsKey (id)) {
						return myMobs [id].GetComponent<Controller> ();
				} else {
						return null;
				}
		}

		private void AddWall (IMMOItem item)
		{
				float x = item.AOIEntryPoint.FloatX;
				float y = item.AOIEntryPoint.FloatY;
				int w = item.GetVariable ("w").GetIntValue ();
				int h = item.GetVariable ("h").GetIntValue ();
				string id = item.GetVariable ("id").GetStringValue ();
				if (!walls.ContainsKey (id)) {
						GameObject obj = GameObject.CreatePrimitive (PrimitiveType.Cube);
						obj.AddComponent<Controller> ();
						walls.Add (id, obj);
				}
				GameObject wall = walls [id];
				wall.transform.position = new Vector3 (x, y, 0);
				wall.transform.localScale = new Vector3 (w, h, 1);
		}

		private void AddItem (IMMOItem item)
		{
				float x = item.AOIEntryPoint.FloatX;
				float y = item.AOIEntryPoint.FloatY;
				string id = item.GetVariable ("id").GetStringValue ();
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
				string id = item.GetVariable ("id").GetStringValue ();
				Debug.Log ("Removing item " + id);
				items.Remove (id);
		}

		private void AddPlayer (IMMOItem item)
		{
				float x = item.AOIEntryPoint.FloatX;
				float y = item.AOIEntryPoint.FloatY;
				string id = item.GetVariable ("id").GetStringValue ();
				int speed = item.GetVariable ("s").GetIntValue ();
				int direction = item.GetVariable ("d").GetIntValue ();
				if (!myMobs.ContainsKey (id)) {
						myMobs.Add (id, GameObject.Instantiate (playerModel) as GameObject);
				}
				GameObject mob = myMobs [id];
				PlayerController controller = mob.GetComponent<PlayerController> ();
				controller.UUID = id;
				// controller.Speed = speed;
				// controller.Direction = direction;
				controller.Position (x, y);
		}

		private void RemovePlayer (IMMOItem item)
		{
				string id = item.GetVariable ("id").GetStringValue ();
				Debug.Log ("Removing player " + id);
				myMobs.Remove (id);
		}

}
