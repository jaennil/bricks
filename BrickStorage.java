import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickStorage extends ArrayList<Brick> {

	private final PlayField playField;
	private final int rows = 1;
	private final int columns = 1;

	public BrickStorage(PlayField playField){
		this.playField = playField;
		int startX = 350;
		int x = startX;
		int y = 300;

		for(int row = 0; row < this.rows; row++){
			for(int column = 0; column < columns; column++){
				Brick.Type brickType = Brick.Type.random();
				Brick newBrick = constructBrick(x, y, brickType);
				playField.addSprite(newBrick);
				add(newBrick);
				x += Brick.images.get(brickType).getWidth(null);
			}

			y += Brick.images.get(Brick.Type.DEFAULT).getHeight(null) + 2;
			x = startX;
		}
	}

	public BrickStorage(PlayField playField, String path){
		this.playField = playField;
		int startX = 20;
		int x = startX;
		int y = 20;

		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				for (int i = 0; i < line.length(); i++) {
					switch (line.charAt(i)) {
						case 'B':
							Brick.Type brickType = Brick.Type.random();
							Brick newBrick = constructBrick(x, y, brickType);
							playField.addSprite(newBrick);
							add(newBrick);
							break;
					}
					x += Brick.images.get(Brick.Type.DEFAULT).getWidth(null);
				}
				y += Brick.images.get(Brick.Type.DEFAULT).getHeight(null) + 2;
				x = startX;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
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
			case STICKY -> new StickyBrick(this.playField, this, bounds);
			case ARMOR -> new ArmorBrick(this.playField, this, bounds);
			case TRAP -> new TrapBrick(this.playField, this, bounds);
		};
	}

	private Rectangle brickBounds(int x, int y, Image image) {
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		return new Rectangle(x, y, width, height);
	}

	public int aliveAmount() {
		int amount = 0;
		for (Brick brick : this) {
			if (!brick.isDead()) {
				// because they always alive
				if (!(brick instanceof WallBrick) && !(brick instanceof StickyBrick))
					amount++;
			}
		}
		return amount;
	}
}