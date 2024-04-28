import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Random;

import java.util.Properties;

public class EnemyBoss extends GameObject implements Shoot, Moveable{
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final String IMAGE_NAME;
    private final Image ENEMYBOSS;
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
    public EnemyBoss(double x, double y) {
        super(x, y);
        HORIZONTAL_SPEED = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyBoss.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.enemyBoss.image");
        ENEMYBOSS = new Image(IMAGE_NAME);
        RADIUS = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemyBoss.radius"));
        ACTIVATION_RADIUS = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyBoss.activationRadius"));
        shootFireBall = false;
        inActiveRadius = false;
        countFrame = 0;
        randomShoot = RANDOM.nextBoolean();
        VERTICAL_SPEED = 2;
    }

    public double getRADIUS() {
        return RADIUS;
    }

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

    public void setDead() {
        isDead = true;
    }

    public boolean isShootFireBall() {
        if (shootFireBall){
            shootFireBall = randomShoot;
            randomShoot = RANDOM.nextBoolean();
        }
        return shootFireBall;
    }

    public void setShootFireBall(boolean shootFireBall) {
        this.shootFireBall = shootFireBall;
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += HORIZONTAL_SPEED;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= HORIZONTAL_SPEED;
        }
    }
    public void update(Input input) {
        if (!isDead){
            if (!shootFireBall && inActiveRadius){
                System.out.println(countFrame);
                countFrame += 1;
                shootFireBall = false;
            }
            move(input);
        }
        else {
            y += VERTICAL_SPEED;
        }
        ENEMYBOSS.draw(x,y);
    }
}
