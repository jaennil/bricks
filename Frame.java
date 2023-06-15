import javax.swing.*;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Frame extends JFrame {
	private BallsStorage ballsStorage;
	private BrickStorage brickStorage;
	private PlayField playField;
	private String message = "";

	public Frame() {
		setTitle("Bricks");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 400);
		MenuBar menuBar = new MenuBar();
		setMenuBar(menuBar);

		Menu menu = new Menu("Game");
		MenuItem startMenuItem, stopMenuItem, resumeMenuItem;
		menu.add(startMenuItem = new MenuItem("start"));
		menu.add(stopMenuItem = new MenuItem("pause"));
		menu.add(resumeMenuItem = new MenuItem("resume"));

		menuBar.add(menu);

		startMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start(); //Начать игру.
			}
		});
    
		stopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playField.stop(); //Приостановить игру.
			}
		});
    
		resumeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playField.start(); //Возобновить игру.
			}
		});

		playField = new PlayField(this);
		add("Center", playField);
		setVisible(true);
		start();
	}

	public void start() {
		message = "";
		playField.clean();

        brickStorage = new BrickStorage(playField);
        ballsStorage = new BallsStorage(playField, 3);
   
		playField.addSprite(new Racket(playField));
		playField.addSprite(ballsStorage.get());
    
		playField.start();
	}

	public void lose() {
		message = "You lose";
		playField.repaint();
		playField.stop();
	}

	public void win() {
		message = "You win";
		playField.repaint();
		playField.stop();
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public static void main(String[] args) {
		Frame frame = new Frame();
	}
}