import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class WallBrick extends Brick {

    public WallBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.WALL));
    }
}