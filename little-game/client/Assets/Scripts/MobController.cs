using UnityEngine;
using System.Collections;

using Sfs2X;

public class MobController : MonoBehaviour
{

		private float maxSpeed = 10f;
		private Animator anim;
		private SmartFox smartFox;

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
				float move = Input.GetAxis ("Horizontal");
				anim.SetFloat ("Speed", Mathf.Abs (move));
				rigidbody2D.velocity = new Vector2 (move * maxSpeed, rigidbody2D.velocity.y);
		}


}
