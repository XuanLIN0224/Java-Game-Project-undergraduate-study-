import bagel.Font;
import bagel.Input;

import java.util.Properties;

public class PlayerHealth extends Health{
    private final int HEALTH_FONT_SIZE;
    private final Font FONT;
    private final int HEALTH_X;
    private final int HEALTH_Y;

    public PlayerHealth() {
        super();
        HEALTH_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.fontSize"));
        FONT = new Font(FONT_SOURCE, HEALTH_FONT_SIZE);
        HEALTH_X = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.x"));
        HEALTH_Y = Integer.parseInt(GAME_PROPS.getProperty("playerHealth.y"));
    }

    public void update() {
        String scoreText = HEALTH_MESSAGE + (int)(health*100);
        FONT.drawString(scoreText, HEALTH_X, HEALTH_Y);
    }
}
