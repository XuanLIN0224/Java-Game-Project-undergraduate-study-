import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EndFlag extends GameObject implements Moveable{
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
        endFlag.draw(x,y);
    }
}
