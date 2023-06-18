import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BrickStorage {

	private PlayField playField;
	private ArrayList<Brick> bricks;
	private final int rows = 6;
	private final int columns = 15;

	public BrickStorage(PlayField playField){
		this.playField = playField;
		bricks = new ArrayList<>();
		int startX = 80;
		int x = startX;
		int y = 100;

		for(int row = 0; row < this.rows; row++){
			for(int column = 0; column < columns; column++){
				Brick.Type brickType = Brick.Type.random();
				Brick newBrick = constructBrick(x, y, brickType);
				playField.addSprite(newBrick);
				// test stuff to check if game ends when all bricks destroyed except wallBricks
				if (newBrick instanceof WallBrick == false)
					bricks.add(newBrick);
				x += Brick.images.get(brickType).getWidth(null);
			}

			y += Brick.images.get(Brick.Type.DEFAULT).getHeight(null) + 2;
			x = startX;
		}
	}

	private Brick constructBrick(int x, int y, Brick.Type type) {
		Image image = Brick.images.get(type);
		Rectangle bounds = brickBounds(x, y, image);
		return switch (type) {
			case DEFAULT -> new DefaultBrick(this.playField, this, bounds);
			case HARD -> new HardBrick(this.playField, this, bounds);
			case POWER -> new PowerBrick(this.playField, this, bounds);
			case WALL -> new WallBrick(this.playField, this, bounds);
		};
	}

	private Rectangle brickBounds(int x, int y, Image image) {
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		return new Rectangle(x, y, width, height);
	}

	public int unbrokenCount() {
		int result = 0;

		for (Brick brick : bricks) {
			if (brick.isDead() == false)
				result++;
		}
		return result;
	}
}