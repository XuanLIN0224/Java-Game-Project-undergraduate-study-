import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Random;

import java.util.Properties;

public class FlyingPlatform extends GameObject implements Moveable{
    private final String IMAGE_NAME;
    private final Image FLYING_PLATFORM;
    private final int HORIZONTAL_SPEED;
    private final int HALF_LENGTH;
    private final int HALF_HEIGHT;
    private final int MAX_RANDOM_X_DISPLACEMENT;
    private int direction;
    private double distanceMoved;
    private boolean isPlayerOn;
    private final Random RANDOM = new Random();
    public FlyingPlatform(double x, double y) {
        super(x, y);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.flyingPlatform.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.flyingPlatform.image");
        FLYING_PLATFORM = new Image(IMAGE_NAME);
        HALF_LENGTH = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.flyingPlatform.halfLength"));
        HALF_HEIGHT = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.flyingPlatform.halfHeight"));
        MAX_RANDOM_X_DISPLACEMENT = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        direction = RANDOM.nextBoolean() ? 1 : -1;
        isPlayerOn = false;
    }

    public int getHalfLength() {
        return HALF_LENGTH;
    }

    public int getHalfHeight() {
        return HALF_HEIGHT;
    }

    public boolean isPlayerOn() {
        return isPlayerOn;
    }

    public void setPlayerOn(boolean playerOn) {
        isPlayerOn = playerOn;
    }
    private void moveRandomly() {
        if (distanceMoved >= MAX_RANDOM_X_DISPLACEMENT) {
            // Reverse direction
            direction = -direction;
            distanceMoved = 0;
        }

        if (direction == 1) {
            x += direction;
            distanceMoved +=direction;
        }
        else if (direction == -1){
            x += direction;
            distanceMoved += (-direction);
        }
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }
    public void update(Input input) {
        move(input);
        moveRandomly();
        FLYING_PLATFORM.draw(x,y);
    }
}
