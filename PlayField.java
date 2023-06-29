import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

	class PlayField extends JPanel implements Runnable {
	private final Window window;
	private Thread thread;
	private SpritesArray sprites;
	private BrickStorage brickStorage;
	private BallsStorage ballsStorage;
	private Racket racket;
	private Ball ball;
	private boolean running = false;
	public int score = 0;
	public long startTime;

	public PlayField(Window window) {
		this.window = window;
	}

	public void restart() {
		score = 0;
		startTime = System.currentTimeMillis();
		thread = new Thread(this);
		sprites = new SpritesArray();
		brickStorage = new BrickStorage(this);
		racket = new Racket(this);
		ballsStorage = new BallsStorage(this, 2);
		ball = ballsStorage.getFirst();
		thread.start();
	}

	public void restart(String path) {
		score = 0;
		startTime = System.currentTimeMillis();
		thread = new Thread(this);
		sprites = new SpritesArray();
		brickStorage = new BrickStorage(this, path);
		racket = new Racket(this);
		ballsStorage = new BallsStorage(this, 2);
		ball = ballsStorage.getFirst();
		thread.start();
	}

	public void run() {
		running = true;
		while (running) {
			sprites.update();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		// maybe this if statement doesn't work somehow
		if (!running)
			return;
		graphics.clearRect(0, 0, getWidth(), getHeight());
		sprites.draw(graphics);
		racket.draw(graphics);
		if (ballsStorage.size() == 0) {
			lose(graphics);
			return;
		}
		if (brickStorage.aliveAmount() == 0) {
			win(graphics);
			return;
		}
		ball.draw(graphics);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
		graphics.drawString("balls: " + ballsStorage.size(), 50, 50);
		graphics.drawString("ability charges: " + (racket.abilityUsed ? 0 : 1), 150, 50);
		graphics.drawString("score: " + score, 50, 100);
	}

	public static void playAudio(final String url) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	public void lose(Graphics graphics) {
		running = false;
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString("YOU LOST!", getWidth()/2-100, getHeight()/2);
	}

	public void win(Graphics graphics) {
		System.out.println(System.currentTimeMillis()-startTime);
		new ScoreDialog(score* 1000L /(System.currentTimeMillis()-startTime));
		running = false;
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString("YOU WIN!", getWidth()/2-100, getHeight()/2);
	}

	public ArrayList<Sprite> testCollision(Sprite inputSprite) {
		// найти спрайтs с которым произошла коллизия. есть не произошла то null
		ArrayList<Sprite> spriteCollisions = sprites.testCollision(inputSprite);
		if (racket.testCollision(inputSprite))
			spriteCollisions.add(racket);
		return spriteCollisions;
	}

	public Rectangle getBoundary() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public Window getWindow() {
		return window;
	}

	public Racket getRacket() {
		return racket;
	}

	public Thread getThread() {
		return thread;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
}