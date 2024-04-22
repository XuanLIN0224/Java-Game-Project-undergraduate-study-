import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class EnemyBoss extends GameObject{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image enemyBoss;
    private int horizontalMoveSpeed;
    private double radius;
    private double health;
    private int activationRadius;
    public EnemyBoss(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.enemyBoss.speed"));
        imageName = game_props.getProperty("gameObjects.enemyBoss.image");
        enemyBoss = new Image(imageName);
        radius = Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.radius"));
        health = Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.health"));
        activationRadius = Integer.parseInt(game_props.getProperty("gameObjects.enemyBoss.activationRadius"));
    }

    public void update(Input input) {
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
        enemyBoss.draw(x,y);
    }
}
