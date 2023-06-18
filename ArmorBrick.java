import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class ArmorBrick extends Brick {

    public ArmorBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.ARMOR));
    }

    @Override
    public void hitBy(Ball ball) {
        Rectangle ballBounds = ball.getBounds();
        Rectangle brickBounds = this.getBounds();
        Rectangle brickTop = new Rectangle(brickBounds.x, brickBounds.y, brickBounds.width, 1);
        Rectangle2D intersection = ballBounds.createIntersection(brickBounds);
        Rectangle intersectionRect = intersection.getBounds();
        super.hitBy(ball);
        if (!intersectionRect.intersects(brickTop)) {
            isDead = false;
        }
    }
}
