package ChatApp;

/**
* ChatApp/ChatHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 25 augusti 2015 kl 23:57 CEST
*/

public final class ChatHolder implements org.omg.CORBA.portable.Streamable
{
  public ChatApp.Chat value = null;

  public ChatHolder ()
  {
  }

  public ChatHolder (ChatApp.Chat initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ChatApp.ChatHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ChatApp.ChatHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ChatApp.ChatHelper.type ();
  }

}
