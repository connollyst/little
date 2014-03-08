using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Core;
using Sfs2X.Requests;

public class ConnectionController : MonoBehaviour
{

		public string serverName = "127.0.0.1";
		public int serverPort = 9933;
		private SmartFox smartFox;

		void Start ()
		{
				SetMessage ("Loading..");
				smartFox = new SmartFox (true);
				Debug.Log ("API Version: " + smartFox.Version);
				smartFox.AddEventListener (SFSEvent.CONNECTION, OnConnection);
				smartFox.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				smartFox.AddEventListener (SFSEvent.LOGIN, OnLogin);
				smartFox.AddEventListener (SFSEvent.LOGIN_ERROR, OnLoginError);
				smartFox.AddEventListener (SFSEvent.ROOM_JOIN, OnRoomJoin);
				SetMessage ("Connecting..");
				smartFox.Connect (serverName, serverPort);
		}

		void FixedUpdate ()
		{
				smartFox.ProcessEvents ();
		}

		public void OnConnection (BaseEvent evt)
		{
				bool success = (bool)evt.Params ["success"];
				if (success) {
						OnConnectionSuccess (evt);
				} else {
						OnConnectionFailure (evt);
				}
		}

		private void OnConnectionSuccess (BaseEvent evt)
		{
				SmartFoxConnection.Connection = smartFox;
				SetMessage ("Connected to server, logging in..");
				smartFox.Send (new LoginRequest ("", "", "little"));
		}

		private void OnConnectionFailure (BaseEvent evt)
		{
				string error = (string)evt.Params ["errorMessage"];
				SetError ("Connection to server failed: " + error);
		}

		public void OnConnectionLost (BaseEvent evt)
		{
				string reason = (string)evt.Params ["reason"];
				SetMessage ("Connection to server was lost: " + reason);
		}
	
		public void OnLogin (BaseEvent evt)
		{
				SetMessage ("Logged in to server, joining room..");
				smartFox.Send (new JoinRoomRequest ("LittleTest"));
		}
	
		public void OnLoginError (BaseEvent evt)
		{
				string error = (string)evt.Params ["errorMessage"];
				SetError ("Login to server failed: " + error);
		}
	
		public void OnRoomJoin (BaseEvent evt)
		{
				SetMessage ("Joined room successfully");
				smartFox.RemoveAllEventListeners ();
				Application.LoadLevel ("scene1");
		}

		private void SetMessage (string text)
		{
				Debug.Log (text);
				guiText.text = text;
		}

		private void SetError (string text)
		{
				Debug.LogError (text);
				guiText.text = text;
		}
}
