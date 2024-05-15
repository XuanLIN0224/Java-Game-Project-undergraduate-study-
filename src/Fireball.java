import bagel.Image;
import bagel.Window;

import java.util.Properties;

public class Fireball {
    private final Properties GAME_PROPS;
    private final String IMAGE_NAME;
    private final Image FIREBALL;
    private double x;
    private final double Y;
    private final double HORIZONTAL_SPEED;
    private double damageSize;
    private final double RADIUS;
    private boolean isActive;
    private final int DIRECTION;

    public Fireball(double x, double y, int direction) {
        this.x = x;
        this.Y = y;
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.fireball.image");
        FIREBALL = new Image(IMAGE_NAME);
        HORIZONTAL_SPEED = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.fireball.speed"));
        damageSize = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.fireball.damageSize"));
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.fireball.radius"));
        isActive = true;
        this.DIRECTION = direction;
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return Y;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getDamageSize() {
        return damageSize;
    }
    public void update() {

        // Draw fireball if active
        if (isActive) {
            if ((getX() > Window.getWidth()) || (getX() < 0)){
                setActive(false);
            }

            x += HORIZONTAL_SPEED * DIRECTION;
            FIREBALL.draw(x, Y);
        }
        else {
            damageSize = 0;
        }
    }
}
