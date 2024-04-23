import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Map;
import java.util.Properties;

public class Platform extends GameObject implements Moveable {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image platform;
    private int horizontalMoveSpeed;
    public Platform(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.platform.speed"));
        imageName = game_props.getProperty("gameObjects.platform.image");
        platform = new Image(imageName);
    }

    public void update(Input input) {
        move(input);
        platform.draw(x,y);
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
    }
}
