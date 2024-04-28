import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

public class InvinciblePower extends Power implements Moveable{
    private final Properties GAME_PROPS;
    private final String IMAGE_NAME;
    private final Image INVINCIBLE_POWER;

    public InvinciblePower(double x, double y) {
        super(x, y);
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        horizontalSpeed = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.invinciblePower.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.invinciblePower.image");
        radius = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.invinciblePower.radius"));
        maxFrames = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.invinciblePower.maxFrames"));
        INVINCIBLE_POWER = new Image(IMAGE_NAME);
        active = false;
        verticalSpeed = 0;
        framesActive = 0;
    }
    @Override
    public void resetObject() {
        super.resetObject();
        active = false;
    }

    public double getRadius() {
        return radius;
    }
    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
    public void move(Input input){
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalSpeed;
        }
    }

    public void update(Input input) {
        if (active){
            framesActive++;
            if (framesActive > MAX_ACTIVE_FRAMES){
                active = false;
            }
        }
        move(input);
        y += verticalSpeed;
        INVINCIBLE_POWER.draw(x,y);
    }

}
