import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
/**
 * Code for Invincible Power
 * written by
 * @xulin2
 */
public class InvinciblePower extends Power{
    private final String IMAGE_NAME;
    private final Image INVINCIBLE_POWER;
    /**
     * The constructor
     */
    public InvinciblePower(double x, double y) {
        super(x, y);
        horizontalSpeed = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.invinciblePower.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.invinciblePower.image");
        radius = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.invinciblePower.radius"));
        maxFrames = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.invinciblePower.maxFrames"));
        INVINCIBLE_POWER = new Image(IMAGE_NAME);
        active = false;
        verticalSpeed = 0;
        framesActive = 0;
    }

    /**
     * get the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * set the vertical speed
     */
    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    /**
     * the invincible power moves with the player
     */
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += horizontalSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalSpeed;
        }
    }

    /**
     * Performs a state update.
     * Allows the power to become active when collides with player
     * Allows the power to fly up when collides with player
     */
    public void update(Input input, Level level) {
        if (active){
            framesActive++;
            if (framesActive > MAX_ACTIVE_FRAMES){
                active = false;
            }
        }
        // if the player collides with invincible power
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), getRadius())) {
            setVerticalSpeed(COLLISION_SPEED);
            level.setInvinciblePowerActive(true);
        }
        move(input);
        y += verticalSpeed;
        INVINCIBLE_POWER.draw(x,y);
    }
}
