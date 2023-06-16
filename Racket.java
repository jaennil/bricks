import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Racket extends MovableSprite implements KeyListener {
	static final int LEFT = 37;
	static final int RIGHT = 39;
	static final int ALPHA = 10;

	private final static BufferedImage image;

	static {
		try {
			image = ImageIO.read(new File("images/racket.gif"));
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
			10);
		
		this.playField.addKeyListener(this);
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
		if ( ball.getDirection() == 90 ) {
			ball.setDirection(270 + ALPHA);
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
	
	/* Обработка нажатия клавиши */
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == LEFT) {
			startMoving();
			velocity.setDirection(180);
		} else if (keyEvent.getKeyCode() == RIGHT) {
			startMoving();
			velocity.setDirection(0);
		}
	}
	
	/* Обработка отжатия клавиши */
	public void keyReleased(KeyEvent e) {
		stopMoving();
	}
	
	public void keyTyped(KeyEvent e) {
		;
	}
}