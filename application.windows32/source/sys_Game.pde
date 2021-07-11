/*
=====================================================================
 Name: sys_Game
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 27/11/2020
 Description: A class to controll and use the whole game - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Removing all unused code
=====================================================================
 */

abstract class Game {
  String gameName;

  protected PImage background;  //Background image to be rendered behind everything
  public final SceneManager sceneManager = new SceneManager();  //The sceneManager decides which scene is shown at all time
  public Inventory inventory    = new Inventory( "inventory", 40, 40, 70, 70 );  //Create the inventory in the top left
  public Cursor clicker;
  public Char mainCharacter;  //Main character, to be shown in every non-cinamatic scene
  public TextDisplay textDisplay = new TextDisplay();  //Text display, this will recieve text via the TextDisplay class in the Tools file
  public Minim minim;  //Minim for the minim library for sound
  public Animation rain_hallway;  //Rain animation
  public Lightning lightning;  //Lightning which strikes randomly
  public int solvedRitual;  //Interger to keep track on the amount of ritual's succesfully executed
  final RuleBook ruleBook = new RuleBook();
  AudioPlayer music;  //Audioplayer specific to this scene, to be played on a loop when this scene is active

  abstract void create();

  void reset() {
    create();
  }

  void update() {  //Every frame
    if ( sceneManager.isEmpty() ) { //Activated once when the game starts
      create();  //Create the game
      startGlobalMusic();  //Start global music
    }
    ruleBook.checkAndExecuteRules();  //First checks all rules
    image( background, 0, 0, 1920, 1080 );  //Render backgound image
    lightning.display();  //Render lightning 
    sceneManager.updateScene();  //Update current scene (and all children)
    inventory.purgeChildren();  //Remove all items form inventory which should be removed
    inventory.display();  //Render inventory (and contents)
    clicker.display();  //Display custom mouse
    textDisplay.checkFadeState();  //Display the text
  }

  // ============ EVENT HANDLERS ============
  // When the mouse is clicked or pressed, handle all active objects appropriatly
  void handleMousePressed() {
    inventory.checkMousePressed();
    sceneManager.getCurrentScene().checkMousePressed();
  }

  void handleMouseClicked() {
    if (!clicker.dragging) {
      inventory.checkMouseClicked();
      mainCharacter.checkMouseClicked();
      sceneManager.getCurrentScene().checkMouseClicked();
    }
  }
}
