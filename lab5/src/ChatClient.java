

import liu.janva.CorbaClientConnection;
import liu.janva.gui.ChatWindow;

public class ChatClient
{
    public static void main(String args[])
    {
	    try {
	    	final CorbaClientConnection.CorbClientConnectionBuilder builder =
	    			new CorbaClientConnection.CorbClientConnectionBuilder();
	    	builder.createORB(args, null);
	    	builder.setNestedChatCallbackImpl();
	    	builder.setNestedRootPOA("RootPOA");
	    	builder.setNestedNcRef("NameService");
	    	builder.setNestedName("Chat");
	    	builder.setNestedCref();
		    
	    	for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	    		if ("Nimbus".equals(info.getName())) {
	    			javax.swing.UIManager.setLookAndFeel(info.getClassName());
	    			break;
	    		} 
	    		java.awt.EventQueue.invokeLater(new Runnable() {
	    			public void run() {
	    				new ChatWindow(builder.createConnection()).setVisible(true);
	    			}
	    		});
	    	}	
	    	} catch(Exception e){
	    		System.out.println("ERROR : " + e);
	    		e.printStackTrace(System.out);
	    	}}}
