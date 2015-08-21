package liu.janva;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import ChatApp.Chat;
import ChatApp.ChatCallback;
import ChatApp.ChatCallbackHelper;
import ChatApp.ChatCallbackPOA;
import ChatApp.ChatHelper;

public class CorbaClientConnection {

	// TODO To many responsibileties
	public static class ChatCallbackImpl extends ChatCallbackPOA {
		// TODO factor out to class? synchronization?
		private MessageQueue messages;
		private ORB orb;

		public ChatCallbackImpl() {
			messages = new MessageQueue();
		}

		public MessageQueue getMessageQueue() {
			return messages;

		}

		// add observable message queque for instance
		public void setORB(ORB orb_val) {
			orb = orb_val;
		}

		// TODO server can use this as update hmm observer pattern maybe
		public void callback(String notification) {
			System.out.println(notification + "--by callback ");
		}

		@Override
		public void update(String message) {
			messages.addMessageToBack(message);
			System.out.println(message + " -this call method will update gui");
		}

		@Override
		public void announceWin(short x) {
			//TODO for now we just announce in chat window
			messages.addMessageToBack("The winner is " + x);
		}
	}

	//for our purposes we only need two of these
	private Chat chatImpl;
	private ORB orb;
	private ChatCallbackImpl chatCallbackImpl;
	private POA rootPOA;
	private NamingContextExt ncRef;
	private String name;
	private org.omg.CORBA.Object ref;
	// and this
	private ChatCallback cref;

	// TODO maybe template method patttern on top
	private CorbaClientConnection(Chat chatImpl, ORB orb,
			ChatCallbackImpl chatCallbackImpl, POA rootPOA,
			NamingContextExt ncRef, String name, Object ref, ChatCallback cref) {
		super();
		this.chatImpl = chatImpl;
		this.orb = orb;
		this.chatCallbackImpl = chatCallbackImpl;
		this.rootPOA = rootPOA;
		this.ncRef = ncRef;
		this.name = name;
		this.ref = ref;
		this.cref = cref;
	}

	public Chat getChatImpl() {
		return chatImpl;
	}

	public ChatCallback getCref() {
		return cref;
	}

	public ChatCallbackImpl getCallbackImpl() {
		return chatCallbackImpl;
	}

	public static class CorbClientConnectionBuilder {
		private Chat nestedChatImpl;
		private ORB nestedOrb;
		private ChatCallbackImpl nestedChatCallbackImpl;
		private POA nestedRootPOA;
		private NamingContextExt nestedNcRef;
		private String nestedName;
		private org.omg.CORBA.Object nestedRef;
		private ChatCallback nestedCref;

		public CorbClientConnectionBuilder createORB(String args[],
				Properties props) {
			this.nestedOrb = ORB.init(args, props);
			return this;
		}

		public CorbClientConnectionBuilder setNestedChatCallbackImpl() {
			this.nestedChatCallbackImpl = new ChatCallbackImpl();
			nestedChatCallbackImpl.setORB(nestedOrb);
			return this;
		}

		public CorbClientConnectionBuilder setNestedRootPOA(String rootName)
				throws InvalidName, AdapterInactive {
			// get reference to RootPOA and activate the POAManager
			// "RootPOA"
			nestedRootPOA = POAHelper.narrow(nestedOrb
					.resolve_initial_references(rootName));
			nestedRootPOA.the_POAManager().activate();
			return this;
		}

		public CorbClientConnectionBuilder setNestedNcRef(String serviceString)
				throws InvalidName {
			// get the root naming context "NameService"
			org.omg.CORBA.Object objRef = nestedOrb
					.resolve_initial_references(serviceString);
			nestedNcRef = NamingContextExtHelper.narrow(objRef);
			return this;
		}

		public CorbClientConnectionBuilder setNestedName(String nestedName)
				throws NotFound, CannotProceed,
				org.omg.CosNaming.NamingContextPackage.InvalidName {
			// resolve the object reference in naming
			this.nestedName = nestedName;
			nestedChatImpl = ChatHelper.narrow(nestedNcRef
					.resolve_str(nestedName));
			return this;
		}

		// public void setNestedRef(org.omg.CORBA.Object nestedRef) {
		// this.nestedRef = nestedRef;
		// }

		public CorbClientConnectionBuilder setNestedCref()
				throws ServantNotActive, WrongPolicy {
			// obtain callback reference for registration w/ server
			// This is the reference server uses to callback to client
			org.omg.CORBA.Object ref = nestedRootPOA
					.servant_to_reference(nestedChatCallbackImpl);
			nestedCref = ChatCallbackHelper.narrow(ref);
			return this;
		}

		public CorbaClientConnection createConnection() {
			return new CorbaClientConnection(nestedChatImpl, nestedOrb,
					nestedChatCallbackImpl, nestedRootPOA, nestedNcRef,
					nestedName, nestedRef, nestedCref);
		}
	}
}
