import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
/**
 * Code for Double Score Power
 * written by
 * @xulin2
 */
public class DoubleScorePower extends Power{
    private final String IMAGE_NAME;
    private final Image DOUBLE_SCORE_POWER;
    /**
     * The constructor
     */
    public DoubleScorePower(double x, double y) {
        super(x, y);
        horizontalSpeed = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.doubleScore.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.doubleScore.image");
        radius = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.doubleScore.radius"));
        maxFrames = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.doubleScore.maxFrames"));
        DOUBLE_SCORE_POWER = new Image(IMAGE_NAME);
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
     * the double score power moves with the player
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
        //if the player collides with a DoubleScorePower
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), getRadius())) {
            setVerticalSpeed(COLLISION_SPEED);
            level.setDoubleScorePowerActive(true);
        }
        move(input);
        y += verticalSpeed;
        DOUBLE_SCORE_POWER.draw(x,y);
    }
}
