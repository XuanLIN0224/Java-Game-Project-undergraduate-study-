import bagel.Image;
import bagel.Input;
import bagel.Keys;
/**
 * Code for Coin
 * written by
 * @xulin2
 */
public class Coin extends GameObject{
    private final String IMAGE_NAME;
    private final Image COIN;
    private final double RADIUS;
    private final int COLLISION_SPEED = -10;
    private final int HORIZONTAL_SPEED;
    private final int ZERO_VALUE = 0;
    private int value;
    private int verticalSpeed;
    /**
     * The constructor
     */
    public Coin(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.coin.image");
        COIN = new Image(IMAGE_NAME);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.coin.radius"));
        value = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.value"));
        verticalSpeed = 0;
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.speed"));
    }

    /**
     * get the value of the coin
     */
    public int getValue() {
        return value;
    }

    /**
     * set the value of the coin
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * set the vertical move speed
     */
    public void setVerticalMoveSpeed(int verticalMoveSpeed) {
        this.verticalSpeed = verticalMoveSpeed;
    }
    /**
     * the coin moves with the player
     */
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }

    public void update(Input input, Level level) {
        move(input);
        //if the player collides with a coin, update score
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), RADIUS)) {
            setVerticalMoveSpeed(COLLISION_SPEED);
            if (level.isDoubleScorePowerActive()){
                level.getSCORE().updateScore(getValue() * 2);
            }
            else {
                level.getSCORE().updateScore(getValue());
            }
            setValue(ZERO_VALUE);
        }
        y += verticalSpeed;
        COIN.draw(x,y);
    }

}
