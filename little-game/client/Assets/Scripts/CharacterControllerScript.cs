using UnityEngine;
using System.Collections;

using Sfs2X;

public class CharacterControllerScript : MonoBehaviour
{
		public string serverName = "127.0.0.1";
		public int serverPort = 9933;
		private float maxSpeed = 10f;
		private Animator anim;
		private SmartFox smartFox;

		void Start ()
		{
				anim = GetComponent<Animator> ();
				smartFox = SmartFoxConnection.Connection;
				// TODO assert connection
		}

		void FixedUpdate ()
		{
				smartFox.ProcessEvents ();
				float move = Input.GetAxis ("Horizontal");
				anim.SetFloat ("Speed", Mathf.Abs (move));
				rigidbody2D.velocity = new Vector2 (move * maxSpeed, rigidbody2D.velocity.y);
		}


}
