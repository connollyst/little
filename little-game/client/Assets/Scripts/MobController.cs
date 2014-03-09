using UnityEngine;
using System.Collections;

public class MobController : MonoBehaviour
{

		private float maxSpeed = 10f;
		private Animator anim;

		public bool MovementDirty { get; set; }

		void Start ()
		{
				anim = GetComponent<Animator> ();
		}

		void FixedUpdate ()
		{
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
