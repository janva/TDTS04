package ChatApp;


/**
* ChatApp/ChatCallbackPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 24 augusti 2015 kl 19:55 CEST
*/

public abstract class ChatCallbackPOA extends org.omg.PortableServer.Servant
 implements ChatApp.ChatCallbackOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("callback", new java.lang.Integer (0));
    _methods.put ("update", new java.lang.Integer (1));
    _methods.put ("announceWin", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ChatApp/ChatCallback/callback
       {
         String message = in.read_string ();
         this.callback (message);
         out = $rh.createReply();
         break;
       }

       case 1:  // ChatApp/ChatCallback/update
       {
         String message = in.read_string ();
         this.update (message);
         out = $rh.createReply();
         break;
       }

       case 2:  // ChatApp/ChatCallback/announceWin
       {
         short x = in.read_short ();
         this.announceWin (x);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ChatApp/ChatCallback:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ChatCallback _this() 
  {
    return ChatCallbackHelper.narrow(
    super._this_object());
  }

  public ChatCallback _this(org.omg.CORBA.ORB orb) 
  {
    return ChatCallbackHelper.narrow(
    super._this_object(orb));
  }


} // class ChatCallbackPOA
