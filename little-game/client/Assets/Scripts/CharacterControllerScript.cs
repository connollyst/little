using UnityEngine;
using System.Collections;

public class CharacterControllerScript : MonoBehaviour
{

		private float maxSpeed = 10f;
		private bool facingRight = true;
		private Animator anim;

		// Use this for initialization
		void Start ()
		{
				anim = GetComponent<Animator> ();
		}
	
		// Update is called once per frame
		void FixedUpdate ()
		{
				float move = Input.GetAxis ("Horizontal");
				anim.SetFloat ("Speed", Mathf.Abs (move));
				rigidbody2D.velocity = new Vector2 (move * maxSpeed, rigidbody2D.velocity.y);
		}

}
