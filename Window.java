import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Window extends JFrame {

	public Window() {
		PlayField playField = new PlayField(this);
		setTitle("Bricks");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		JMenuItem restart = new JMenuItem("restart");
		menu.add(restart);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playField.restart();
			}
		});
		menuBar.add(menu);
		setJMenuBar(menuBar);
		add(playField);
		setSize(800, 800);
		setVisible(true);
	}
}