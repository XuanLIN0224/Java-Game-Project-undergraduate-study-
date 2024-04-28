public abstract class Power extends GameObject{
    protected boolean active;
    protected double verticalSpeed;
    protected int horizontalSpeed;
    protected int maxFrames;
    protected double radius;
    protected int framesActive;
    public final int MAX_ACTIVE_FRAMES = 500;

    public Power(double x, double y) {
        super(x, y);
        active = false;
    }
}
