using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Requests;
using Sfs2X.Entities.Data;
using Sfs2X.Entities.Variables;

public class FoodController : MonoBehaviour
{

		public string UUID { get; set; }
	
		public void Position (float x, float y)
		{
				transform.position = new Vector2 (x, y);
		}

}