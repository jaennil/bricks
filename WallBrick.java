import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class WallBrick extends Brick {

    public WallBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.WALL));
    }

    @Override
    public void hitBy(Ball ball) {
        Rectangle ballBounds = ball.getBounds();
        Rectangle brickBounds = this.getBounds();
        Rectangle brickLeft = new Rectangle(brickBounds.x, brickBounds.y, 1, brickBounds.height);
        Rectangle brickTop = new Rectangle(brickBounds.x, brickBounds.y, brickBounds.width, 1);
        Rectangle brickRight = new Rectangle(brickBounds.x+brickBounds.width-1, brickBounds.y, 1, brickBounds.height);
        Rectangle brickBottom = new Rectangle(brickBounds.x, brickBounds.y+brickBounds.height-1, brickBounds.width, 1);
        Rectangle2D intersection = ballBounds.createIntersection(brickBounds);
        Rectangle intersectionRect = intersection.getBounds();
        if (intersectionRect.intersects(brickLeft) && intersectionRect.intersects(brickTop))
            ball.setDirection(90+45);
        else if (intersectionRect.intersects(brickTop) && intersectionRect.intersects(brickRight))
            ball.setDirection(45);
        else if (intersectionRect.intersects(brickRight) && intersectionRect.intersects(brickBottom))
            ball.setDirection(270+45);
        else if (intersectionRect.intersects(brickBottom) && intersectionRect.intersects(brickLeft))
            ball.setDirection(180 + 45);
        else if (intersectionRect.intersects(brickLeft) || intersectionRect.intersects(brickRight))
            ball.getVelocity().reverseX();
        else if (intersectionRect.intersects(brickTop) || intersectionRect.intersects(brickBottom))
            ball.getVelocity().reverseY();
    }
}