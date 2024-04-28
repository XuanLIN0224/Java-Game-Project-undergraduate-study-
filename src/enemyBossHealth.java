import bagel.Font;
import bagel.Input;
import bagel.util.Colour;
import java.util.Properties;
import bagel.DrawOptions;

public class enemyBossHealth extends Health {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private final int HEALTH_FONT_SIZE;
    private final Font FONT;
    private final int HEALTH_X;
    private final int HEALTH_Y;

    public enemyBossHealth() {
        super();
        HEALTH_FONT_SIZE = Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize"));
        FONT = new Font(FontSource, HEALTH_FONT_SIZE);
        HEALTH_X = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        HEALTH_Y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));
    }

    public void update() {
        String scoreText = healthMessage + (int)(health*100);
        DrawOptions options = new DrawOptions();
        FONT.drawString(scoreText, HEALTH_X, HEALTH_Y, options.setBlendColour(Colour.RED));
    }
}
