import bagel.*;
import java.util.Properties;
import bagel.Image;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 *
 * Please enter your name below
 * @xulin2
 */
public class ShadowMario extends AbstractGame {
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final ArrayList <GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private final ArrayList<Power> Powers = new ArrayList<>();
    private final ArrayList<Fireball> fireballsPlayer = new ArrayList<>();
    private final ArrayList<Fireball> fireballsEnemy = new ArrayList<>();
    private final Image BACKGROUND_IMAGE;
    private static Player player;
    private EnemyBoss enemyBoss;
    private final Win WIN = new Win();
    private final Title TITLE = new Title();
    private final GameOver GAME_OVER = new GameOver();
    private final StartInstruction START_INSTRUCTION = new StartInstruction();
    private String fileName;
    private final Level level;
    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        level = new Level();

        // you can initialise other values from the property files here
        // I prefer initialise them in the main function and it works fine
    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        //initialising the game
        ShadowMario game = new ShadowMario(game_props, message_props);

        game.run();
    }
    public void LoadLevel(String fileName){
        //create a 2D array 'data' to store and initialise the positions for all game objects
        int dataLength = IOUtils.countLines(fileName);
        String[][] data = IOUtils.readCsv(fileName);
        for (int i = 0; i < dataLength; i++){
            String entityType = data[i][0];
            double x = Double.parseDouble(data[i][1]);
            double y = Double.parseDouble(data[i][2]);
            switch (entityType) {
                case "ENEMY_BOSS":
                    enemyBoss = new EnemyBoss(x, y);
                    break;
                case "INVINCIBLE_POWER":
                    Powers.add(new InvinciblePower(x, y));
                    break;
                case "FLYING_PLATFORM":
                    flyingPlatforms.add(new FlyingPlatform(x, y));
                    break;
                case "DOUBLE_SCORE":
                    Powers.add(new DoubleScorePower(x, y));
                    break;
                case "PLATFORM":
                    gameObjects.add(new Platform(x, y));
                    break;
                case "PLAYER":
                    player = new Player(x, y);
                    break;
                case "COIN":
                    gameObjects.add(new Coin(x, y));
                    break;
                case "ENEMY":
                    gameObjects.add(new Enemy(x, y));
                    break;
                case "END_FLAG":
                    gameObjects.add(new EndFlag(x, y));
                    break;
            }
        }
    }

    public void resetGame(){
        gameObjects.clear();
        fireballsPlayer.clear();
        fireballsEnemy.clear();
        level.getPLAYER_HEALTH().resetHealth();
        level.getSCORE().resetScore();
        if (enemyBoss != null){
            enemyBoss.resetObject();
        }
        level.getENEMY_BOSS_HEALTH().resetHealth();
        player = null;
        enemyBoss = null;
        level.resetVariables();
    }

    public static boolean isCollideWithPlayer(double x, double y, double radius){
        return Math.sqrt(Math.pow(player.getX() - x, 2) +
                Math.pow(player.getY() - y, 2)) <= player.getRADIUS() + radius;
    }
    public boolean isCollideWithBoss(double x, double y, double radius){
        return Math.sqrt(Math.pow(enemyBoss.getX() - x, 2) +
                Math.pow(enemyBoss.getY() - y, 2)) <= enemyBoss.getRADIUS() + radius;
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        //while the game is not finished
        if (!level.isWonGame() && !level.isGameOver()){
            // close window
            if (input.wasPressed(Keys.ESCAPE)){
                Window.close();
            }
            //before starting the game, show the title and instruction
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            if (level.isGiveInstruction()){
                START_INSTRUCTION.update();
                TITLE.update();
                if (input.wasPressed(Keys.NUM_1)){
                    fileName = GAME_PROPS.getProperty("level1File");
                    LoadLevel(fileName);
                    level.setGiveInstruction(false);
                }
                else if (input.wasPressed(Keys.NUM_2)){
                    fileName = GAME_PROPS.getProperty("level2File");
                    LoadLevel(fileName);
                    level.setGiveInstruction(false);
                }
                else if (input.wasPressed(Keys.NUM_3)){
                    fileName = GAME_PROPS.getProperty("level3File");
                    LoadLevel(fileName);
                    level.setGiveInstruction(false);
                }
            }
            else{
                //start the game
                for (GameObject gameObject: gameObjects){
                    gameObject.update(input, level);
                }
                for (Power power: Powers){
                    power.update(input, level);
                }
                player.update(input, level);
                if (enemyBoss!=null){
                    enemyBoss.update(input, level);
                }
                level.getSCORE().update();
                level.getPLAYER_HEALTH().update();
                level.checkPowersActive();
                if (fileName.equals(GAME_PROPS.getProperty("level3File"))){
                    level.getENEMY_BOSS_HEALTH().update();
                }

                //show flying platforms and make player stand on flying platforms
                for (FlyingPlatform flyingPlatform: flyingPlatforms){
                    flyingPlatform.update(input, level);
                    //check if the player lands on flying platforms
                    if ((Math.abs(player.getX() - flyingPlatform.x) < flyingPlatform.getHalfLength()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) <= flyingPlatform.getHalfHeight()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) >= flyingPlatform.getHalfHeight() - 1)
                            && !level.isFallingFromPlatform()) {
                        //place the player on the platform
                        if (!player.isOnFlyingPlatform()){
                            player.setVerticalSpeed(0);
                            player.setOnFlyingPlatform(true);
                            player.setJumping(false);
                            flyingPlatform.setPlayerOn(true);
                            level.setFallingFromPlatform(false);
                        }
                    }
                    //when the player falls from the flying platforms
                    else if ((Math.abs(player.getX() - flyingPlatform.x) > flyingPlatform.getHalfLength()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) <= flyingPlatform.getHalfHeight()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) >= flyingPlatform.getHalfHeight() - 1) &&
                        flyingPlatform.isPlayerOn()){
                        player.setOnFlyingPlatform(false);
                        flyingPlatform.setPlayerOn(false);
                        player.setJumping(true);
                        level.setFallingFromPlatform(true);
                    }
                }
                if (player.isOnGround()){
                    level.setFallingFromPlatform(false);
                }

                //shooting fireBall
                if ((enemyBoss != null) && player.isShootFireBall() && enemyBoss.isInActivationRadius(player)){
                    player.setShootFireBall(false);
                    if (player.isTurnLeft()){
                        fireballsPlayer.add(new Fireball(player.x, player.y, -1));
                    }
                    else {
                        fireballsPlayer.add(new Fireball(player.x, player.y, 1));
                    }
                }
                for (Fireball fireball : fireballsPlayer){
                    fireball.update();
                    if (isCollideWithBoss(fireball.getX(), fireball.getY(), fireball.getRADIUS())) {
                        fireball.setActive(false);
                        level.getENEMY_BOSS_HEALTH().updateHealth(fireball.getDamageSize());
                    }
                }
                if (enemyBoss != null){
                    enemyBoss.isInActivationRadius(player);
                }
                if (enemyBoss != null && enemyBoss.isShootFireBall()){
                    fireballsEnemy.add(new Fireball(enemyBoss.x, enemyBoss.y, -1));
                    enemyBoss.setShootFireBall(false);
                }
                for (Fireball fireball : fireballsEnemy){
                    fireball.update();
                    if (isCollideWithPlayer(fireball.getX(), fireball.getY(), fireball.getRADIUS())) {
                        fireball.setActive(false);
                        level.getPLAYER_HEALTH().updateHealth(fireball.getDamageSize());
                    }
                }

                //check if the game is over or not
                level.checkGameOver(gameObjects, Powers, player, enemyBoss, flyingPlatforms);

            }
        }
        else {
            if (input.wasPressed(Keys.ESCAPE)) {
                Window.close();
            }
            if (!level.isGiveInstruction()){
                //when game over
                if (level.isGameOver()){
                    if (player.getY() >= level.WINDOW_HEIGHT) {
                        GAME_OVER.update();
                        if (input.wasPressed(Keys.SPACE)) {
                            level.setGiveInstruction(true);
                        }
                    }
                }
                //when winning the game
                else {
                    WIN.update();
                    if (input.wasPressed(Keys.SPACE)){
                        level.setGiveInstruction(true);
                    }
                }
            }
            //reset the game entities and restart the game
            else {
                START_INSTRUCTION.update();
                TITLE.update();
                if (input.wasPressed(Keys.NUM_1)){
                    resetGame();
                    fileName = GAME_PROPS.getProperty("level1File");
                    LoadLevel(fileName);
                }
                else if (input.wasPressed(Keys.NUM_2)){
                    resetGame();
                    fileName = GAME_PROPS.getProperty("level2File");
                    LoadLevel(fileName);
                }
                else if (input.wasPressed(Keys.NUM_3)){
                    resetGame();
                    fileName = GAME_PROPS.getProperty("level3File");
                    LoadLevel(fileName);
                }
            }
        }
    }
}
