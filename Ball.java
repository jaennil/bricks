import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Ball extends MovableSprite implements Runnable {
	public enum Type {
		DEFAULT, STICKY;
	}
	private Thread thread;
	private int delay = 8;
	private final BallsStorage ballsStorage;
	private final PlayField playField;
	public static HashMap<Type, BufferedImage> images;

	static {
		images = new HashMap<>();
		try {
			images.put(Type.DEFAULT, ImageIO.read(new File("images/ball/default.png")));
			images.put(Type.STICKY, ImageIO.read(new File("images/ball/sticky.png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Ball(PlayField playField, BallsStorage ballsStorage) {
		super(
			playField,
			images.get(Type.DEFAULT),
			(new Rectangle(playField.getWidth()/2, playField.getHeight()/2+250, images.get(Type.DEFAULT).getWidth(playField), images.get(Type.DEFAULT).getHeight(playField))),
				270,
			2);
		this.ballsStorage = ballsStorage;
		this.playField = playField;
		while (true) {
			if (playField.getThread().isAlive()) {
				break;
			}
		}
		thread = new Thread(this, "ball thread");
		thread.start();
	}

	public void move() {
		if (!isMoving)
			return;

		Rectangle playFieldBoundary = playField.getBoundary();

		prevPos = bounds;
		bounds.translate((int)Math.round(velocity.getSpeedX()), (int)Math.round(velocity.getSpeedY()));

		if (bounds.x <= playFieldBoundary.x) {
			bounds.x = playFieldBoundary.x;
			velocity.reverseX();
		} else if (bounds.x + bounds.width >= playFieldBoundary.width + playFieldBoundary.x) {
			bounds.x = playFieldBoundary.x + playFieldBoundary.width - bounds.width;
			velocity.reverseX();
		} else if (bounds.y <= playFieldBoundary.y) {
			bounds.y = playFieldBoundary.y;
			velocity.reverseY();
		} else if (bounds.y + bounds.height > playFieldBoundary.y + playFieldBoundary.height) {
			isDead = true;
		}

		ArrayList<Sprite> collisionWith = collideWith();
		if (collisionWith.size() != 0) {
			bounds = prevPos;
			for (Sprite sprite : collisionWith)
				sprite.hitBy(this);
		}
	}

	public void run() {
		while (isDead == false) {
			move();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		ballsStorage.remove(this);
		playField.ball = ballsStorage.getFirst();
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	@Override
	public void hitBy(Ball ball) {

	}
}
