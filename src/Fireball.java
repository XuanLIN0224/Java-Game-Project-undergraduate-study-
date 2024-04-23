import bagel.Image;
import bagel.util.Point;
import java.util.Properties;

public class Fireball {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image fireBall;
    private double x;
    private double y;
    private double horizontalSpeed;
    private double damageSize;
    private double radius;
    private boolean isActive;
    private int direction;

    public Fireball(double x, double y, int direction) {
        this.x = x;
        this.y = y;
        imageName = game_props.getProperty("gameObjects.fireball.image");
        fireBall = new Image(imageName);
        horizontalSpeed = Double.parseDouble(game_props.getProperty("gameObjects.fireball.speed"));
        damageSize = Double.parseDouble(game_props.getProperty("gameObjects.fireball.damageSize"));
        radius = Double.parseDouble(game_props.getProperty("gameObjects.fireball.radius"));
        isActive = true;
        this.direction = direction;
    }

    public void update() {

        // Draw fireball if active
        if (isActive) {
            x += horizontalSpeed * direction;
            fireBall.draw(x, y);
        }
        else {
            damageSize = 0;
        }
    }
    public double getX_boundary() {
        return x + radius;
    }

    public double getY_boundary() {
        return y + radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getDamageSize() {
        return damageSize;
    }

}
