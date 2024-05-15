import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;
/**
 * Code for EndFlag
 * written by
 * @xulin2
 */
public class EndFlag extends GameObject{
    private final String IMAGE_NAME;
    private final double RADIUS;
    private final Image END_FLAG;
    private final int HORIZONTAL_SPEED;
    /**
     * The constructor
     */
    public EndFlag(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.endFlag.image");
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.endFlag.radius"));
        END_FLAG = new Image(IMAGE_NAME);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.endFlag.speed"));
    }

    /**
     * The end flag moves with the player
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
     * Allows setting the game to win when collides with player
     */
    public void update(Input input, Level level) {
        move(input);
        //if the player collide with EndFlag, win the game
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), RADIUS)) {
            level.setWonGame(true);
        }
        END_FLAG.draw(x,y);
    }
}
