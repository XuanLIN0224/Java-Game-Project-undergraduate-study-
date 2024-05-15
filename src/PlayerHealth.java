import bagel.Font;
import bagel.Input;

import java.util.Properties;
/**
 * Code for player health
 * written by
 * @xulin2
 */
public class PlayerHealth extends Health{
    private final int HEALTH_FONT_SIZE;
    private final Font FONT;
    private final int HEALTH_X;
    private final int HEALTH_Y;
    /**
     * The constructor
     */
    public PlayerHealth() {
        super();
        HEALTH_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.fontSize"));
        FONT = new Font(FONT_SOURCE, HEALTH_FONT_SIZE);
        HEALTH_X = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.x"));
        HEALTH_Y = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.y"));
    }
    /**
     * Performs a state update.
     * display player health on screen
     */
    public void update() {
        String scoreText = HEALTH_MESSAGE + (int)(getHealth()*100);
        FONT.drawString(scoreText, HEALTH_X, HEALTH_Y);
    }
}
