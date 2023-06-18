import java.awt.Rectangle;

public class HardBrick extends Brick {
    private int health = 2;

    public HardBrick(PlayField playField, BrickStorage brickPile, Rectangle bounds) {
        super(playField, brickPile, bounds, Brick.images.get(Type.HARD));
    }

    @Override
    public void hitBy(Ball ball) {
        health--;
        if (health == 0)
            super.hitBy(ball);
        else {
            super.image = images.get(Type.DEFAULT);
            ball.getVelocity().reverseY();

            if (brickStorage.unbrokenCount() == 0) {
//                playField.win();
            }
        }
    }

    public void update() {
        ;
    }
}
