package liu.janva;

import java.util.Observable;

import ChatApp.Chat;
import ChatApp.ChatCallback;


//Models user still under consideration given time
public class User extends Observable {

	private String name;
	boolean isActive;
	Chat chatImpl;
	ChatCallback callback;
}
