import bagel.Font;
import bagel.Input;
import bagel.util.Colour;
import java.util.Properties;
import bagel.DrawOptions;

public class enemyBossHealth {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private String FontSource;
    private String healthMessage;
    private int ScoreFontSize;
    private Font font;
    private int Health_x;
    private int Health_y;
    private double health;

    public enemyBossHealth() {
        FontSource = game_props.getProperty("font");
        healthMessage = message_props.getProperty("health");
        ScoreFontSize = Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize"));
        font = new Font(FontSource, ScoreFontSize);
        Health_x = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        Health_y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));
        health = 1;
    }
    public void updateHealth(double health) {
        this.health -= health;
    }
    public void resetHealth() {
        health = 1;
    }

    public double getHealth() {
        return health;
    }

    public void update(Input input) {
        String scoreText = healthMessage + (int)(health*100);
        DrawOptions options = new DrawOptions();
        font.drawString(scoreText, Health_x, Health_y, options.setBlendColour(Colour.RED));
    }
}
