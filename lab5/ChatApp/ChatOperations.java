package ChatApp;


/**
* ChatApp/ChatOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 25 augusti 2015 kl 22:31 CEST
*/

public interface ChatOperations 
{
  String say (ChatApp.ChatCallback objref, String message);
  String list (ChatApp.ChatCallback objref);
  boolean join (ChatApp.ChatCallback objref, String user);
  void leave (ChatApp.ChatCallback objref, String user);
  String send (ChatApp.ChatCallback objref, String msg);

  //game interface we use same interface beacause we are lazy
  void mark (ChatApp.ChatCallback objref, short x, short y, short mark);
  void playGame (ChatApp.ChatCallback objref, String userName);
  void leaveGame (ChatApp.ChatCallback objref, String userName);
} // interface ChatOperations
