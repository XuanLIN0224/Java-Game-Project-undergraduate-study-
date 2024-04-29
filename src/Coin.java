import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Coin extends GameObject implements Moveable {
    private final String IMAGE_NAME;
    private final Image COIN;
    private final double RADIUS;
    private final int COLLISION_SPEED = -10;
    private final int HORIZONTAL_SPEED;
    private final int ZERO_VALUE = 0;
    private int value;
    private int verticalSpeed;
    public Coin(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.coin.image");
        COIN = new Image(IMAGE_NAME);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.coin.radius"));
        value = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.value"));
        verticalSpeed = 0;
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.speed"));
    }


    public int getCOLLISION_SPEED() {
        return COLLISION_SPEED;
    }

    public int getZERO_VALUE() {
        return ZERO_VALUE;
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
        y += verticalSpeed;
        COIN.draw(x,y);
    }

}
