import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EndFlag extends GameObject implements Moveable{
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final String IMAGE_NAME;
    private final double RADIUS;
    private final Image ENDFLAG;
    private final int HORIZONTAL_SPEED;

    public EndFlag(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.endFlag.image");
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.endFlag.radius"));
        ENDFLAG = new Image(IMAGE_NAME);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.endFlag.speed"));
    }

    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
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
        ENDFLAG.draw(x,y);
    }
}
