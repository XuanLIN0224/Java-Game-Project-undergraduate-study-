import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
public class Player extends GameObject implements Shoot{
    private final String IMAGE_NAME_RIGHT;
    private final String IMAGE_NAME_LEFT;
    private final Image PLAYER_RIGHT;
    private final Image PLAYER_LEFT;
    private final double RADIUS;
    private final double PLATFORM_Y;
    private boolean turnLeft;
    private boolean onGround;
    private boolean isJumping;
    private boolean isDead;
    private double verticalSpeed;
    private boolean isOnFlyingPlatform;
    private boolean shootFireBall;
    public Player(double x, double y) {
        super(x, y);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.player.radius"));
        IMAGE_NAME_RIGHT = GAME_PROPS.getProperty("gameObjects.player.imageRight");
        IMAGE_NAME_LEFT = GAME_PROPS.getProperty("gameObjects.player.imageLeft");
        PLAYER_RIGHT = new Image(IMAGE_NAME_RIGHT);
        PLAYER_LEFT = new Image(IMAGE_NAME_LEFT);
        verticalSpeed = 0;
        this.PLATFORM_Y = y;
        turnLeft = false;
        onGround = true;
        isDead = false;
        isJumping = false;
        isOnFlyingPlatform = false;
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public void setVerticalSpeed(double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setDead() {
        isDead = true;
    }

    public boolean isOnFlyingPlatform() {
        return isOnFlyingPlatform;
    }

    public void setOnFlyingPlatform(boolean onFlyingPlatform) {
        isOnFlyingPlatform = onFlyingPlatform;
    }

    public boolean isShootFireBall() {
        return shootFireBall;
    }

    public boolean isTurnLeft() {
        return turnLeft;
    }

    public void setShootFireBall(boolean shootFireBall) {
        this.shootFireBall = shootFireBall;
    }

    @Override
    public void resetObject() {
        super.resetObject();
        verticalSpeed= 0;
        isDead = false;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isOnGround() {
        return onGround;
    }


    public void update(Input input) {
        if (input.isDown(Keys.LEFT) && !isDead) {
            turnLeft = true;
        }
        if (input.isDown(Keys.RIGHT) && !isDead) {
            turnLeft = false;
        }
        if (!isJumping && input.isDown(Keys.UP) && onGround && !isDead) {
            isJumping = true;
            onGround = false;
            verticalSpeed = -20;
        }
        if (!isJumping && input.isDown(Keys.UP) && isOnFlyingPlatform && !isDead) {
            isJumping = true;
            isOnFlyingPlatform = false;
            verticalSpeed = -20;
        }
        if (input.wasPressed(Keys.S) && !isDead && !shootFireBall){
            shootFireBall = true;
        }

        if (isJumping) {
            y += verticalSpeed;
            verticalSpeed += 1;

            if (y >= PLATFORM_Y) {
                y = PLATFORM_Y;
                verticalSpeed = 0;
                onGround = true;
                isJumping = false;
            }


        }
        if (isDead){
            y += 2;
        }
        if (turnLeft) {
            PLAYER_LEFT.draw(x, y);
        } else {
            PLAYER_RIGHT.draw(x, y);
        }
    }
}








