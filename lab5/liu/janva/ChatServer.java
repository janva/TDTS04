package liu.janva;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ChatApp.*;          // The package containing our stubs.

import org.omg.CosNaming.*; // HelloServer will use the naming service.
import org.omg.CosNaming.NamingContextPackage.*; // ..for exceptions.
import org.omg.CORBA.*;     // All CORBA applications need these classes.
import org.omg.PortableServer.*;

class ChatImpl extends ChatPOA
{ 
	private ORB orb;
    //do we need hashtable synchronized threadsafe
    private Map<String, ChatCallback> activeUsers; 
   //list of acitve and inactive users do we need to map them to
    private String list="list of users";

    //TODO instansiate game and init
    
    public ChatImpl() {
	super();
	this.activeUsers = new HashMap<String, ChatCallback>();
    }

	public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String say(ChatCallback callobj, String msg)
    {
       //so use callobj to call on update in client
        callobj.callback(msg);
        return ("         ....server says Goodbye!\n");
    }
    
 public String list (ChatCallback objref){
      System.out.println("List under construction");
      //TODO generate list of user from active
      StringBuilder sb = new StringBuilder();
      for(String userName : activeUsers.keySet() )
      {
    	 sb.append(userName);
    	 sb.append(",");
      }
      System.out.println(sb.toString());
    	objref.callback(sb.toString());  
     return sb.toString();
   }
   public boolean join (ChatCallback cref, String user){
	  if(activeUsers.containsKey(user))
	  {
		  cref.callback("Alias " + user + " is already taken");
		  return false;
	  }
	  else
	  {
		  activeUsers.put(user, cref);
		  cref.callback("Your alias is " + user +" is now active" );
	  }
	  return true;
}
   //TODO think through should use name as key or cref latter probable better
   //behöver jag stänga specifica orben oxå
   public   void  leave (ChatApp.ChatCallback cref, String user){
	   ChatCallback userCallBack=activeUsers.remove(user);	
	   if (userCallBack != null)
	   {
		   userCallBack.callback(user + ": is leaving conversation");
	   }else
	   {
		   //TODO should maybe return this value or bool instead to mark success
		   cref.callback("no such user");
	   }
   }
   public  String send (ChatApp.ChatCallback senderRef, String msg){
	   //TODO for know message will do roundtrip and sent back 
	   // to sender as well
	   Collection< ChatCallback>userRefs =activeUsers.values();
	   if(userRefs.contains(senderRef))
	   {
		   for(ChatCallback cref : userRefs)
		   {
			   cref.update(msg);
			   System.out.println("called several times");
		   }
	   }
	   return msg;
}
 
}

public class ChatServer
{

    public static void main(String args[])
    {
	try {
	    // create and initialize the ORB
	    ORB orb = ORB.init(args, null);

	    // create servant (impl) and register it with the ORB
	    ChatImpl chatImpl = new ChatImpl();
	    chatImpl.setORB(orb);

	    // get reference to rootpoa & activate the POAManager
	    POA rootpoa =
		POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    rootpoa.the_POAManager().activate();

	    // get the root naming context
	    org.omg.CORBA.Object objRef =
		           orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

	    // obtain object reference from the servant (impl)
	    org.omg.CORBA.Object ref =
		rootpoa.servant_to_reference(chatImpl);
	    Chat cref = ChatHelper.narrow(ref);

	    // bind the object reference in naming
	    String name = "Chat";
	    NameComponent path[] = ncRef.to_name(name);
	    ncRef.rebind(path, cref);

	    // Application code goes below
	    System.out.println("ChatServer ready and waiting ...");

	    // wait for invocations from clients
	    orb.run();
	}

	catch(Exception e) {
	    System.err.println("ERROR : " + e);
	    e.printStackTrace(System.out);
	}

	System.out.println("ChatServer Exiting ...");
    }

}
