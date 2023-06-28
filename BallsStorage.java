import java.util.ArrayList;

class BallsStorage extends ArrayList<Ball> {

    public BallsStorage(PlayField playField, int amount) {
        for (int i = 0; i < amount; i++)
            add( new Ball(playField, this));
    }

    public Ball getFirst() {
        if (size() > 0) {
            Ball ball = get(0);
            ball.updateBounds();
            ball.startMoving();
            return ball;
        } else {
            return null;
        }
    }
}