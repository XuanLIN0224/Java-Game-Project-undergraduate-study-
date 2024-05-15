import bagel.Input;
import bagel.Keys;

import java.util.Properties;
/**
 * Code for the parent class GameObject
 * written by
 * @xulin2
 */
public abstract class GameObject {
    /**
     * GAME_PROPS can be reused in the subclasses therefore it is protected
     */
    protected final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    /**
     * x needs to be changing in the subclasses therefore it is protected
     */
    protected double x;
    /**
     * y needs to be changing in the subclasses therefore it is protected
     */
    protected double y;
    /**
     * original_x needs to be used in the subclasses therefore it is protected
     */
    protected final double ORIGINAL_X;
    /**
     * isPlayerDead needs to be changing in the subclasses therefore it is protected
     */
    protected boolean isPlayerDead = false;
    /**
     * The constructor
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.ORIGINAL_X = x;
    }

    /**
     * get the x value
     */
    public double getX() {
        return x;
    }

    /**
     * get the y value
     */
    public double getY() {
        return y;
    }
    /**
     * set the player to be dead
     */
    public void setPlayerDead() {
        isPlayerDead = true;
    }

    /**
     * Performs a state update.
     */
    public abstract void update(Input input, Level level);
}
