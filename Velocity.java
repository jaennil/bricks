class Velocity {

	private double dx, dy;
	private int speed;

	public Velocity(int direction, int speed) {
		this.speed = speed;
		setDirection(direction);
	}

	public void setDirection(int direction) {
		dx = Math.cos(Math.toRadians(direction)) * (double) speed;
		dy = Math.sin(Math.toRadians(direction)) * (double) speed;
	}

	public int getDirection() {
		return ((int) Math.toDegrees(Math.atan2(dy, dx)))%360; // maybe without 360
	}

	public void reverse() {
		dx = -dx;
		dy = -dy;
	}

	public void reverseX() {
		dx = -dx;
	}

	public void reverseY() {
		dy = -dy;
	}

	public int getSpeed() {
		return speed;
	}

	public int getSpeedX() {
		return (int) dx;
	}

	public int getSpeedY() {
		return (int) dy;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSpeedX(int speedX) {
		dx = speedX;
	}

	public void setSpeedY(int speedY) {
		dy = speedY;
	}
}