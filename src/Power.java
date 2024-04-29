public abstract class Power extends GameObject{
    protected boolean active;
    protected double verticalSpeed;
    protected int horizontalSpeed;
    protected int maxFrames;
    protected double radius;
    protected int framesActive;
    public final int MAX_ACTIVE_FRAMES = 500;
    private final int COLLISION_SPEED = -10;

    public Power(double x, double y) {
        super(x, y);
        active = false;
    }

    public int getCOLLISION_SPEED() {
        return COLLISION_SPEED;
    }
}
