import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class PlayField extends JPanel implements Runnable {
	private final Window window;
	private Thread thread;
	private SpritesArray sprites;
	private BrickStorage brickStorage;
	private BallsStorage ballsStorage;
	private Racket racket;
	public Ball ball;
	private boolean running = false;

	public PlayField(Window window) {
		this.window = window;
	}

	public void restart() {
		running = true;
		sprites = new SpritesArray();
		thread = new Thread(this);
		thread.start();
		brickStorage = new BrickStorage(this);
		racket = new Racket(this);
		ballsStorage = new BallsStorage(this, 2);
		ball = ballsStorage.getFirst();
	}

	public void run() {
		while (running) {
			sprites.update();
			repaint();
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		if (!running)
			return;
//		graphics.clearRect(0, 0, WIDTH, HEIGHT);
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
		graphics.drawString("balls: " + ballsStorage.size(), 50, 50);
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

	public Thread getThread() {
		return thread;
	}
}