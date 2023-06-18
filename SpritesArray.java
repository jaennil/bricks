import java.awt.Graphics;
import java.util.ArrayList;

class SpritesArray extends ArrayList<Sprite> {
	public void draw(Graphics graphics) {
		// cant replace with foreach because of ConcurrentModificationException
		for (int i = 0; i < size(); i++) {
			Sprite sprite = get(i);
			sprite.draw(graphics);
		}
	}
	
	public ArrayList<Sprite> testCollision(Sprite inputSprite) {
		ArrayList<Sprite> result = new ArrayList<>();
		for (Sprite sprite : this) {
			if (inputSprite == sprite)
				continue;

			if (inputSprite.testCollision(sprite))
				result.add(sprite);
		}
		return result;
	}
	
	public void update() {
		// cant replace with foreach because of ConcurrentModificationException
		for (int i = 0; i < size(); i++) {
			Sprite sprite = get(i);
			sprite.update();
			if (sprite.isDead()) {
				remove(sprite);
			}
		}
	}
}
