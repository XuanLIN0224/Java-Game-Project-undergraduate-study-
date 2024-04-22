import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Coin extends GameObject {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName = game_props.getProperty("gameObjects.coin.image");
    private Image coin;
    private double radius = Double.parseDouble(game_props.getProperty("gameObjects.coin.radius"));
    private int value = Integer.parseInt(game_props.getProperty("gameObjects.coin.value"));
    private int verticalMoveSpeed = 0;
    private int horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.coin.speed"));
    public Coin(double x, double y) {
        super(x, y);
        coin = new Image(imageName);
    }


    public double getX_boundary() {
        return x + radius;
    }
    public double getY_boundary() {
        return y + radius;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public void setVerticalMoveSpeed(int verticalMoveSpeed) {
        this.verticalMoveSpeed = verticalMoveSpeed;
    }
    @Override
    public void resetObject() {
        super.resetObject();
        verticalMoveSpeed= 0;
        value = Integer.parseInt(game_props.getProperty("gameObjects.coin.value"));
        isPlayerDead = false;
    }

    public void update(Input input) {
        //make it do not go out of range of the platform
        if (input.isDown(Keys.LEFT) && !isPlayerDead && x < original_x) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
        y += verticalMoveSpeed;
        coin.draw(x,y);
    }

}
