public abstract class Power extends GameObject{
    protected boolean active;
    protected double verticalSpeed;
    protected int horizontalMoveSpeed;
    protected int maxFrames;
    protected double radius;
    protected int framesActive;

    public Power(double x, double y) {
        super(x, y);
        active = false;
    }
}
