class Velocity {

	private double dx, dy;
	private int speed;

	public Velocity(int direction, int speed) {
		this.speed = speed;
		setDirection(direction);
	}

	public void setDirection(int angle) {
		dx = Math.cos(Math.toRadians(angle)) * (double) speed;
		dy = -Math.sin(Math.toRadians(angle)) * (double) speed;
	}

	public int getDirection() {
		return (int)Math.toDegrees(Math.asin(dy/(double)speed));
	}

	public void reverseX() {
		dx = -dx;
	}

	public void reverse() {
		dx = -dx;
		dy = -dy;
	}

	public void reverseY() {
		dy = -dy;
	}

	public int getSpeed() {
		return speed;
	}

	public double getSpeedX() {
		return dx;
	}

	public double getSpeedY() {
		return dy;
	}
}