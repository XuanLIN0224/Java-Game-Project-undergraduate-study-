import bagel.*;
import java.util.Properties;
import bagel.Image;
import java.util.ArrayList;

/**
 * Implemented Code for SWEN20003 Project 1, Semester 1, 2024
 * Please enter your name below
 * @xulin2
 * Not completely written by Xuan LIN, skeleton code provided by the University of Melbourne.
 */
public class ShadowMario extends AbstractGame {
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final ArrayList <GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    // creating the Powers arraylist allows me to handle the powers separately from other GameObjects
    private final ArrayList<Power> Powers = new ArrayList<>();
    // creating the fireballsPlayer and fireballsEnemy allows me to
    // handle the fireballs shot from the player and the fireballs shot from the enemyBoss separately
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
    /**
     * Method to load levels
     * I prefer to use a public void method inside ShadowMario instead of in the Level class
     * because I want the purpose of the level class to only be customising specific level behaviours.
     * I think putting the loading level feature in the level class will make the level class too complicated,
     * so I decided to implement the LoadLevel in the ShadowMario class
     */
    public void LoadLevel(String fileName){
        //create a 2D array 'data' to store and initialise the positions for all game objects
        int dataLength = IOUtils.countLines(fileName);
        String[][] data = IOUtils.readCsv(fileName);
        for (int i = 0; i < dataLength; i++){
            String entityType = data[i][0];
            double x = Double.parseDouble(data[i][1]);
            double y = Double.parseDouble(data[i][2]);
            switch (entityType) {
                //the enemyBoss only exists in Level 3, so even though it is a subclass of GameObject,
                //I still decided to make an instance of it to check if it is null or not
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
                //As I need to check if the player shoots the fireball or not and
                //if the player is on the flying platforms or not,
                //I need to make the player not be in the gameObjects arraylist
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
    /**
     * reset the game by clearing the arrayLists,
     * setting enemyBoss, player to null and
     * reset the variables
     */
    public void resetGame(){
        gameObjects.clear();
        fireballsPlayer.clear();
        fireballsEnemy.clear();
        level.getPLAYER_HEALTH().resetHealth();
        level.getSCORE().resetScore();
        level.getENEMY_BOSS_HEALTH().resetHealth();
        player = null;
        enemyBoss = null;
        level.resetVariables();
        flyingPlatforms.clear();
        Powers.clear();
    }
    /**
     * check if an object collides with the player
     */
    public static boolean isCollideWithPlayer(double x, double y, double radius){
        return Math.sqrt(Math.pow(player.getX() - x, 2) +
                Math.pow(player.getY() - y, 2)) <= player.getRADIUS() + radius;
    }
    /**
     * check if an object collides with the player
     */
    public boolean isCollideWithBoss(double x, double y, double radius){
        return Math.sqrt(Math.pow(enemyBoss.getX() - x, 2) +
                Math.pow(enemyBoss.getY() - y, 2)) <= enemyBoss.getRADIUS() + radius;
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Allows loading different levels by pressing different keys.
     * Allows reloading levels
     * Allows checking if the player and the enemyBoss can shoot fireballs or not
     * Allows checking if the player is on flying platforms
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

                //shooting fireBalls
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
                //As in this project, level selecting is done by pressing different keys on the keyboard,
                //I need to handle each Key pressing condition separately
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
