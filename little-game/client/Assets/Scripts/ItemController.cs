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

public class ItemController : MonoBehaviour
{
	
		public GameObject foodModel;

		private SmartFox server;
		private Dictionary<string, GameObject> items = new Dictionary<string, GameObject> ();

		void Start ()
		{
				if (!SmartFoxConnection.IsInitialized) {
						Application.LoadLevel ("Connector");
						return;
				}
				server = SmartFoxConnection.Connection;
				server.AddEventListener (SFSEvent.EXTENSION_RESPONSE, OnExtensionResponse);
		}
	
		void FixedUpdate ()
		{
				if (server != null) {
						server.ProcessEvents ();
				}
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				ISFSObject dt = (SFSObject)evt.Params ["params"];
				if (cmd == "spawnItem") {
						SpawnItem (dt);
				}
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

}
