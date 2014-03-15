using UnityEngine;
using System.Collections;

public class WallController : MonoBehaviour
{

		void OnCollisionEnter2D (Collision2D other)
		{
				Debug.Log ("Wall collision detected.");
		}

}