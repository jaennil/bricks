import javax.imageio.ImageIO;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Racket extends MovableSprite implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	static final int DELAY = 4;
	private Thread thread;
	private boolean leftReleased = true;
	private boolean rightReleased = true;
	public boolean spacePressed = false;
	private Ball abilityBall;
	public boolean abilityUsed = false;
	private int xToDrag = -1;

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
			playField.getWidth() / 2 - image.getWidth()/2,
			playField.getHeight() - 20,
			image.getWidth(playField),
			image.getHeight(playField)
			),
			0,
			1);
		playField.getWindow().addKeyListener(this);
		playField.addMouseMotionListener(this);
		playField.addMouseListener(this);
		thread = new Thread(this, "racket thread");
		thread.start();
	}

	public void move() {
		// #TODO: better naming
		if (!isMoving)
			return;
		Rectangle b = playField.getBoundary();
		bounds.x += velocity.getSpeedX();
		if (abilityBall != null) {
			Rectangle ballBounds = abilityBall.getBounds();
			Rectangle newBounds = new Rectangle(ballBounds.x+(int)velocity.getSpeedX(), ballBounds.y+(int)velocity.getSpeedY(), ballBounds.width, ballBounds.height);
			abilityBall.setBounds(newBounds);
		}

		if (bounds.x < b.x)
			bounds.x = b.x;
		else if (bounds.x + bounds.width > b.x + b.width)
			bounds.x = b.x + b.width - bounds.width;
	}

	public void hitBy(Ball ball) {
		if (spacePressed) {
			if (!abilityUsed) {
				ball.stopMoving();
				abilityBall = ball;
			}
		}
		Rectangle ballBounds = ball.getBounds();
		Rectangle racketBounds = getBounds();
		int factor = racketBounds.x + racketBounds.width - ballBounds.x - ballBounds.width;
		int angle = mapAngle(factor);
		ball.setDirection(angle);
	}

	public static int mapAngle(int input) {
		return (int) (input * 0.9) + 45;
	}

	public void run() {
		while(!playField.getThread().isAlive()) {
			// do nothing
			;
		}
		while (true) {
			move();
			Rectangle racketBounds = getBounds();
			if (xToDrag-3 <= racketBounds.getCenterX() && racketBounds.getCenterX() <= xToDrag+3 && leftReleased && rightReleased) {
				stopMoving();
			}
			if (racketBounds.getCenterX() > xToDrag && leftReleased && rightReleased) {
				setDirection(180);
			} else if (racketBounds.getCenterX() <= xToDrag && leftReleased && rightReleased){
				setDirection(0);
			}
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			leftReleased = false;
			setDirection(180);
			startMoving();
		}
		if (key == KeyEvent.VK_RIGHT) {
			rightReleased = false;
			setDirection(0);
			startMoving();
		}
		if (key == KeyEvent.VK_SPACE) {
			spacePressed = true;
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
		if (key == KeyEvent.VK_SPACE) {
			if (!abilityUsed) {
				if (abilityBall != null) {
					spacePressed = false;
					abilityBall.setDirection(90);
					abilityBall.startMoving();
					abilityBall = null;
					abilityUsed = true;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		xToDrag = e.getX();
		startMoving();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xToDrag = e.getX();
		startMoving();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		stopMoving();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics;
		if (abilityBall != null) {
			graphics2D.setStroke(new BasicStroke(2));
			graphics2D.setColor(Color.RED);
			graphics2D.drawLine((int) bounds.getCenterX(), (int) getBounds().getCenterY()-2, (int) bounds.getCenterX(), (int) bounds.getCenterY() - 10000);
		}
	}
}