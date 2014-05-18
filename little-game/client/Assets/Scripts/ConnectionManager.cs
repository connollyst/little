using UnityEngine;
using System.Collections;

using Sfs2X;
using Sfs2X.Core;
using Sfs2X.Requests;
using Sfs2X.Entities;
using Sfs2X.Entities.Data;

public class ConnectionManager : MonoBehaviour
{

		private static string serverName = "127.0.0.1";
		private static int    serverPort = 9933;
		private static string password = "";
		private static string zone = "little";
		private static string gameScene = "Game";
		private SmartFox server;

		void Start ()
		{
				SetMessage ("Loading..");
				server = new SmartFox (true);
				server.AddEventListener (SFSEvent.CONNECTION, OnConnection);
				server.AddEventListener (SFSEvent.CONNECTION_LOST, OnConnectionLost);
				server.AddEventListener (SFSEvent.LOGIN, OnLogin);
				server.AddEventListener (SFSEvent.LOGIN_ERROR, OnLoginError);
				SetMessage ("Connecting..");
				if (Security.PrefetchSocketPolicy (serverName, serverPort, 200)) {
						server.Connect (serverName, serverPort);
				} else {
						SetError ("Failed to prefetch the webplayer socket security policy.");
				}
		}

		void FixedUpdate ()
		{
				server.ProcessEvents ();
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
				SmartFoxConnection.Connection = server;
				SetMessage ("Connected to server, logging in..");
				if (PreviewLabs.RequestParameters.HasKey ("username")) {
						string username = PreviewLabs.RequestParameters.GetValue ("username");
						server.Send (new LoginRequest (username, password, zone));
				} else {
						SetError ("Cannot log in without 'username'.");
				}
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
				SetMessage ("Logged in, starting game..");
				server.RemoveAllEventListeners ();
				Application.LoadLevel (gameScene);
		}
	
		public void OnLoginError (BaseEvent evt)
		{
				string error = (string)evt.Params ["errorMessage"];
				SetError ("Login to server failed: " + error);
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
