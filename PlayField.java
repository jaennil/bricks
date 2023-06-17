import javax.swing.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class PlayField extends Canvas implements Runnable {
	private Thread gameThread;
	private SpritesArray sprites;
	private Rectangle bounds;
	private Frame frame;
	private Image offImage;
	private Graphics offGraphics;
	private final int delay = 50;

	public PlayField(Frame frame) {
		sprites = new SpritesArray();
		this.frame = frame;
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(offImage, 0, 0, null);
	}

	@Override
	public void update(Graphics graphics) {
		if (offGraphics == null) {
			offImage = createImage(getWidth(), getHeight());
			offGraphics = offImage.getGraphics();
		}

		offGraphics.clearRect(0, 0, getWidth(), getHeight());
		sprites.draw(offGraphics);
		drawMessage(frame.getMessage());
		graphics.drawImage(offImage, 0, 0, null);
	}

	public void run() {
//		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//		long theStartTime = System.currentTimeMillis();
	
//		while (Thread.currentThread() == gameThread) {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sprites.update();
				repaint();
			}
		};
		Timer timer = new Timer(delay, actionListener);
		timer.start();
//			try {
//				theStartTime += delay;
//				Thread.sleep(Math.max(0, theStartTime - System.currentTimeMillis()));
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				break;
//			}
	}

	public void start() {
		if (gameThread == null) {
			bounds = new Rectangle(0, 0, getWidth(), getHeight());
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	void stop() {
		if (gameThread != null) {
			gameThread = null;
		}
	}

	public Frame getFrame() {
		return frame;
	}
  
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	public void clean() {
		sprites.clear();
	}

	public Sprite testCollision(Sprite sprite) {
		return sprites.testCollision(sprite);
	}

	private void drawMessage(String string) {
		offGraphics.drawString(string,getWidth()/2-20,getHeight()/2);
	}

	public Rectangle getBoundary() {
		return bounds;
	}
}