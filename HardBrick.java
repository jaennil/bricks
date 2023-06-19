import java.awt.Rectangle;

public class HardBrick extends Brick {
    private int health = 2;

    public HardBrick(PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, Brick.images.get(Type.HARD));
    }

    @Override
    public void hitBy(Ball ball) {
        super.hitBy(ball);
        health--;
        if (health == 0)
            setDead();
        else {
            super.image = images.get(Type.DEFAULT);
        }
    }
}
