package liu.janva;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class MessageQueue extends Observable {
	
	//synchronizetion?
	private Queue<String> messages;
	
	public MessageQueue() {
		messages = new LinkedList<String>();
	}

	public void addMessageToBack (String msg)
	{
		messages.add(msg);
		this.setChanged();
		this.notifyObservers();
	}
	
	public String poll()
	{
		//caller handles null
		return messages.poll();
    }
	

}
