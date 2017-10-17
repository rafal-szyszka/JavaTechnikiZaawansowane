# Diffie-Hellman Chat
This a simple distributed application that simulates chat. All messages are being encryped using Diffie-Hellman protocol.

### Server application 
By server I mean a part of application that receives message from client and sends it to all users that are registered to the app.
Server interface is located in:
```
it.szyszka.diffiehellmanschat.core.server.ChatServer
```
and its implementation is in:
```
it.szyszka.diffiehellmanschat.core.server.DiffieHellmansChatServer
```
Server application needs to receive 2 arguments to start properly:
1. Port number that will be used to create ```Registry```
2. A unique name that application will be ```bind```ed to the ```Registry``` 

```security.policy``` file is necces

### Client application 
Here are two options.
1. Option one runs on terminal
```main``` function to this option is located in ```it.szyszka.diffiehellmanschat.core.client.DiffieHellmansChatClient```
when started it will ask few questions
```
Enter server port number: *9000*
Enter server name: *myServer*
Enter your nickname: *myNickname*
```
These informations are needed to find server in ```Registry``` and request it to register new ```ChatClient```.
When all this is done, you can run second client with unique nickname, and you can type with yourself.

2. Option two has a simple GUI made in JavaFX
To start GUI client just start from ```it.szyszka.diffiehellmanschat.gui.client.ClientFx```

*hint:* send "q" to leave the chat ;)
