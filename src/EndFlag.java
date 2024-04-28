import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EndFlag extends GameObject implements Moveable{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private String imageName;
    private final double RADIUS;
    private Image endFlag;
    private int horizontalMoveSpeed;

    public EndFlag(double x, double y) {
        super(x, y);
        imageName = game_props.getProperty("gameObjects.endFlag.image");
        RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.endFlag.radius"));
        endFlag = new Image(imageName);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.endFlag.speed"));
    }

    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public void update(Input input) {
        move(input);
        endFlag.draw(x,y);
    }
}
