import bagel.Font;
import bagel.Input;
import bagel.util.Colour;
import java.util.Properties;
import bagel.DrawOptions;

public class enemyBossHealth extends Health {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private int HealthFontSize;
    private Font font;
    private int Health_x;
    private int Health_y;

    public enemyBossHealth() {
        super();
        HealthFontSize = Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize"));
        font = new Font(FontSource, HealthFontSize);
        Health_x = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        Health_y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));
    }

    public void update() {
        String scoreText = healthMessage + (int)(health*100);
        DrawOptions options = new DrawOptions();
        font.drawString(scoreText, Health_x, Health_y, options.setBlendColour(Colour.RED));
    }
}
