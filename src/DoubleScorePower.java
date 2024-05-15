import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

public class DoubleScorePower extends Power{
    private final String IMAGE_NAME;
    private final Image DOUBLE_SCORE_POWER;


    public DoubleScorePower(double x, double y) {
        super(x, y);
        horizontalSpeed = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.doubleScore.speed"));
        IMAGE_NAME = GAME_PROPS.getProperty("gameObjects.doubleScore.image");
        radius = Double.parseDouble(GAME_PROPS.getProperty("gameObjects.doubleScore.radius"));
        maxFrames = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.doubleScore.maxFrames"));
        DOUBLE_SCORE_POWER = new Image(IMAGE_NAME);
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
        if (input.isDown(Keys.LEFT) && x < ORIGINAL_X && !isPlayerDead) {
            x += horizontalSpeed;
        }
        if (input.isDown(Keys.RIGHT) && !isPlayerDead) {
            x -= horizontalSpeed;
        }
    }


    public void update(Input input, Level level) {
        if (active){
            framesActive++;
            if (framesActive > MAX_ACTIVE_FRAMES){
                active = false;
            }
        }
        //if the player collides with a DoubleScorePower
        if (ShadowMario.isCollideWithPlayer(getX(), getY(), getRadius())) {
            setVerticalSpeed(getCOLLISION_SPEED());
            level.setDoubleScorePowerActive(true);
        }
        move(input);
        y += verticalSpeed;
        DOUBLE_SCORE_POWER.draw(x,y);
    }
}
