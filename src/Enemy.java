import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;

public class Enemy extends GameObject {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    private Image enemy;
    private double radius;
    private int horizontalMoveSpeed;
    private double damage;
    private int direction; // Track the direction of movement
    private double distanceMoved; // Track the distance moved
    Random random = new Random();

    public Enemy(double x, double y) {
        super(x, y);
        imageName = game_props.getProperty("gameObjects.enemy.image");
        enemy = new Image(imageName);
        radius = Double.parseDouble(game_props.getProperty("gameObjects.enemy.radius"));
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.enemy.speed"));
        damage = Double.parseDouble(game_props.getProperty("gameObjects.enemy.damageSize"));
        distanceMoved = 0;
        direction = random.nextBoolean() ? 1 : -1;
    }

    @Override
    public void resetObject() {
        super.resetObject();
        damage = Double.parseDouble(game_props.getProperty("gameObjects.enemy.damageSize"));
        isPlayerDead = false;
    }

    public double getX_boundary() {
        return x + radius;
    }

    public double getY_boundary() {
        return y + radius;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void update(Input input) {
        // Random movement behavior
        if (distanceMoved >= 50) {
            // Reverse direction
            direction = -direction;
            distanceMoved = 0;
        }

        if (direction == 1) {
            x += direction;
            distanceMoved +=direction;
        }
        else if (direction == -1){
            x += direction;
            distanceMoved += (-direction);
        }

        // Player-controlled movement behavior
        if (input.isDown(Keys.LEFT) && !isPlayerDead && x < original_x) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }

        enemy.draw(x, y);
    }
}
