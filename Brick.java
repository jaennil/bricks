import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

abstract public class Brick extends StationarySprite {

	public enum Type {
		DEFAULT, HARD, POWER, WALL;

		public static Type random() {
			Type[] types = values();
			return types[(int)(Math.random() * values().length)];
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
		isDead = true;
		ball.getVelocity().reverseY();
	
		if (brickStorage.unbrokenCount() == 0) {
			playField.getFrame().win();
		}
	}

	public void update() {
		;
	}
}