import bagel.Image;
import bagel.util.Point;
import java.util.Properties;

public class Fireball {
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final String IMAGE_NAME;
    final private Image fireBall;
    private double x;
    private double y;
    private final double HORIZONTAL_SPEED;
    private double damageSize;
    private final double RADIUS;
    private boolean isActive;
    private final int DIRECTION;

    public Fireball(double x, double y, int direction) {
        this.x = x;
        this.y = y;
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.fireball.image");
        fireBall = new Image(IMAGE_NAME);
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
        return y;
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
            x += HORIZONTAL_SPEED * DIRECTION;
            fireBall.draw(x, y);
        }
        else {
            damageSize = 0;
        }
    }
}
