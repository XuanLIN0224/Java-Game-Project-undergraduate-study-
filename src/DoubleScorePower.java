import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

public class DoubleScorePower extends Power{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    String imageName;
    final private Image doubleScorePower;


    public DoubleScorePower(double x, double y) {
        super(x, y);
        horizontalMoveSpeed = Integer.parseInt(game_props.getProperty("gameObjects.doubleScore.speed"));
        imageName = game_props.getProperty("gameObjects.doubleScore.image");
        radius = Double.parseDouble(game_props.getProperty("gameObjects.doubleScore.radius"));
        maxFrames = Integer.parseInt(game_props.getProperty("gameObjects.doubleScore.maxFrames"));
        doubleScorePower = new Image(imageName);
        active = false;
        verticalSpeed = 0;
        framesActive = 0;
    }
    @Override
    public void resetObject() {
        super.resetObject();
        active = false;
    }

    public double getX_boundary() {
        return x + radius;
    }

    public double getY_boundary() {
        return y + radius;
    }
    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
    public void setActive(){
        active = true;
    }


    public void update(Input input) {
        if (active){
            framesActive++;
            if (framesActive > 500){
                active = false;
            }
        }
        if (input.isDown(Keys.LEFT) && x < original_x && !isPlayerDead) {
            x += horizontalMoveSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalMoveSpeed;
        }
        y += verticalSpeed;
        doubleScorePower.draw(x,y);
    }
}
