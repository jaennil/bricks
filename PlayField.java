import javax.swing.*;
import java.awt.*;

class PlayField extends JPanel implements Runnable {
	private Window window;
	private Thread thread;
	private int DELAY = 0;
	private SpritesArray sprites;
	private BrickStorage brickStorage;
	private BallsStorage ballsStorage;
	private Racket racket;
	private static final int HEIGHT = 700;
	private static final int WIDTH = 700;
	private static final Rectangle BOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
	private boolean running = true;

	public PlayField(Window window) {
		this.window = window;
		setSize(WIDTH, HEIGHT);
		sprites = new SpritesArray();
		thread = new Thread(this);
		thread.start();
		brickStorage = new BrickStorage(this);
		racket = new Racket(this);
		ballsStorage = new BallsStorage(this, 2);

	}

	public void run() {
		while (running) {
			sprites.update();
			repaint();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		graphics.clearRect(0, 0, WIDTH, HEIGHT);
		sprites.draw(graphics);
		racket.draw(graphics);
//		Ball aliveBall = ballsStorage.getFirst();
		if (ballsStorage.size() == 0) {
			lose(graphics);
			return;
		}
		if (brickStorage.aliveAmount() == 0) {
			win(graphics);
			return;
		}
		ballsStorage.getFirst().draw(graphics);
		graphics.drawString("balls: " + ballsStorage.size(), 50, 50);
	}

	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	public void lose(Graphics graphics) {
		running = false;
		graphics.drawString("you lost", WIDTH/2, HEIGHT/2);
	}

	public void win(Graphics graphics) {
		running = false;
		graphics.drawString("you win", WIDTH/2, HEIGHT/2);
	}

	public Sprite testCollision(Sprite inputSprite) {
		// найти спрайт с которым произошла коллизия. есть не произошла то null
		Sprite sprite = sprites.testCollision(inputSprite);
		if (sprite == null) {
			if (racket.testCollision(inputSprite))
				return racket;
			return null;
		}
		return sprite;
	}

	public Rectangle getBoundary() {
		return BOUNDS;
	}

	public Window getWindow() {
		return window;
	}

	public Thread getThread() {
		return thread;
	}
}