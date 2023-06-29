import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Ball extends MovableSprite implements Runnable {
    public enum Type {
        DEFAULT, STICKY
    }

    private Thread thread;
    private int delay = 12;
    private final BallsStorage ballsStorage;
    private final PlayField playField;
    public static HashMap<Type, BufferedImage> images;

    static {
        images = new HashMap<>();
        try {
            images.put(Type.DEFAULT, ImageIO.read(new File("images/ball/default.png")));
            images.put(Type.STICKY, ImageIO.read(new File("images/ball/sticky.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Ball(PlayField playField, BallsStorage ballsStorage) {
        // #TODO: somehow fix that small angles don't work at low speed
        super(
                playField,
                images.get(Type.DEFAULT),
			/* #TODO: fix. this is supposed to spawn ball at the racket location, but it works once, because balls
			created all at once so it does not mention current racket location. mb do it when new ball is spawned
			 */
                (new Rectangle((int) playField.getRacket().getBounds().getCenterX(),
                        (int) playField.getRacket().getBounds().getY() - images.get(Type.DEFAULT).getHeight(),
                        images.get(Type.DEFAULT).getWidth(), images.get(Type.DEFAULT).getHeight())),
                270,
                3);
        this.ballsStorage = ballsStorage;
        this.playField = playField;
        thread = new Thread(this, "ball thread");
        thread.start();
    }

    public void updateBounds() {
        this.bounds = new Rectangle((int) playField.getRacket().getBounds().getCenterX(),
                (int) playField.getRacket().getBounds().getY() - images.get(Type.DEFAULT).getHeight(),
                images.get(Type.DEFAULT).getWidth(), images.get(Type.DEFAULT).getHeight());
    }

    public void setBounds(Rectangle newBounds) {
        this.bounds = newBounds;
    }

    public void move() {
        if (!isMoving)
            return;

        Rectangle playFieldBoundary = playField.getBoundary();

        prevPos = bounds;
        bounds.translate((int) Math.round(velocity.getSpeedX()), (int) Math.round(velocity.getSpeedY()));

        if (bounds.x <= playFieldBoundary.x) {
            bounds.x = playFieldBoundary.x;
            velocity.reverseX();
        } else if (bounds.x + bounds.width >= playFieldBoundary.width + playFieldBoundary.x) {
            bounds.x = playFieldBoundary.x + playFieldBoundary.width - bounds.width;
            velocity.reverseX();
        } else if (bounds.y <= playFieldBoundary.y) {
            bounds.y = playFieldBoundary.y;
            velocity.reverseY();
        } else if (bounds.y + bounds.height > playFieldBoundary.y + playFieldBoundary.height) {
            isDead = true;
        }
        handleCollisions();
    }

    private void handleCollisions() {
        ArrayList<Sprite> collisionWith = collideWith();
        if (collisionWith.size() != 0) {
            bounds = prevPos;
            for (Sprite sprite : collisionWith)
                sprite.hitBy(this);
        }
    }

    public void run() {
        while (!playField.getThread().isAlive()) {

        }
        while (!isDead) {
            move();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        PlayField.playAudio("audio/death.wav");
        ballsStorage.remove(this);
        playField.setBall(ballsStorage.getFirst());
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public void hitBy(Ball ball) {

    }
}