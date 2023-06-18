import java.awt.Rectangle;

public class StickyBrick extends Brick {

    public StickyBrick (PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, images.get(Type.STICKY));
    }

    @Override
    public void hitBy(Ball ball) {
        ball.setDirection(270);
        ball.setImage(Ball.images.get(Ball.Type.STICKY));
        try {
            Thread.sleep(2000);
        } catch (Exception exception) {
            System.out.println("cant sleep thread in sticky brick");
        }
    }
}
