using UnityEngine;
using System.Collections;

public class FoodController : MonoBehaviour
{

		void OnTriggerEnter2D (Collider2D other)
		{
				Debug.Log ("Food collision detected.");
		}

}