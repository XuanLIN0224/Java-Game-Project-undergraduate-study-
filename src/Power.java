/**
 * Code for the abstract parent class power
 * written by
 * @xulin2
 */
public abstract class Power extends GameObject{
    /**
     * active can be different inside the subclasses therefore it is protected.
     */
    protected boolean active;
    /**
     * verticalSpeed can be different inside the subclasses therefore it is protected.
     */
    protected double verticalSpeed;
    /**
     * horizontalSpeed can be different inside the subclasses therefore it is protected.
     */
    protected int horizontalSpeed;
    /**
     * maxFrames can be different inside the subclasses therefore it is protected.
     */
    protected int maxFrames;
    /**
     * radius can be different inside the subclasses therefore it is protected.
     */
    protected double radius;
    /**
     * frameActive can be different inside the subclasses therefore it is protected.
     */
    protected int framesActive;
    /**
     * MAX_ACTIVE_FRAMES will not change and can be accessed among different subclasses therefore it is protected.
     */
    protected final int MAX_ACTIVE_FRAMES = 500;
    /**
     * COLLISION_SPEED will not change and can be accessed among different subclasses therefore it is protected.
     */
    protected final int COLLISION_SPEED = -10;
    /**
     * The constructor
     */
    public Power(double x, double y) {
        super(x, y);
        active = false;
    }
}
