import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Random;

import java.util.Properties;
/**
 * Code for EnemyBoss
 * written by
 * @xulin2
 */
public class EnemyBoss extends GameObject implements Shoot{
    private final String IMAGE_NAME;
    private final Image ENEMY_BOSS;
    private final int HORIZONTAL_SPEED;
    private final double RADIUS;
    private final int ACTIVATION_RADIUS;
    private boolean shootFireBall;
    private boolean inActiveRadius;
    private int countFrame;
    private final Random RANDOM = new Random();
    private boolean randomShoot;
    private boolean isDead;
    private final int VERTICAL_SPEED;
    /**
     * The constructor
     */
    public EnemyBoss(double x, double y) {
        super(x, y);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyBoss.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.enemyBoss.image");
        ENEMY_BOSS = new Image(IMAGE_NAME);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemyBoss.radius"));
        ACTIVATION_RADIUS = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyBoss.activationRadius"));
        shootFireBall = false;
        inActiveRadius = false;
        countFrame = 0;
        randomShoot = RANDOM.nextBoolean();
        VERTICAL_SPEED = 2;
    }
    /**
     * get the radius
     */
    public double getRADIUS() {
        return RADIUS;
    }

    /**
     * check if the player is in the active radius
     */
    public boolean isInActivationRadius(Player player) {
        if (((x - player.getX()) <= ACTIVATION_RADIUS)){
            inActiveRadius = true;
            if (countFrame == 100){
                shootFireBall = true;
                countFrame = 0;
            }
        }
        else {
            inActiveRadius = false;
        }
        return inActiveRadius;
    }
    /**
     * set the boolean siDead
     */
    public void setDead() {
        isDead = true;
    }

    /**
     * get the boolean shootFireBall
     */
    public boolean isShootFireBall() {
        if (shootFireBall){
            shootFireBall = randomShoot;
            randomShoot = RANDOM.nextBoolean();
        }
        return shootFireBall;
    }
    /**
     * set the boolean shootFireBall
     */
    public void setShootFireBall(boolean shootFireBall) {
        this.shootFireBall = shootFireBall;
    }

    /**
     * the enemyBoss should move with the player
     */
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }
    /**
     * Performs a state update.
     * Allows to shoot fireball when the player is in the active radius
     */
    public void update(Input input, Level level) {
        if (!isDead){
            if (!shootFireBall && inActiveRadius){
                countFrame += 1;
            }
            move(input);
        }
        else {
            y += VERTICAL_SPEED;
        }
        ENEMY_BOSS.draw(x,y);
    }
}
