

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import liu.janva.Board;
import liu.janva.Position;
import liu.janva.SimpleCheck;
import liu.janva.SimpleGameBoard;
import ChatApp.*;          // The package containing our stubs.

import org.omg.CosNaming.*; // HelloServer will use the naming service.
import org.omg.CORBA.*;     // All CORBA applications need these classes.
import org.omg.PortableServer.*;

class ChatImpl extends ChatPOA
{ 
	private Map<ChatCallback, String> activeUsers; 
	private Map<String, ChatCallback> players; 
	
	private Board board;
	private boolean activeGame= false;

	//instansiate game and init    
	public ChatImpl() {
		super();
		this.activeUsers = new HashMap<ChatCallback, String>();
		this.players= new HashMap<String, ChatCallback>();
		board = new SimpleGameBoard(new SimpleCheck(), 9);
		//For testing purposes board is not empty 
		board.clearBoard();
	}

//	public void setORB(ORB orb_val) {
//	}

	public String list (ChatCallback objref){
		//generate list of user from active
		StringBuilder sb = new StringBuilder();
		for(String userName : activeUsers.values() )
		{
			sb.append(userName);
			sb.append(",");
		}
		//works by returning as well
		return sb.toString();
	}

	public boolean join (ChatCallback cref, String user){
		if(activeUsers.containsValue(user))
		{
			return false;
		}
		else
		{
			sendAll(user + " joined conversation");
			activeUsers.put(cref,user);
			cref.update("Your alias is " + user +" happy chatting \n" );
		}
		return true;
	}
	public   void  leave (ChatApp.ChatCallback cref, String user){
		
		String userName=activeUsers.remove(cref);	
		if (userName != null)
		{
			leaveGame(cref, user);	
			sendAll(user + ": is leaving conversation");
		}else
		{
			cref.update("no such user");
		}
	}
	public  void send (ChatApp.ChatCallback senderRef, String msg){
		String user = activeUsers.get(senderRef);
		if(user!= null)
		{
			sendAll("["+user +"] "+ msg);
		}
		else
		{
			//senderRef.update("We could not find your alias");
  		   // return "We could not find your alias";
		}
	}
	//game interface 

	@Override //need more work
	public void mark(ChatCallback objref, short x, short y, short mark) {
		if (activeGame) 
		{	
			//TODO case was thought of at late stage
			if(board.full())
			{
				sendAllPlayers("No winner new game");
				board.clearBoard();
				sendAllPlayers(board.toString());
				return;
			}
			Position lastMove =new Position(x, y);
			//was square allready taken?
			if(board.markPosition(lastMove, mark))
			{
				//did placing mark cause win
				if(board.checkWin(lastMove))
				{
					activeGame=false;
					sendAllPlayers(board.toString());
					board.clearBoard();
					for (ChatCallback playerCallbacks : players.values()) {
						//Initially announce was intended to do more stuff on client side 
						playerCallbacks.announceWin(x);
						playerCallbacks.update(board.toString());
					}
					//This is just stupid :-)
					activeGame=true;
				}else
				{   
					sendAllPlayers("\n"+board.toString());
				}
			}else //square was occupied
			{
				objref.update("(" + x +","+ y +") is not possible \n");
			}
		}
	}

	@Override
	public void playGame(ChatCallback callbackRef, String userName) {
		//Only those active on chat can play
		if (activeUsers.containsKey(callbackRef))
		{
			String stringBoard = board.toString();
			if(players.containsValue(callbackRef))
			{
				callbackRef.update("you allready joined as player");
				return;
			}
			if(players.isEmpty())
			{
				//clear the board just in case
				board.clearBoard();
				activeGame = true;
				sendAll(userName  + "Invites everyone to play \n"+stringBoard);
				players.put(userName, callbackRef);
			}else
			{
				//add player to active players
				players.put(userName, callbackRef);
				callbackRef.update(stringBoard);
			}
		}
	}

	@Override
	public void leaveGame(ChatCallback objref,String user) {
		if(players.containsKey(user))
		{
			players.remove(user);
			if (players.isEmpty())
			{
				activeGame = false;
				board.clearBoard();
			}else 
			{
				sendAllPlayers(user + "is leaving game");
			}
		}
	}

	private void sendAll(String msg)
	{
		sendToSet(msg,activeUsers.keySet());
	}

	private void sendAllPlayers(String msg)
	{
		sendToSet(msg,players.values());
	}

	private void sendToSet(String msg, Collection< ChatCallback> callbackRefs)
	{
		for (ChatCallback callback : callbackRefs) {
			callback.update(msg +"\n");
		}
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
			//chatImpl.setORB(orb);

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
