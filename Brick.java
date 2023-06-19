import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

abstract public class Brick extends Sprite {

	public enum Type {
		DEFAULT, HARD, POWER, WALL, STICKY, ARMOR, TRAP;

		public static Type random() {
			Type[] types = values();
			return types[(int)(Math.random() * types.length)];
		}

	}
	protected BrickStorage brickStorage;

	public static HashMap<Type, BufferedImage> images;

	static {
		images = new HashMap<>();
		try {
			images.put(Type.DEFAULT, ImageIO.read(new File("images/bricks/default.png")));
			images.put(Type.HARD, ImageIO.read(new File("images/bricks/hard.png")));
			images.put(Type.POWER, ImageIO.read(new File("images/bricks/power.png")));
			images.put(Type.WALL, ImageIO.read(new File("images/bricks/wall.png")));
			images.put(Type.STICKY, ImageIO.read(new File("images/bricks/sticky.png")));
			images.put(Type.ARMOR, ImageIO.read(new File("images/bricks/armor.png")));
			images.put(Type.TRAP, ImageIO.read(new File("images/bricks/trap.png")));
		} catch (IOException e) {
			System.out.println("cant find brick images");
			throw new RuntimeException(e);
		}
	}

	public Brick(PlayField playField, BrickStorage brickStorage, Rectangle bounds, Image image) {
		super(playField, bounds, image);
		this.brickStorage = brickStorage;
	}

	public void hitBy(Ball ball) {
		// by default, it acts like wall brick to prevent a lot of duplicated code
		Rectangle ballBounds = ball.getBounds();
		Rectangle brickBounds = this.getBounds();
		Rectangle brickLeft = new Rectangle(brickBounds.x, brickBounds.y, 1, brickBounds.height);
		Rectangle brickTop = new Rectangle(brickBounds.x, brickBounds.y, brickBounds.width, 1);
		Rectangle brickRight = new Rectangle(brickBounds.x+brickBounds.width-1, brickBounds.y, 1, brickBounds.height);
		Rectangle brickBottom = new Rectangle(brickBounds.x, brickBounds.y+brickBounds.height-1, brickBounds.width, 1);
		Rectangle2D collisionIntersection2D = ballBounds.createIntersection(brickBounds);
		Rectangle collisionIntersection = collisionIntersection2D.getBounds();
		if (collisionIntersection.intersects(brickLeft) && collisionIntersection.intersects(brickTop))
			ball.setDirection(90+45);
		else if (collisionIntersection.intersects(brickTop) && collisionIntersection.intersects(brickRight))
			ball.setDirection(45);
		else if (collisionIntersection.intersects(brickRight) && collisionIntersection.intersects(brickBottom))
			ball.setDirection(270+45);
		else if (collisionIntersection.intersects(brickBottom) && collisionIntersection.intersects(brickLeft))
			ball.setDirection(180 + 45);
		else if (collisionIntersection.intersects(brickLeft) || collisionIntersection.intersects(brickRight))
			ball.getVelocity().reverseX();
		else if (collisionIntersection.intersects(brickTop) || collisionIntersection.intersects(brickBottom))
			ball.getVelocity().reverseY();
	}

	public void update() {
		;
	}
}