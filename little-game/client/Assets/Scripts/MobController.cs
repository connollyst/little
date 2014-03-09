using UnityEngine;
using System.Collections;

using Sfs2X;

public class MobController : MonoBehaviour
{

		private float maxSpeed = 10f;
		private Animator anim;
		private SmartFox smartFox;

		public bool MovementDirty { get; set; }

		void Start ()
		{
				anim = GetComponent<Animator> ();
				smartFox = SmartFoxConnection.Connection;
				if (smartFox == null) {
						Debug.LogError ("Not connected to server.");
				}
		}

		void FixedUpdate ()
		{
				if (smartFox != null) {
						smartFox.ProcessEvents ();
				}
				float y = Input.GetAxis ("Vertical");
				float x = Input.GetAxis ("Horizontal");
				if (y != 0 || x != 0) {
						MovementDirty = true;
				}
				anim.SetFloat ("Speed", Mathf.Abs (x + y));
				//rigidbody2D.velocity.y
				rigidbody2D.velocity = new Vector2 (x * maxSpeed, y * maxSpeed);
		}


}
