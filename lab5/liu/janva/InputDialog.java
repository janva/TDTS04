package liu.janva;
import javax.swing.*;

public class InputDialog extends JPanel{
	private static final long serialVersionUID = 1L;
	JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(5);
    //JPanel myPanel = new JPanel();
    
	public InputDialog() {
		super();
		  this.add(new JLabel("x:"));
	      this.add(xField);
	      this.add(Box.createHorizontalStrut(15)); // a spacer
	      this.add(new JLabel("y:"));
	      this.add(yField);
	}

	public int getX() {
		return 1;
	}


	public int getY() {
		return 1;
	}

	
}