import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Ball extends MovableSprite {
	private BallsStorage ballsStorage;
	private PlayField playField;
	private final static BufferedImage image;

	static {
		try {
			image = ImageIO.read(new File("images/ball.gif"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Ball(PlayField playField, BallsStorage ballsStorage) {
		super(
			playField,
			image,
			(new Rectangle(playField.getWidth()/2, playField.getHeight()/2, image.getWidth(playField), image.getHeight(playField))),
			90,
			10);
		isMoving = true;
		this.ballsStorage = ballsStorage;
		this.playField = playField;
	}

	public void move() {
		if (!isMoving)
			return;

		Rectangle playFieldBoundary = playField.getBoundary();

		prevPos = bounds;
		bounds.translate(velocity.getSpeedX(), velocity.getSpeedY());
    
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
			playField.getFrame().setMessage(Integer.toString(ballsStorage.size()));
			isDead = true;
			if (ballsStorage.size() == 0) {
				playField.getFrame().lose();
			} else {
				playField.addSprite(ballsStorage.get());
			}
		}
	
		/* Обработка соударения с другими спрайтами */
		if (collideWith() != null) {
			bounds = prevPos;
			collideInto(collideWith());
		}
	}
	
	/* Реакция на возникновение коллизии. */
	public void collideInto(Sprite sprite) {
		sprite.hitBy(this);
	}
	
	public void hitBy(Ball ball) {
		;
	}
}
