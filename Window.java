import javax.swing.*;

class Window extends JFrame {

	public Window() {
		setTitle("Bricks");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(800, 800);
		setVisible(true);
		getContentPane().add(new PlayField(this));
	}
}