package ChatApp;


/**
* ChatApp/_ChatStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Chat.idl
* den 25 augusti 2015 kl 20:02 CEST
*/

public class _ChatStub extends org.omg.CORBA.portable.ObjectImpl implements ChatApp.Chat
{

  public String say (ChatApp.ChatCallback objref, String message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("say", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (message);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return say (objref, message        );
            } finally {
                _releaseReply ($in);
            }
  } // say

  public String list (ChatApp.ChatCallback objref)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("list", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return list (objref        );
            } finally {
                _releaseReply ($in);
            }
  } // list

  public boolean join (ChatApp.ChatCallback objref, String user)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("join", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (user);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return join (objref, user        );
            } finally {
                _releaseReply ($in);
            }
  } // join

  public void leave (ChatApp.ChatCallback objref, String user)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("leave", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (user);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                leave (objref, user        );
            } finally {
                _releaseReply ($in);
            }
  } // leave

  public String send (ChatApp.ChatCallback objref, String msg)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("send", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (msg);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return send (objref, msg        );
            } finally {
                _releaseReply ($in);
            }
  } // send


  //game interface we use same interface beacause we are lazy
  public void mark (ChatApp.ChatCallback objref, short x, short y, short mark)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("mark", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_short (x);
                $out.write_short (y);
                $out.write_short (mark);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                mark (objref, x, y, mark        );
            } finally {
                _releaseReply ($in);
            }
  } // mark

  public void playGame (ChatApp.ChatCallback objref, String userName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("playGame", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (userName);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                playGame (objref, userName        );
            } finally {
                _releaseReply ($in);
            }
  } // playGame

  public void leaveGame (ChatApp.ChatCallback objref, String userName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("leaveGame", true);
                ChatApp.ChatCallbackHelper.write ($out, objref);
                $out.write_string (userName);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                leaveGame (objref, userName        );
            } finally {
                _releaseReply ($in);
            }
  } // leaveGame

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ChatApp/Chat:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ChatStub
