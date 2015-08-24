

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import liu.janva.Board;
import liu.janva.Position;
import liu.janva.SimpleCheck;
import liu.janva.SimpleGameBoard;
import ChatApp.*;          // The package containing our stubs.

import org.omg.CosNaming.*; // HelloServer will use the naming service.
import org.omg.CosNaming.NamingContextPackage.*; // ..for exceptions.
import org.omg.CORBA.*;     // All CORBA applications need these classes.
import org.omg.PortableServer.*;

class ChatImpl extends ChatPOA
{ 
	private ORB orb;
	private Map<String, ChatCallback> activeUsers; 
	//TODO Migrate to this if time is given user would contain team as well as 
	//userName 
	//private Map<User, ChatCallback> activeUsers;
	//even if it's a bad idea we let client handle team selection	
	private Map<String, ChatCallback> players; 

	private Board board;

	//instansiate game and init    
	public ChatImpl() {
		super();
		this.activeUsers = new HashMap<String, ChatCallback>();
		this.players= new HashMap<String, ChatCallback>();
		board = new SimpleGameBoard(new SimpleCheck(), 9);
		//TODO for testing purposes board is not empty 
		board.clearBoard();
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
		//generate list of user from active
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
			leaveGame(cref,user);
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
			}
		}
		return msg;
	}

	//game interface 
	//TODO here for now move up later
	boolean activeGame= false;

	@Override
	public void mark(ChatCallback objref, short x, short y, short mark) {
		// TODO probably need to buffer commands command pattern?
		// simplest possible boolean active game if not do nothing?
		// sync problems?
		// call mark on board and check if caused win if so 
		//restart game (note might need to drop mark commands
		// which came to late)
		//for now we just drop incomming commands if not active
		if (activeGame) {			
			//TODO mark position side effect maybe not a good idea
			//(return true if win occurred)
			if(board.markPosition(new Position(x, y), mark))
			{
				activeGame=false;
				System.out.println("Winning board announced from chat server");
				board.clearBoard();
				for (ChatCallback playerCallbacks : players.values()) {
					playerCallbacks.update(board.toString());
					playerCallbacks.announceWin(x);
					//TODO send back new empty board empty board to active players
					//hmm maybe not here could be problem
					playerCallbacks.update(board.toString());
				}
			}else
			{
			for (ChatCallback playerCallbacks : players.values()) {
				playerCallbacks.update(board.toString());
			}
			}
		}
	}

	// This might also be vournability issue
	@Override
	public void playGame(ChatCallback callbackRef, String userName) {
		//TODO minimal check that we have joined through chat that is we a
		//Only those active on chat can play
		if (activeUsers.containsKey(userName))
		{
			//For now we send whole board as string
			String stringBoard = board.toString();
			if(players.containsValue(callbackRef))
			{
				//TODO could return value value here instead 
				// and handle on client side
				callbackRef.update("you allready joined as player");
				return;
			}
			if(players.isEmpty())
			{
				//clear the board just in case
				board.clearBoard();
				//TODO not a nice solution but ok for now
				activeGame = true;
				for(ChatCallback callback : activeUsers.values())
				{
					//tell everyone we started new game
					callback.update(userName +" Started new game join \n"+stringBoard);
				}
				players.put(userName, callbackRef);
			}else
			{
				//add player to active players
				players.put(userName, callbackRef);
				callbackRef.update(stringBoard);
			}
		}
		//callbackRef.callback("where did you come from? We've got an eye on you");
		///if we are already active players do nothing
		// else add as player 
		// if gameboard is null create new board and 
		//propagate board to those playing 
	}


	@Override
	public void leaveGame(ChatCallback objref,String user) {
		//players.remove(objref);
		if(players.containsKey(user))
		{
			//TODO think though is this the same refernce
			players.remove(user);
		}

		if (players.isEmpty())
		{
			activeGame = false;
			board.clearBoard();
		}else{
			for (ChatCallback pl : players.values()) {
				pl.update(user + " is leaving game \n");
			}
		}
		board.clearBoard();
		// TODO check if we are the last to leave if so 
		// now startgame will work correctly anyway but maybe 
		// good idea to to reset board here as well
		// set board to null

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
