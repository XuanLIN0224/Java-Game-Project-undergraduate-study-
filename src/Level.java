import java.util.ArrayList;
import java.util.Properties;

/**
 * Code for customising specific level behaviours for each level
 * written by
 * @xulin2
 */
public class Level {
    private final Properties GAME_PROPS;
    /**
     * WINDOW_HEIGHT can also be used in ShadowMario therefore it is public
     */
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
    /**
     * The constructor
     */
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

    /**
     * set all the boolean variables to false and int variables to 0
     */
    public void resetVariables(){
        setGiveInstruction(false);
        setGameOver(false);
        setWonGame(false);
        setInvinciblePowerActive(false);
        setActiveFramesInvincible(0);
        setDoubleScorePowerActive(false);
        setActiveFramesDoubleScore(0);
    }

    /**
     * check if the Powers are active or not
     */
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

    /**
     * check if the game is over
     */
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

    /**
     * get the isInvinciblePowerActive variable
     */
    public boolean isInvinciblePowerActive() {
        return isInvinciblePowerActive;
    }

    /**
     * set the isInvinciblePowerActive variable
     */
    public void setInvinciblePowerActive(boolean invinciblePowerActive) {
        isInvinciblePowerActive = invinciblePowerActive;
    }

    /**
     * get the isDoubleScorePowerActive variable
     */
    public boolean isDoubleScorePowerActive() {
        return isDoubleScorePowerActive;
    }

    /**
     * set the isDoubleScorePowerActive variable
     */
    public void setDoubleScorePowerActive(boolean doubleScorePowerActive) {
        isDoubleScorePowerActive = doubleScorePowerActive;
    }

    /**
     * get the activeFramesInvincible variable
     */
    public int getActiveFramesInvincible() {
        return activeFramesInvincible;
    }

    /**
     * set the activeFramesInvincible variable
     */
    public void setActiveFramesInvincible(int activeFramesInvincible) {
        this.activeFramesInvincible = activeFramesInvincible;
    }

    /**
     * get the activeFramesDoubleScore variable
     */
    public int getActiveFramesDoubleScore() {
        return activeFramesDoubleScore;
    }

    /**
     * set the activeFramesDoubleScore variable
     */
    public void setActiveFramesDoubleScore(int activeFramesDoubleScore) {
        this.activeFramesDoubleScore = activeFramesDoubleScore;
    }

    /**
     * get the isFallingFromPlatform variable
     */
    public boolean isFallingFromPlatform() {
        return isFallingFromPlatform;
    }

    /**
     * set the isFallingFromPlatform variable
     */
    public void setFallingFromPlatform(boolean fallingFromPlatform) {
        isFallingFromPlatform = fallingFromPlatform;
    }

    /**
     * get the WonGame variable
     */
    public boolean isWonGame() {
        return WonGame;
    }

    /**
     * set the WonGame variable
     */
    public void setWonGame(boolean wonGame) {
        WonGame = wonGame;
    }

    /**
     * get the GiveInstruction variable
     */
    public boolean isGiveInstruction() {
        return GiveInstruction;
    }

    /**
     * set the GiveInstruction variable
     */
    public void setGiveInstruction(boolean giveInstruction) {
        GiveInstruction = giveInstruction;
    }

    /**
     * get the isGameOver variable
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * set the isGameOver variable
     */
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     * get the PLAYER_HEALTH;
     */
    public PlayerHealth getPLAYER_HEALTH() {
        return PLAYER_HEALTH;
    }

    /**
     * get the SCORE
     */
    public Score getSCORE() {
        return SCORE;
    }

    /**
     * get the ENEMY_BOSS_HEALTH
     */
    public enemyBossHealth getENEMY_BOSS_HEALTH() {
        return ENEMY_BOSS_HEALTH;
    }
}
