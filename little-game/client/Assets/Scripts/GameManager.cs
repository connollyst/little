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

		void Start ()
		{
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				server = SmartFoxConnection.Connection;
				server.AddEventListener (SFSEvent.ROOM_VARIABLES_UPDATE, OnRoomVariablesUpdate);
				server.AddEventListener (SFSEvent.USER_VARIABLES_UPDATE, OnUserVariablesUpdate);
				server.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				server.AddEventListener (SFSEvent.EXTENSION_RESPONSE, OnExtensionResponse);
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

		public void OnUserVariablesUpdate (BaseEvent evt)
		{
				SFSUser user = (SFSUser)evt.Params ["user"];
				ArrayList changedVars = (ArrayList)evt.Params ["changedVars"];
				if (user == server.MySelf) {
						foreach (string changedVar in changedVars) {
								ISFSObject value = user.GetVariable (changedVar).GetSFSObjectValue ();
								Debug.Log ("MY user variable updated: " + changedVar + " => " + value);
								SetPlayer (value);
						}
				} else {
						foreach (string changedVar in changedVars) {
								Debug.Log ("OTHER user variable updated: " + changedVar);
						}
				}
		}

		public void OnRoomVariablesUpdate (BaseEvent evt)
		{
				Debug.Log ("Room variable updated");
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
						Debug.Log ("Proximity item added: " + item);
						AddItem (item);
				}
				foreach (IMMOItem item in removedItems) {
						Debug.Log ("Proximity item removed: " + item);
						RemoveItem (item);
				}
		}
		
		private void AddItem (IMMOItem item)
		{
				string id = item.GetVariable ("uuid").GetStringValue ();
				float x = item.AOIEntryPoint.IntX;
				float y = item.AOIEntryPoint.IntY;
				if (!items.ContainsKey (id)) {
						items.Add (id, GameObject.Instantiate (foodModel) as GameObject);
				}
				Debug.Log ("Adding item at (" + x + "," + y + ")");
				GameObject gameItem = items [id];
				Controller controller = gameItem.GetComponent<Controller> ();
				controller.UUID = id;
				controller.Position (x, y);
		}

		private void RemoveItem (IMMOItem item)
		{
				string id = item.GetVariable ("uuid").GetStringValue ();
				items.Remove (id);
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
