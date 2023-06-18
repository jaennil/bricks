import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Racket extends MovableSprite implements Runnable, KeyListener {
	static final int DELAY = 1;
	private Thread thread;
	private boolean leftReleased = true;
	private boolean rightReleased = true;

	private final static BufferedImage image;

	static {
		try {
			image = ImageIO.read(new File("images/racket.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Racket(PlayField playField) {
		super(
			playField,
			image,
			new Rectangle(
			playField.getWidth() / 2,
			playField.getHeight() - 20,
			image.getWidth(playField),
			image.getHeight(playField)
			),
			0,
			1);
		playField.getWindow().addKeyListener(this);
		while(true) {
			if (playField.getThread().isAlive()) {
				break;
			}
		}
		thread = new Thread(this, "racket thread");
		thread.start();
	}

	public void move() {
		if (isMoving) {
			Rectangle b = playField.getBoundary();
			bounds.x += velocity.getSpeedX();
			
			if (bounds.x < b.x)
				bounds.x = b.x;
			else if (bounds.x + bounds.width > b.x + b.width)
				bounds.x = b.x + b.width - bounds.width;
		}
	}

	public void hitBy(Ball ball) {
		System.out.println(ball.getDirection());
		if ( ball.getDirection() == 90 ) {
			ball.setDirection(70);
		} else {
			int px = ball.getBounds().x + ball.getBounds().width/2;
			int l  = (int) (bounds.x + bounds.width*(1.0/3));
			int r  = (int) (bounds.x + bounds.width*(2.0/3));

			if ( px < l || px > r ) {
				ball.getVelocity().reverse();
			} else {
				ball.getVelocity().reverseY();
			}
		}
	}
	
	public void run() {
		while (true) {
//			System.out.println("racket thread running...");
			move();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			leftReleased = false;
			startMoving();
			setDirection(180);
		}
		if (key == KeyEvent.VK_RIGHT) {
			rightReleased = false;
			startMoving();
			setDirection(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			leftReleased = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			rightReleased = true;
		}
		if (leftReleased && rightReleased)
			stopMoving();
	}
}