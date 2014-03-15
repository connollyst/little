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
		private string id;
		private int speed = 0;
		private int direction = 0;

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

		public void SetId (string id)
		{
				this.id = id;
		}

		public void SetPosition (float x, float y)
		{
				transform.position = new Vector2 (x, y);
		}

		public void SetSpeed (int speed)
		{
				this.speed = speed;
		}

		public void SetDirection (int direction)
		{
				this.direction = direction;
				// TODO update transform.rotation
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