import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Random;

import java.util.Properties;

public class EnemyBoss extends GameObject{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image enemyBoss;
    private int horizontalMoveSpeed;
    private double radius;
    private int activationRadius;
    private boolean shootFireBall;
    private boolean inActiveRadius;
    private int countFrame;
    private Random random = new Random();
    private boolean randomShoot;
    private boolean isDead;
    private int verticalSpeed;
    public EnemyBoss(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.enemyBoss.speed"));
        imageName = game_props.getProperty("gameObjects.enemyBoss.image");
        enemyBoss = new Image(imageName);
        radius = Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.radius"));
        activationRadius = Integer.parseInt(game_props.getProperty("gameObjects.enemyBoss.activationRadius"));
        shootFireBall = false;
        inActiveRadius = false;
        countFrame = 0;
        randomShoot = random.nextBoolean();
        verticalSpeed = 2;
    }

    public void update(Input input) {
        if (!isDead){
            if (!shootFireBall && inActiveRadius){
                System.out.println(countFrame);
                countFrame += 1;
                shootFireBall = false;
            }
            if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
                x += horizontalMoveSpeed;
            }
            if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
                x -= horizontalMoveSpeed;
            }
        }
        else {
            y += verticalSpeed;
        }
        enemyBoss.draw(x,y);

    }
    public double getX_boundary() {
        return x + radius;
    }

    public double getY_boundary() {
        return y + radius;
    }

    public boolean isInActivationRadius(Player player) {
        if (((x - player.getX()) <= activationRadius)){
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

    public void setActivationRadius(int activationRadius) {
        this.activationRadius = activationRadius;
    }

    public void setDead() {
        isDead = true;
    }

    public boolean isShootFireBall() {
        if (shootFireBall){
            shootFireBall = randomShoot;
            randomShoot = random.nextBoolean();
        }
        return shootFireBall;
    }

    public void setShootFireBall(boolean shootFireBall) {
        this.shootFireBall = shootFireBall;
    }
}
