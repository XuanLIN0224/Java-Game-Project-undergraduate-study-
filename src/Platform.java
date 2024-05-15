import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Map;
import java.util.Properties;
/**
 * Code for platform
 * written by
 * @xulin2
 */
public class Platform extends GameObject {
    private final String IMAGE_NAME;
    private final Image PLATFORM;
    private final int HORIZONTAL_SPEED;
    /**
     * The constructor
     */
    public Platform(double x, double y) {
        super(x, y);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.platform.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.platform.image");
        PLATFORM = new Image(IMAGE_NAME);
    }

    /**
     * The platform can move with the player
     */
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }

    /**
     * Performs a state update.
     * display on screen
     */
    public void update(Input input, Level level) {
        move(input);
        PLATFORM.draw(x,y);
    }
}
