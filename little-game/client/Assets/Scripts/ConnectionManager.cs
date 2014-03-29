﻿using UnityEngine;
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
		private static string username = "";
		private static string password = "";
		private static string zone = "little";
		private static string room = "LittleTest";
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
				server.AddEventListener (SFSEvent.ROOM_JOIN, OnRoomJoin);
				server.AddEventListener (SFSEvent.EXTENSION_RESPONSE, OnExtensionResponse);
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
				server.Send (new LoginRequest (username, password, zone));
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
				SetMessage ("Logged in, joining room..");
				if (PreviewLabs.RequestParameters.HasKey ("ide_id")) {
						server.Send (new JoinRoomRequest (room));
				} else {
						SetError ("Cannot authenticate without 'ide_id'.");
				}
		}
	
		public void OnLoginError (BaseEvent evt)
		{
				string error = (string)evt.Params ["errorMessage"];
				SetError ("Login to server failed: " + error);
		}

		public void OnRoomJoin (BaseEvent evt)
		{
				SetMessage ("Joined room, connecting client..");
				SendAuthenticationRequest ();
		}

		private void SendAuthenticationRequest ()
		{
				Room room = server.LastJoinedRoom;
				string id = PreviewLabs.RequestParameters.GetValue ("ide_id");
				ISFSObject ideId = SFSObject.NewInstance ();
				ideId.PutUtfString ("ide_id", id);
				server.Send (new ExtensionRequest ("authIDE", ideId, room));
		}

		public void OnExtensionResponse (BaseEvent evt)
		{
				string cmd = (string)evt.Params ["cmd"];
				switch (cmd) {
				case "authIDE":
						SetMessage ("Authenticated, loading game..");
						OnAuthenticated ();
						break;
				default:
						SetError ("Unrecognized server command '" + cmd + "'.");
						break;
				}
		}

		public void OnAuthenticated ()
		{
				server.RemoveAllEventListeners ();
				Application.LoadLevel (gameScene);
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
