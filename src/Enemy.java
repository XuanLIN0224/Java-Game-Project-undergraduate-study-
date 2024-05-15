import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;

public class Enemy extends GameObject{
    private final String IMAGE_NAME;
    private final Image ENEMY;
    private final double RADIUS;
    private final int HORIZONTAL_SPEED;
    private double damage;
    private int direction; // Track the direction of movement
    private double distanceMoved; // Track the distance moved
    private final int RANDOM_SPEED;
    private Random RANDOM = new Random();

    public Enemy(double x, double y) {
        super(x, y);
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.enemy.image");
        ENEMY = new Image(IMAGE_NAME);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemy.radius"));
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemy.speed"));
        damage = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemy.damageSize"));
        distanceMoved = 0;
        RANDOM_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        direction = RANDOM.nextBoolean() ? RANDOM_SPEED : -RANDOM_SPEED;
    }

    @Override
    public void resetObject() {
        super.resetObject();
        damage = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemy.damageSize"));
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
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }

    public void update(Input input, Level level) {
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
        // if the player collides with an enemy
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), getRADIUS())) {
            if (!level.isInvinciblePowerActive()){
                level.getPLAYER_HEALTH().updateHealth(getDamage());
                setDamage(0);
            }
        }

        move(input);
        ENEMY.draw(x, y);
    }
}
