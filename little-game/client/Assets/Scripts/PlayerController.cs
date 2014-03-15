using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Requests;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;

public class PlayerController : MonoBehaviour
{
	
		private SmartFox server;

		void Start ()
		{
				server = SmartFoxConnection.Connection;
		}

		void OnCollisionEnter2D (Collision2D other)
		{
				Debug.Log ("Player collision detected: " + other);
				SendCollisionEvent ();
		}

		void OnTriggerEnter2D (Collider2D other)
		{
				Debug.Log ("Player trigger detected: " + other);
				SendCollisionEvent ();
		}
	
		private void SendCollisionEvent ()
		{
				server.Send (new ExtensionRequest ("collision", new SFSObject (), server.LastJoinedRoom));
		}

}