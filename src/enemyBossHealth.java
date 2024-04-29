import bagel.Font;
import bagel.util.Colour;
import java.util.Properties;
import bagel.DrawOptions;

public class enemyBossHealth extends Health {
    private final Properties GAME_PROPS;
    private final int HEALTH_FONT_SIZE;
    private final Font FONT;
    private final int HEALTH_X;
    private final int HEALTH_Y;

    public enemyBossHealth() {
        super();
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        HEALTH_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("enemyBossHealth.fontSize"));
        FONT = new Font(FONT_SOURCE, HEALTH_FONT_SIZE);
        HEALTH_X = Integer.parseInt(GAME_PROPS.getProperty("enemyBossHealth.x"));
        HEALTH_Y = Integer.parseInt(GAME_PROPS.getProperty("enemyBossHealth.y"));
    }

    public void update() {
        String scoreText = HEALTH_MESSAGE + (int)(health*100);
        DrawOptions options = new DrawOptions();
        FONT.drawString(scoreText, HEALTH_X, HEALTH_Y, options.setBlendColour(Colour.RED));
    }
}
