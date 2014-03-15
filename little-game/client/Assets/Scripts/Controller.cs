using UnityEngine;
using System.Collections;

public class Controller : MonoBehaviour
{

		public string UUID { get; set; }
	
		public void Position (float x, float y)
		{
				transform.position = new Vector2 (x, y);
		}

}