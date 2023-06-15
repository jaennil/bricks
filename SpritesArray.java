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
	
	public Sprite testCollision(Sprite inputSprite) {
		for (Sprite sprite : this) {
			if (inputSprite == sprite)
				continue;

			if (inputSprite.testCollision(sprite))
				return sprite;
		}
		return null;
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
