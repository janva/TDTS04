package ChatApp;

/**
* ChatApp/ChatCallbackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 24 augusti 2015 kl 20:44 CEST
*/

public final class ChatCallbackHolder implements org.omg.CORBA.portable.Streamable
{
  public ChatApp.ChatCallback value = null;

  public ChatCallbackHolder ()
  {
  }

  public ChatCallbackHolder (ChatApp.ChatCallback initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ChatApp.ChatCallbackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ChatApp.ChatCallbackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ChatApp.ChatCallbackHelper.type ();
  }

}
