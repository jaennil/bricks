import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

// #TODO make every movable sprite have its own thread that moving it.
abstract class MovableSprite extends Sprite {

	protected boolean isMoving = false;
	protected Velocity velocity;
	protected Rectangle prevPos;

	public MovableSprite(PlayField playField, Image image, Rectangle bounds, int direction, int speed) {
		super(playField, bounds, image);
		velocity = new Velocity(direction, speed);
	}

	public void setDirection(int dir) {
		velocity.setDirection(dir);
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public ArrayList<Sprite> collideWith() {
		return playField.testCollision(this);
	}

	abstract public void move();

	public void update() {
		move();
	}

	public void startMoving() {
		isMoving = true;
	}

	public void stopMoving() {
		isMoving = false;
	}
}