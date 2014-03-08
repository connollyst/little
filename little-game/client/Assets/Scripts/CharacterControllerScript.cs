using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Core;
using Sfs2X.Requests;

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
				smartFox = new SmartFox (true);
				Debug.Log ("API Version: " + smartFox.Version);
		
				// Register callback delegate
				smartFox.AddEventListener (SFSEvent.CONNECTION, OnConnection);
				smartFox.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				smartFox.AddEventListener (SFSEvent.LOGIN, OnLogin);
				smartFox.AddEventListener (SFSEvent.LOGIN_ERROR, OnLoginError);
				smartFox.AddEventListener (SFSEvent.ROOM_JOIN, OnRoomJoin);
		
				smartFox.Connect (serverName, serverPort);
		}

		void FixedUpdate ()
		{
				smartFox.ProcessEvents ();
				float move = Input.GetAxis ("Horizontal");
				anim.SetFloat ("Speed", Mathf.Abs (move));
				rigidbody2D.velocity = new Vector2 (move * maxSpeed, rigidbody2D.velocity.y);
		}

		public void OnConnection (BaseEvent evt)
		{
				bool success = (bool)evt.Params ["success"];
				string error = (string)evt.Params ["errorMessage"];
				Debug.Log ("On Connection callback got: " + success + " (error : <" + error + ">)");
				if (success) {
						SmartFoxConnection.Connection = smartFox;
						Debug.Log ("Connection succesful, logging in..");
						smartFox.Send (new LoginRequest ("", "", "little"));
				} else {
						Debug.LogError ("Can't connect to server!");
				}
		}

		public void OnConnectionLost (BaseEvent evt)
		{
				Debug.Log ("Connection was lost, Reason: " + (string)evt.Params ["reason"]);
		}
	
		public void OnLogin (BaseEvent evt)
		{
				Debug.Log ("Logged in successfully, joining room..");
				smartFox.Send (new JoinRoomRequest ("LittleTest"));
		}
	
		public void OnLoginError (BaseEvent evt)
		{
				Debug.Log ("Login error: " + (string)evt.Params ["errorMessage"]);
		}

		public void OnRoomJoin (BaseEvent evt)
		{
				Debug.Log ("Joined room successfully");
		}

}
