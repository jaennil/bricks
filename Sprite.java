import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

abstract class Sprite {

	protected Image image;
	protected PlayField playField;
	protected Rectangle bounds;
	protected boolean isDead;

	public Sprite(PlayField playField, Rectangle bounds, Image image) {
		this.playField = playField;
		this.bounds = bounds;
		this.image = image;
	}

	public void draw(Graphics graphics) {
		graphics.drawImage(image, bounds.x, bounds.y, playField);
	}
	
	public boolean testCollision(Sprite sprite) {
		if (sprite != this)
			return bounds.intersects(sprite.getBounds());

		return false;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setDead() {
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	abstract public void update();
	abstract public void hitBy(Ball ball);
}