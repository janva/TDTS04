package liu.janva;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import ChatApp.Chat;
import ChatApp.ChatCallback;

//TODO again to many responsibilities clean up also should be broken down 
// into smaller pieces
public class ChatWindow extends javax.swing.JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private Chat chatImpl;
	private ChatCallback cref;
	private MessageQueue messages;	
	private String userName ="";
	private String team="X";

	private javax.swing.JButton ListButton;
	private javax.swing.JLabel teamLabel;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane sendTextAreaScrollPane;
	private javax.swing.JButton joinButton;
	private javax.swing.JButton leaveButton;
	private javax.swing.JPanel loginPanel;
	private javax.swing.JPanel messagePanel;
	private javax.swing.JButton postButton;
	private javax.swing.JButton playButton;
	private javax.swing.JButton leaveGameButton;
	private javax.swing.JButton markButton;
	private javax.swing.JTextArea receivedTextarea;
	private javax.swing.JTextArea sendTextarea;
	private javax.swing.ButtonGroup teamGroup;
	private java.awt.Label userLabel;
	private javax.swing.JTextField userNameField;

	public ChatWindow(CorbaClientConnection  cc) {
		//for convienience
		this.chatImpl = cc.getChatImpl();
		this.cref = cc.getCref();
		//TODO fixme me refactor a LOOOOOOT just get things to work for
		//now
		messages = cc.getCallbackImpl().getMessageQueue();
		messages.addObserver(this);


		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(userName != "")
				{
					chatImpl.leave(cref, userName);
				}
			}
		});
		initComponents();

	}


	private void initComponents() {

		teamGroup = new javax.swing.ButtonGroup();
		loginPanel = new javax.swing.JPanel();
		userLabel = new java.awt.Label();
		userNameField = new javax.swing.JTextField();
		joinButton = new javax.swing.JButton();
		leaveButton = new javax.swing.JButton();
		leaveButton.setEnabled(false);
		markButton = new javax.swing.JButton();

		javax.swing.JRadioButton xRadioButton = new javax.swing.JRadioButton();
		javax.swing.JRadioButton oRadioButton = new javax.swing.JRadioButton();
		teamLabel = new javax.swing.JLabel();
		ListButton = new javax.swing.JButton();
		messagePanel = new javax.swing.JPanel();
		postButton = new javax.swing.JButton();
		playButton =  new javax.swing.JButton();
		leaveGameButton = new javax.swing.JButton();
		sendTextAreaScrollPane = new javax.swing.JScrollPane();
		sendTextarea = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		receivedTextarea = new javax.swing.JTextArea();
		receivedTextarea.setEditable(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		loginPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		userLabel.setText("User:");

		joinButton.setText("Join");
		joinButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				joinButtonActionPerformed(evt);
			}
		});

		leaveButton.setText("Leave");
		leaveButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leaveButtonActionPerformed(evt);
			}
		});

		teamGroup.add(xRadioButton);
		xRadioButton.setSelected(true);
		xRadioButton.setText("X");

		teamGroup.add(oRadioButton);
		oRadioButton.setText("O");

		teamLabel.setText("Team:");


		ListButton.setText("List");
		ListButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ListButtonActionPerformed(evt);
			}
		});

		sendTextarea.setColumns(26);
		sendTextarea.setRows(3);
		sendTextAreaScrollPane.setViewportView(sendTextarea);

		javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
		loginPanel.setLayout(loginPanelLayout);
		loginPanelLayout.setHorizontalGroup(
				loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(loginPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(loginPanelLayout.createSequentialGroup()
										.addComponent(joinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(leaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(ListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
										.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
												.addComponent(sendTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(10, 10, Short.MAX_VALUE)
												.addComponent(postButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGap(0, 0, Short.MAX_VALUE))
										.addGroup(loginPanelLayout.createSequentialGroup()
												.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(userNameField)
																																					)))
																		.addContainerGap())
				);
		loginPanelLayout.setVerticalGroup(
				loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(loginPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
								.addGap(18, 18, 18)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(joinButton)
												.addComponent(leaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(ListButton))
												.addGap(23, 23, 23)
												.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(sendTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE
														))
														.addGap(10, 10, 10)
														.addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(postButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(18, 18, 18)
						));

		messagePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		postButton.setText("Post");
		postButton.setEnabled(false);
		postButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				postButtonActionPerformed(evt);
			}
		});
		playButton.setText("PLay");
		playButton.setEnabled(false);
		playButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playButtonActionPerformed(evt);
			}
		});

		leaveGameButton.setText("Leave Game");
		leaveGameButton.setEnabled(false);
		leaveGameButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leaveGameButtonActionPerformed(evt);
			}
		});

		markButton.setText("make move");
		markButton.setEnabled(false);
		markButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				markButtonActionPerformed(evt);
			}
		});


		javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
		messagePanel.setLayout(messagePanelLayout);
		messagePanelLayout.setHorizontalGroup(
				messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(messagePanelLayout.createSequentialGroup()
						.addComponent(teamLabel)
						.addComponent(xRadioButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(oRadioButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(messagePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(playButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(leaveGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(markButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								)
								.addContainerGap())
				);
		messagePanelLayout.setVerticalGroup(
				messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(messagePanelLayout.createSequentialGroup()
						.addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(xRadioButton)
						.addComponent(oRadioButton)
						.addComponent(teamLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addContainerGap()
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(leaveGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(markButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		jPanel2.setBorder(null);

		receivedTextarea.setColumns(20);
		receivedTextarea.setRows(5);
		jScrollPane1.setViewportView(receivedTextarea);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE))
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGap(18, 18, 18)
												.addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addContainerGap())
				);

		pack();
	}

	private void postButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String sentMsg =  sendTextarea.getText();
		sendTextarea.setText("");
		chatImpl.send(cref, sentMsg);
	}

	private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
		chatImpl.playGame(cref, userName);
		//TODO refactor this part sucks
		Enumeration<AbstractButton> allRadioButton=teamGroup.getElements(); 
		while (allRadioButton.hasMoreElements())
		{
			JRadioButton chosenTeam = (JRadioButton)allRadioButton.nextElement();
			if((chosenTeam.isSelected()) && (chosenTeam.getText().equals("X")))
			{
				team = "X";
				break;
			}
			else 
			{
				team = "O";
			}
			break;
		}
		markButton.setEnabled(true);
		leaveGameButton.setEnabled(true);
	}
	private void leaveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		chatImpl.leaveGame(cref, userName);
		markButton.setEnabled(false);
		leaveGameButton.setEnabled(false);
	}
	private void markButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO make mark some abstract type which can be converted to wanted type
		InputDialog xyDialog= new InputDialog(); 
		int result = JOptionPane.showConfirmDialog(this, xyDialog, 
				"Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			int x = Integer.parseInt(xyDialog.xField.getText());
			int y = Integer.parseInt(xyDialog.yField.getText());
			chatImpl.mark(cref, (short)x, (short)y, (short)(team=="X" ? 1 : 2));
		}
	}

	private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//return null or "" on empty field?
		userName = userNameField.getText();

		//TODO fixme need to wait for callback to do the bellow not working at the moment
		if(userName=="")
		{
			JOptionPane.showMessageDialog(this, "User cannot be empty string");
			return;
		}
		boolean joined = chatImpl.join(cref, userName);
		if (!joined)
		{
			JOptionPane.showMessageDialog(this, "A user with that alias allready exists");
			return;
		}

		postButton.setEnabled(true);       
		leaveButton.setEnabled(true);
		playButton.setEnabled(true);
		//GameButton.setEnabled(true);

		joinButton.setEnabled(false);
	}

	private void leaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		chatImpl.leave(cref, userName);
		userName = "";
		userNameField.setText("");
		postButton.setEnabled(false);
		joinButton.setEnabled(true);
		playButton.setEnabled(false);
		leaveGameButton.setEnabled(false);
	
	}

	private void ListButtonActionPerformed(java.awt.event.ActionEvent evt) {
		receivedTextarea.append(chatImpl.list(cref) +"\n");  
	}


	@Override
	//TODO instead of having whoel JFrame being observer
	//make specific textwindow the observer
	public void update(Observable arg0, Object arg1) {
		//TODO should do check for correct arg could couse syncproblems messages?
		receivedTextarea.append(messages.poll());
	}
}
