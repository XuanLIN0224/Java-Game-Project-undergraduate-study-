import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Coin extends GameObject implements Moveable {
    private final Properties GAME_PROPS;
    private final String IMAGE;
    private final Image COIN;
    private final double RADIUS;
    private final int COLLISION_SPEED = -10;
    private final int HORIZONTAL_SPEED;
    private int value;
    private int verticalSpeed;
    private boolean isCollided = false;
    public Coin(double x, double y) {
        super(x, y);
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        IMAGE = GAME_PROPS.getProperty("gameObjects.coin.image");
        COIN = new Image(IMAGE);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.coin.radius"));
        value = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.value"));
        verticalSpeed = 0;
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.speed"));
    }


    public double getX_boundary() {
        return x + RADIUS;
    }
    public double getY_boundary() {
        return y + RADIUS;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public void setVerticalMoveSpeed(int verticalMoveSpeed) {
        this.verticalSpeed = verticalMoveSpeed;
    }
    @Override
    public void resetObject() {
        super.resetObject();
        verticalSpeed= 0;
        value = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.value"));
        isPlayerDead = false;
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
        y += verticalSpeed;
        COIN.draw(x,y);
    }

}
