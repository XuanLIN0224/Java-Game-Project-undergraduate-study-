import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;

public class Enemy extends GameObject implements Moveable{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private String imageName;
    private Image enemy;
    private final double RADIUS;
    private int horizontalMoveSpeed;
    private double damage;
    private int direction; // Track the direction of movement
    private double distanceMoved; // Track the distance moved
    private int randomSpeed;
    Random random = new Random();

    public Enemy(double x, double y) {
        super(x, y);
        imageName = game_props.getProperty("gameObjects.enemy.image");
        enemy = new Image(imageName);
        RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.enemy.radius"));
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.enemy.speed"));
        damage = Double.parseDouble(game_props.getProperty("gameObjects.enemy.damageSize"));
        distanceMoved = 0;
        randomSpeed = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        direction = random.nextBoolean() ? randomSpeed : -randomSpeed;
    }

    @Override
    public void resetObject() {
        super.resetObject();
        damage = Double.parseDouble(game_props.getProperty("gameObjects.enemy.damageSize"));
        isPlayerDead = false;
    }
    public double getRADIUS() {
        return RADIUS;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
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

        move(input);

        enemy.draw(x, y);
    }
}
