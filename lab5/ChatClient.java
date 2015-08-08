

import liu.janva.ChatWindow;
import liu.janva.CorbaClientConnection;
import ChatApp.*;          // The package containing our stubs

import org.omg.CosNaming.*; // HelloClient will use the naming service.
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;     // All CORBA applications need these classes.
import org.omg.PortableServer.*;


public class ChatClient
{
    public static void main(String args[])
    {
    //TODO refactor separate into seperat methods at least if not 
    //classes
    	// builder pattern? dependency inject into gui or factory pattern
    	// could i benefit from observer?
	    try {
	    	final CorbaClientConnection.CorbClientConnectionBuilder builder =
	    			new CorbaClientConnection.CorbClientConnectionBuilder();
	    	builder.createORB(args, null);
	    	builder.setNestedChatCallbackImpl();
	    	builder.setNestedRootPOA("RootPOA");
	    	builder.setNestedNcRef("NameService");
	    	builder.setNestedName("Chat");
	    	builder.setNestedCref();

//		    chatImpl.join(cref, "kalle");
//		    chatImpl.join(cref, "Orvar");
//		    chatImpl.list(cref);
//		    chatImpl.send(cref, "hello");
		    
		    //this is a bit mysterious we send something whitout the cref yet
		    //server finds it's way to this method call how come
		    //chat = chatImpl.list();
		    //System.out.println(chat);
	    	for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	    		if ("Nimbus".equals(info.getName())) {
	    			javax.swing.UIManager.setLookAndFeel(info.getClassName());
	    			break;
	    		} 
	    		java.awt.EventQueue.invokeLater(new Runnable() {
	    			public void run() {
	    				//for now just pass 
	    				//new ChatWindow(chatImpl, cref_temp).setVisible(true);
	    				new ChatWindow(builder.createConnection()).setVisible(true);
	    			}
	    		});
	    	}	
	    	} catch(Exception e){
	    		System.out.println("ERROR : " + e);
	    		e.printStackTrace(System.out);
	    	}}}
//	    	catch (ClassNotFoundException ex) {
//	    		java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//	    	} catch (InstantiationException ex) {
//	    		java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//	    	} catch (IllegalAccessException ex) {
//	    		java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//	    	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//	    		java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//	    	}
	    
    
    
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    //try {
    	
       
     //   }
    //}
	
    /* Create and display the form */
    

