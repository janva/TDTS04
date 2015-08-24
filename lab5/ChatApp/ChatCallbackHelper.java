package ChatApp;


/**
* ChatApp/ChatCallbackHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 24 augusti 2015 kl 19:55 CEST
*/

abstract public class ChatCallbackHelper
{
  private static String  _id = "IDL:ChatApp/ChatCallback:1.0";

  public static void insert (org.omg.CORBA.Any a, ChatApp.ChatCallback that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ChatApp.ChatCallback extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (ChatApp.ChatCallbackHelper.id (), "ChatCallback");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ChatApp.ChatCallback read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ChatCallbackStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ChatApp.ChatCallback value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static ChatApp.ChatCallback narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof ChatApp.ChatCallback)
      return (ChatApp.ChatCallback)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      ChatApp._ChatCallbackStub stub = new ChatApp._ChatCallbackStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static ChatApp.ChatCallback unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof ChatApp.ChatCallback)
      return (ChatApp.ChatCallback)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      ChatApp._ChatCallbackStub stub = new ChatApp._ChatCallbackStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
