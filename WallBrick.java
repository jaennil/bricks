import java.awt.Rectangle;

public class WallBrick extends Brick {

    public WallBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.WALL));
    }
}