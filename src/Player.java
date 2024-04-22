import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
public class Player extends GameObject {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName_right;
    String imageName_left;
    private Image player_right;
    private Image player_left;
    private double radius;
    private double platformY;
    private boolean turnLeft;
    private boolean onGround;
    private boolean isJumping;
    private boolean isFalling;
    private boolean isDead;
    private double verticalSpeed;
    private boolean isOnFlyingPlatform;
    public Player(double x, double y) {
        super(x, y);
        radius = Double.parseDouble(game_props.getProperty("gameObjects.player.radius"));
        imageName_right = game_props.getProperty("gameObjects.player.imageRight");
        imageName_left = game_props.getProperty("gameObjects.player.imageLeft");
        player_right = new Image(imageName_right);
        player_left = new Image(imageName_left);
        verticalSpeed = 0;
        this.platformY = y;
        turnLeft = false;
        onGround = true;
        isDead = false;
        isJumping = false;
        isOnFlyingPlatform = false;
    }

    public double getX_boundary() {
        return x + radius;
    }

    public double getY_boundary() {
        return y + radius;
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

    @Override
    public void resetObject() {
        super.resetObject();
        verticalSpeed= 0;
        isDead = false;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
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

        if (isJumping) {
            y += verticalSpeed;
            verticalSpeed += 1;

            if (y >= platformY) {
                y = platformY;
                verticalSpeed = 0;
                onGround = true;
                isJumping = false;
            }


        }
        if (isDead){
            y += 2;
        }
        if (turnLeft) {
            player_left.draw(x, y);
        } else {
            player_right.draw(x, y);
        }
    }
}








