import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

public class InvinciblePower extends Power implements Moveable{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private String imageName;
    final private Image invinciblePower;

    public InvinciblePower(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.invinciblePower.speed"));
        imageName = game_props.getProperty("gameObjects.invinciblePower.image");
        radius = Double.parseDouble(game_props.getProperty("gameObjects.invinciblePower.radius"));
        maxFrames = Integer.parseInt(game_props.getProperty("gameObjects.invinciblePower.maxFrames"));
        invinciblePower = new Image(imageName);
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
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
    }

    public void update(Input input) {
        if (active){
            framesActive++;
            if (framesActive > 500){
                active = false;
            }
        }
        move(input);
        y += verticalSpeed;
        invinciblePower.draw(x,y);
    }

}
