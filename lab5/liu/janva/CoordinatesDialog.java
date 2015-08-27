package liu.janva;
import javax.swing.*;

public class CoordinatesDialog extends JPanel{
	private static final long serialVersionUID = 1L;
	JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(5);
    
	public CoordinatesDialog() {
		super();
		  this.add(new JLabel("x:"));
	      this.add(xField);
	      this.add(Box.createHorizontalStrut(15)); // a spacer
	      this.add(new JLabel("y:"));
	      this.add(yField);
	}
}