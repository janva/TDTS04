package ChatApp;


/**
* ChatApp/ChatCallbackOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 24 augusti 2015 kl 20:44 CEST
*/

public interface ChatCallbackOperations 
{
  void callback (String message);
  void update (String message);
  void announceWin (short x);
} // interface ChatCallbackOperations
