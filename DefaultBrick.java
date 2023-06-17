import java.awt.Rectangle;

public class DefaultBrick extends Brick {

    public DefaultBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.DEFAULT));
    }
}