import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EndFlag extends GameObject implements Moveable{
    private final String IMAGE_NAME;
    private final double RADIUS;
    private final Image END_FLAG;
    private final int HORIZONTAL_SPEED;

    public EndFlag(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.endFlag.image");
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.endFlag.radius"));
        END_FLAG = new Image(IMAGE_NAME);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.endFlag.speed"));
    }

    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public void update(Input input) {
        move(input);
        END_FLAG.draw(x,y);
    }
}
