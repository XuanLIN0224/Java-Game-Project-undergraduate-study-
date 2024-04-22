import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EndFlag extends GameObject {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    private double radius;
    private Image endFlag;
    private int horizontalMoveSpeed;

    public EndFlag(double x, double y) {
        super(x, y);
        imageName = game_props.getProperty("gameObjects.endFlag.image");
        radius = Double.parseDouble(game_props.getProperty("gameObjects.endFlag.radius"));
        endFlag = new Image(imageName);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.endFlag.speed"));
    }
    public double getX_boundary() {
        return x+radius;
    }

    public double getY_boundary() {
        return y+radius;
    }

    public void update(Input input) {
        //make it do not go out of range of the platform
        if (input.isDown(Keys.LEFT) && !isPlayerDead && x < original_x) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
        endFlag.draw(x,y);
    }
}
