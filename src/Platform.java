import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Map;
import java.util.Properties;

public class Platform extends GameObject implements Moveable {
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final String IMAGE_NAME;
    private final Image PLATFORM;
    private final int HORIZONTAL_SPEED;
    public Platform(double x, double y) {
        super(x, y);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.platform.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.platform.image");
        PLATFORM = new Image(IMAGE_NAME);
    }

    public void update(Input input) {
        move(input);
        PLATFORM.draw(x,y);
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }
}
