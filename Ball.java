import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Ball extends MovableSprite implements Runnable {
	private Thread thread;
	private int delay = 5;
	private BallsStorage ballsStorage;
	private PlayField playField;
	private final static BufferedImage image;

	static {
		try {
			image = ImageIO.read(new File("images/ball.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Ball(PlayField playField, BallsStorage ballsStorage) {
		super(
			playField,
			image,
			(new Rectangle(playField.getWidth()/2, playField.getHeight()/2+50, image.getWidth(playField), image.getHeight(playField))),
				135,
			2);
//		isMoving = true;
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
			if (ballsStorage.size() == 0) {
//				playField.lose();
			} else {
				ballsStorage.remove(0);
			}
		}

		/* Обработка соударения с другими спрайтами */
		if (collideWith() != null) {
			bounds = prevPos;
			collideInto(collideWith());
		}
	}

	public void collideInto(Sprite sprite) {
		sprite.hitBy(this);
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
