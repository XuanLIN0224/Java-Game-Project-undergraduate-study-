import java.util.ArrayList;
import java.util.Properties;

public class Level {
    private final Properties GAME_PROPS;
    public final int WINDOW_HEIGHT;
    private boolean isInvinciblePowerActive;
    private boolean isDoubleScorePowerActive;
    private int activeFramesInvincible;
    private int activeFramesDoubleScore;
    private boolean isFallingFromPlatform;
    public final int MAX_POWER_ACTIVE_FRAMES = 500;
    private boolean WonGame;
    private boolean GiveInstruction;
    private boolean isGameOver;
    private final PlayerHealth PLAYER_HEALTH;
    private final Score SCORE;
    private final enemyBossHealth ENEMY_BOSS_HEALTH;

    public Level() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        WINDOW_HEIGHT = Integer.parseInt(GAME_PROPS.getProperty("windowHeight"));
        isInvinciblePowerActive = false;
        isDoubleScorePowerActive = false;
        activeFramesInvincible = 0;
        activeFramesDoubleScore = 0;
        isFallingFromPlatform = false;
        WonGame = false;
        GiveInstruction = true;
        isGameOver = false;
        PLAYER_HEALTH = new PlayerHealth();
        SCORE = new Score();
        ENEMY_BOSS_HEALTH = new enemyBossHealth();
    }
    //set all the boolean variables to false and int variables to 0
    public void resetVariables(){
        setGiveInstruction(false);
        setGameOver(false);
        setWonGame(false);
        setInvinciblePowerActive(false);
        setActiveFramesInvincible(0);
        setDoubleScorePowerActive(false);
        setActiveFramesDoubleScore(0);
    }
    //check if the Powers are active or not
    public void checkPowersActive(){
        if (isDoubleScorePowerActive()){
            setActiveFramesDoubleScore(getActiveFramesDoubleScore() + 1);
            if (getActiveFramesDoubleScore() > MAX_POWER_ACTIVE_FRAMES){
                setDoubleScorePowerActive(false);
                setActiveFramesDoubleScore(0);
            }
        }

        if (isInvinciblePowerActive()){
            setActiveFramesInvincible(getActiveFramesInvincible() + 1);
            if (getActiveFramesInvincible() > MAX_POWER_ACTIVE_FRAMES){
                setInvinciblePowerActive(false);
                setActiveFramesInvincible(0);
            }
        }
    }
    //check if the game is over
    public void checkGameOver(ArrayList<GameObject> gameObjects, ArrayList<Power> Powers, Player player, EnemyBoss enemyBoss, ArrayList<FlyingPlatform> flyingPlatforms){
        //if health is less or equal to 0, game over and set the player to dead
        if (getPLAYER_HEALTH().getHealth() <= 0){
            for (GameObject gameObject : gameObjects){
                gameObject.setPlayerDead();
            }
            for (Power power : Powers){
                power.setPlayerDead();
            }
            player.setDead();

            enemyBoss.setPlayerDead();
            if (player.getY() > WINDOW_HEIGHT){
                setGameOver(true);
            }
            for (FlyingPlatform flyingPlatform : flyingPlatforms){
                flyingPlatform.setPlayerDead();
            }

        }
        if ((enemyBoss != null) && (getENEMY_BOSS_HEALTH().getHealth() <= 0)){
            enemyBoss.setDead();
        }
    }

    public boolean isInvinciblePowerActive() {
        return isInvinciblePowerActive;
    }

    public void setInvinciblePowerActive(boolean invinciblePowerActive) {
        isInvinciblePowerActive = invinciblePowerActive;
    }

    public boolean isDoubleScorePowerActive() {
        return isDoubleScorePowerActive;
    }

    public void setDoubleScorePowerActive(boolean doubleScorePowerActive) {
        isDoubleScorePowerActive = doubleScorePowerActive;
    }

    public int getActiveFramesInvincible() {
        return activeFramesInvincible;
    }

    public void setActiveFramesInvincible(int activeFramesInvincible) {
        this.activeFramesInvincible = activeFramesInvincible;
    }

    public int getActiveFramesDoubleScore() {
        return activeFramesDoubleScore;
    }

    public void setActiveFramesDoubleScore(int activeFramesDoubleScore) {
        this.activeFramesDoubleScore = activeFramesDoubleScore;
    }

    public boolean isFallingFromPlatform() {
        return isFallingFromPlatform;
    }

    public void setFallingFromPlatform(boolean fallingFromPlatform) {
        isFallingFromPlatform = fallingFromPlatform;
    }

    public boolean isWonGame() {
        return WonGame;
    }

    public void setWonGame(boolean wonGame) {
        WonGame = wonGame;
    }

    public boolean isGiveInstruction() {
        return GiveInstruction;
    }

    public void setGiveInstruction(boolean giveInstruction) {
        GiveInstruction = giveInstruction;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public PlayerHealth getPLAYER_HEALTH() {
        return PLAYER_HEALTH;
    }

    public Score getSCORE() {
        return SCORE;
    }

    public enemyBossHealth getENEMY_BOSS_HEALTH() {
        return ENEMY_BOSS_HEALTH;
    }
}
