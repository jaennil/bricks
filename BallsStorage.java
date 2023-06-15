class BallsStorage {
    private Ball[] balls;
    private int amount;

    public BallsStorage(PlayField playField, int amount) {
        balls = new Ball[amount];

        for (int i = 0; i < amount; i++)
            balls[i] = new Ball(playField, this);

        this.amount = amount;
    }

    public int size() {
        return amount;
    }

    public Ball get() {
        return amount > 0 ? balls[--amount] : null;
    }
}