import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

public class BrickStorage {

	private Vector<Brick> bricks;
	private PlayField playField;
	private final int rows = 4;
	private final int columns = 10;

	public BrickStorage(PlayField playField){
		this.playField = playField;
		bricks = new Vector<>();
		int startX = 80;
		int x = startX;
		int y = 100;

		for(int row = 0; row < this.rows; row++){
			for(int column = 0; column < columns; column++){
				Brick.Type brickType = Brick.Type.random();
				Brick newBrick = brick(x, y, brickType);
				playField.addSprite(newBrick);
				bricks.addElement(newBrick); //!!!
				x += Brick.images.get(brickType).getWidth(null);
			}

			y += Brick.images.get(Brick.Type.DEFAULT).getHeight(null) + 2;
			x = startX;
		}
	}

	private Brick brick(int x, int y, Brick.Type type) {
		Image image = Brick.images.get(type);
		Rectangle bounds = brickBounds(x, y, image);
		return switch (type) {
			case DEFAULT -> new DefaultBrick(this.playField, this, bounds);
			case HARD -> new HardBrick(this.playField, this, bounds);
			case POWER -> new PowerBrick(this.playField, this, bounds);
		};
	}

	private Rectangle brickBounds(int x, int y, Image image) {
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		return new Rectangle(x, y, width, height);
	}

	public int unbrokenCount() {
		int result = 0;
		
		for (int i = 0; i < bricks.size(); i++) {
			if ( !((Brick) bricks.elementAt(i)).isDead() )
				result++; 	
		}
		
		return result;
	}

	public int brokenCount() {
		return bricks.size() - unbrokenCount();
	}
}