Little Game Server
==================

The little game server is a SmartFoxServer project which maintains the state of
 the simulation, given input from each of the clients.

Note that no physics is evaluated on the server. Each client runs it's own
 physics simulation using the state of each of the entities (eg: speed,
 direction), informing the server of any events. In response to these events,
 the server evaluates any appropriate _little code_ and notifies the clients of
 any change to state.