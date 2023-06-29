import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class TrapBrick extends Brick {

    public TrapBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.TRAP));
    }

    @Override
    public void hitBy(Ball ball) {
        Rectangle ballBounds = ball.getBounds();
        Rectangle brickBounds = this.getBounds();
        Rectangle brickBottom = new Rectangle(brickBounds.x, brickBounds.y+brickBounds.height-1, brickBounds.width, 1);
        Rectangle2D collisionIntersection2D = ballBounds.createIntersection(brickBounds);
        Rectangle collisionIntersection = collisionIntersection2D.getBounds();
        if (collisionIntersection.intersects(brickBottom)) {
            ball.setDead();
            playField.score+=1000;
        } else {
            super.hitBy(ball);
            setDead();
        }
    }
}
