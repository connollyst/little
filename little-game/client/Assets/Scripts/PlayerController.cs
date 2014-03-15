using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Requests;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;

public class PlayerController : MonoBehaviour
{
	
		private SmartFox server;
		private Vector2 velocity;

		public string UUID{ get; set; }

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

		public void Position (float x, float y)
		{
				transform.position = new Vector2 (x, y);
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

		private void UpdateVelocity ()
		{
				velocity = new Vector2 (speed * Mathf.Cos (direction), speed * Mathf.Sin (direction));
				rigidbody2D.velocity = velocity;
		}

		private void SendCollisionEvent ()
		{
				server.Send (new ExtensionRequest ("collision", new SFSObject (), server.LastJoinedRoom));
		}

}