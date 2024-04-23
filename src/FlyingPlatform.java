import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Random;

import java.util.Properties;

public class FlyingPlatform extends GameObject implements Moveable{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image flyingPlatform;
    private int horizontalMoveSpeed;
    private int halfLength;
    private int halfHeight;
    private int maxRandomDisplacementX;
    private int direction;
    private double distanceMoved;
    private boolean isPlayerOn;
    Random random = new Random();
    public FlyingPlatform(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.speed"));
        imageName = game_props.getProperty("gameObjects.flyingPlatform.image");
        flyingPlatform = new Image(imageName);
        halfLength = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.halfLength"));
        halfHeight = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        maxRandomDisplacementX = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        direction = random.nextBoolean() ? 1 : -1;
        isPlayerOn = false;
    }

    public int getHalfLength() {
        return halfLength;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public boolean isPlayerOn() {
        return isPlayerOn;
    }

    public void setPlayerOn(boolean playerOn) {
        isPlayerOn = playerOn;
    }
    private void moveRandomly() {
        if (distanceMoved >= maxRandomDisplacementX) {
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
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
    }
    public void update(Input input) {
        move(input);
        moveRandomly();
        flyingPlatform.draw(x,y);
    }
}
