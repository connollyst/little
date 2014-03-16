using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Requests;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;

public class PlayerController : Controller
{
	
		private SmartFox server;
		private Vector2 velocity;
		private int speed = 0;
		private int direction = 0;

		public int Speed {
				get { return speed; }
				set {
						speed = value;
						UpdateVelocity ();
				}
		}

		public int Direction {
				get { return direction; }
				set {
						direction = value;
						// TODO update transform.rotation
						UpdateVelocity ();
				}
		}

		void Start ()
		{
				server = SmartFoxConnection.Connection;
				UpdateVelocity ();
		}

		void FixedUpdate ()
		{
				rigidbody2D.velocity = velocity;
		}

		void OnCollisionEnter2D (Collision2D collision)
		{
				// HandleCollision (collision.gameObject);
		}

		void OnTriggerEnter2D (Collider2D other)
		{
				// HandleCollision (other.gameObject);
		}

		private void HandleCollision (GameObject other)
		{
				Debug.Log ("Player collided with " + other);
				Controller controller = other.GetComponent<Controller> ();
				if (controller == null) {
						Debug.LogError ("Player collided with GameObject without a controller: " + other);
						return;
				}
				string otherUUID = controller.UUID;
				if (otherUUID == null) {
						Debug.LogError ("Player collided with GameObject without a UUID: " + other);
						return;
				}
				SendCollisionEvent (otherUUID);
		}
	
		private void UpdateVelocity ()
		{
				velocity = new Vector2 (speed * Mathf.Cos (direction), speed * Mathf.Sin (direction));
		}

		private void SendCollisionEvent (string otherUUID)
		{
				SFSObject data = new SFSObject ();
				data.PutUtfString ("a", UUID);
				data.PutUtfString ("b", otherUUID);
				Debug.Log ("Reporting collision between " + UUID + " & " + otherUUID);
				server.Send (new ExtensionRequest ("collision", data, server.LastJoinedRoom));
		}

}