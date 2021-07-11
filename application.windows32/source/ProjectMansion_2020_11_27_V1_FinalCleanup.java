import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Stack; 
import java.util.HashMap; 
import ddf.minim.*; 
import java.util.Map; 
import java.util.HashMap; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ProjectMansion_2020_11_27_V1_FinalCleanup extends PApplet {

/*
=====================================================================
 Name: ProjectMansion
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 27/11/2020
 Description: A point and click game for the CMGT second project "First Contact"
 Last update: Removing all unused code
 Date last test: 26/11/2020 - Letting other classmates test it
 =====================================================================
 */

Game game;

public void settings() {
  fullScreen();
  //size( 1920, 1080, FX2D  );
}

public void setup() {
  AudioSetup();
  game = new TheResurrection();
}

public void draw() {
  frameRate(60);
  background(12, 20, 69);
  game.update();
}


// ============ EVENT HANDLERS ============

public void mouseClicked() { 
  game.handleMouseClicked();
}

public void mousePressed() { 
  game.handleMousePressed();
}
/*
=====================================================================
 Name: GameDefinition
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 20/11/2020
 Description: A class to setup all other items/scenes in this game - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Removing all unused code
 =====================================================================
 */

final public int audioLevel = -10; //global interger for sound

// Designers may touch this file
class TheResurrection extends Game {
  public void create() {
    ruleBook.reset();
    inventory.reset();
    sceneManager.reset();

    ImageGameObject mainCharacterImg = new ImageGameObject( "mainy", 791, 141, "mainCharacter.png" ); //The main character as imageObject for the cinamatics
    mainCharacterImg.w /= 2;
    mainCharacterImg.h /= 2;

    Scene  mainMenu = new Scene(  "MainMenu", "foreground.png", "rain outside.wav", audioLevel + 5, true);
    Scene  openingScene = new CinamaticScene(  "openingScene", "characterCinamatic.png", "songloop.wav", audioLevel);
    Scene  entrHallScene = new Scene(  "entrHallScene", "entranceHall.png", "rain inside.wav", audioLevel + 10, false, width/2 );
    Scene  gardenScene = new Scene(  "gardenScene", "gardenScene.png", "rain outside.wav", audioLevel + 5, true, 1300 );
    Scene  kitchenScene = new Scene(  "kitchenScene", "kitchenScene.png", "Cauldron sound.wav", audioLevel + 5, false, 1458  );
    Scene  closingScene = new CinamaticScene( "closingScene", "characterCinamatic.png", "rain inside.wav", audioLevel + 10);
    Scene  credits = new CinamaticScene("credits", "credits.png", "", audioLevel);

    //========= mainMenu ==========
    //Start up lobby
    mainMenu.addChild( new SceneChangeButton( "FromMainToEntrHallScene", 929, 854, "mainDoorClosed.png", "mainDoorOpen.png", openingScene ) );
    mainMenu.addChild( new FadinImage( new ImageGameObject( "logo", 0, 0, "logo.png" )));
    sceneManager.addScene(  mainMenu );

    //======= openingScene ========
    //Introduction to the story
    CyclingTextButton openCinematic = new CyclingTextButton("openingSceneButton", 1780, height-210, "Next", 50, entrHallScene); 
    openCinematic.addChild(new TextObject("opnText1", "This is such a fateful day... I can smell the delicate scent of her gardenian perfume... Tonight I will hold her in my arms again, evermore... My lovely Lenore..."));
    openCinematic.addChild(new TextObject("opnText2", "I am certain of my wish...I will regain what I have once lost... and unshackle the divine creature... In return ill be with her evermore."));
    openCinematic.addChild(new TextObject("opnText3", "Never shall I forget the sacred words... nor shall I forget recipe for the ancient brew... Its figure shall be whole again... just as she will be.."));
    openCinematic.addChild(new TextObject("opnText4", "I will reap our reward... the Paladins shall pay... for murdering Lenore... For capturing the Spirit... With this spell... I will set us all free..."));
    openCinematic.addChild(new TextObject("opnText5", "I am thankful for this sleeping Vision... I will perform this ritual... That is my final decision..."));
    openingScene.addChild(mainCharacterImg);
    openingScene.addChild(openCinematic);
    sceneManager.addScene(  openingScene );

    //======= entrHallScene =======
    //Main hall, with most important puzzles and the FinalButton to go to the closing cinamatic
    entrHallScene.addChild( new SceneChangeButton( "FromEntrHallToKitchenScene", 0, height-520, "doorSmolL.png", "doorBigL.png", kitchenScene ) );
    entrHallScene.addChild( new SceneChangeButton( "FromEntrHallToGardenScene", width-212, height-520, "doorSmolR.png", "doorBigR.png", gardenScene ) );

    InventoryItem head1 = new InventoryItem( "head1", 278, 532, "head1.png", "I can feel her call..." );
    InventoryItem head2 = new InventoryItem( "head3", 557, 941, "head3.png", "“Its eyes are following me..." );
    entrHallScene.addChild(new ImageButton("window", 1498, 100, "window.png", "the rain sounds soothing", "The wind is Howling", "She loved weather like this... a sign?"));

    entrHallScene.addChild(new ImageButton("goldenPlaque1", 414, 937, "goldenPlaque.png", "The elixir of resurrection will be offered here..."));
    entrHallScene.addChild(new ImageButton("goldenPlaque2", 1401, 937, "goldenPlaque.png", "Body of the beast"));

    ImageButton pentagram = new FinalButton("pentagram", 755, 260, "pentagram.png", "The Ritual is the only way...", "The ritual... IT IS READY", closingScene);
    entrHallScene.addChild(pentagram);
    entrHallScene.addChild(new ImageGameObject("candleG1", 719, 275, "candleLitGreen.png"));
    entrHallScene.addChild(new ImageGameObject("candleG2", 1185, 275, "candleLitGreen.png"));
    entrHallScene.addChild(new ImageGameObject("candleY1", 252, 700, "candleLitYellow.png"));
    entrHallScene.addChild(new ImageGameObject("candleY2", 1652, 700, "candleLitYellow.png"));
    InventoryItem plasterCandle = new InventoryItem("plasterCandle", 1714, 817, "candleWithPlaster.png", "Should not have broken you..." );
    entrHallScene.addChild(new CandlePuzzle(entrHallScene, plasterCandle));

    InventoryItem essenceOfTheBeast = new InventoryItem("finishedBrew", 854, 721, "finishedBrew.png", "No sacrifice is too great for you...");
    entrHallScene.addChild( new BrewPlacementPuzzle(entrHallScene, essenceOfTheBeast));
    entrHallScene.addChild(new StatuePuzzle(entrHallScene, head1, head2));
    sceneManager.addScene( entrHallScene );

    //======= gardenScene =======
    //Contains many key items
    gardenScene.addChild( new SceneChangeButton( "FromGardenToEntrHallScene", 66, 551, "gardenDoorClosed.png", "gardenDoorOpen.png", entrHallScene ) );

    InventoryItem apple = new InventoryItem( "apple", 1227, 382, "apple.png", "The cursed apple... It will fit right in...");
    InventoryItem evilFlower = new InventoryItem( "evilFlower", 1558, 808, "flower.png", "The Flower of the dead, just what I needed...");
    InventoryItem blood = new InventoryItem( "blood", 1244, 511, "blood.png", "It won't stop flowing...");
    gardenScene.addChild( new ImageButton( "tombStone", 796, 767, "tombstone.png", "Blackboard, we are lucky this horrible beast has been slain...") );
    gardenScene.addChild( new ImageButton( "tombStoneLenor", 1589, 664, "tombstoneLenor.png", "No more Grief... we will be reunited soon..." ));
    gardenScene.addChild( plasterCandle );
    gardenScene.addChild( head2 );
    gardenScene.addChild( new ImageGameObject("fence", 457, 702, "fence.png") );
    gardenScene.addChild( apple );
    gardenScene.addChild( evilFlower );
    gardenScene.addChild( new ImageGameObject("overlay", 0, 0, "gardenSceneOverlay.png"));
    sceneManager.addScene(  gardenScene );

    //======= kitchenScene =======
    //Contains the brewing puzzle and the hidden head
    kitchenScene.addChild( new SceneChangeButton( "FromEntrHallToGardenScene", width-212+60, height-520, "doorSmolSlimR.png", "doorBigSlimR.png", entrHallScene  ) );
    kitchenScene.addChild( new ImageButton( "fire", 876, 860, "campFire.png", "Hope it lasts long enough..." ));
    kitchenScene.addChild( new ImageGameObject( "kitchenMainThingy", 0, 0, "kitchenMainThingy.png"));

    kitchenScene.addChild( new ImageButton("window1", 512, 385, "longWindow.png", "the rain sounds soothing", "The wind is Howling", "She loved weather like this... a sign?"));
    kitchenScene.addChild( new ImageButton("window2", 1272, 386, "longWindow.png", "the rain sounds soothing", "The wind is Howling", "She loved weather like this... a sign?"));
    kitchenScene.addChild( new ImageButton("window3", 591, 49, "wideWindow.png", "the rain sounds soothing", "The wind is Howling", "She loved weather like this... a sign?"));
    InventoryItem knife = new InventoryItem( "knife", 1623, 783, "bigKnife.png", "The Ritual Dagger.." );
    //kitchenScene.addChild( head1 );
    kitchenScene.addChild( new CabinetPuzzle(kitchenScene, head1) );
    kitchenScene.addChild( knife );
    kitchenScene.addChild(new BrewingPuzzle(kitchenScene, apple, evilFlower, blood, essenceOfTheBeast));
    sceneManager.addScene(  kitchenScene );

    //======= closingScene =======
    //The final scene as the conclusion of the game
    ImageGameObject mainCharacterSmall = new ImageGameObject( "mainy", 250, 545, "mainCharacter.png" );
    mainCharacterSmall.w /= 4;
    mainCharacterSmall.h /= 4;
    mainCharacterSmall.x += 200;

    CyclingTextButton closeCinematic = new CyclingTextButton("closingSceneButton", 1780, 870, "Next", 50, credits); 
    TextObject clsText1 = new TextObject("clsText1", "RITUS VIVESCERETE BESTIA... APERITE ABSTRUSA PORTA... ANIMA CALCATE MUNDI...");
    clsText1.addChild( new FadinImage( pentagram ) );
    closeCinematic.addChild(clsText1);

    TextObject clsText2 = new TextObject("clsText2", "Did it work? It is different from the Dream... Uncanny does it seem, that nothing happens at all...");  
    clsText2.addChild( pentagram );
    clsText2.addChild(new FadinImage( mainCharacterSmall ));
    closeCinematic.addChild(clsText2);

    TextObject clsText3 = new TextObject("clsText3", "What is this I hear? I read none of this in the ritual scroll... Bells ringing, loud and clear! for whom do they toll?.");
    clsText3.addChild( pentagram );
    clsText3.addChild(mainCharacterSmall);
    clsText3.addChild(new SoundObject("scream", "bells!.wav", 0 + audioLevel));
    closeCinematic.addChild(clsText3);

    TextObject clsText4 = new TextObject("clsText4", "Now it is coming... Oh gods, how big can this demon be? Calling it was heresy... STAY AWAY! JUST GRANT MY WISH...");
    clsText4.addChild( pentagram );
    clsText4.addChild(mainCharacterSmall);
    closeCinematic.addChild(clsText4);

    TextObject clsText5 = new TextObject("clsText5", "I see it now, oh so clear... I just wanted to be with my Lenore... but with this demon only the end of the world is near...");
    clsText5.addChild(mainCharacterSmall);
    closeCinematic.addChild(clsText5);

    TextObject clsText6 = new TextObject("clsText6", "...");
    clsText6.addChild(new SoundObject("screm", "screm.wav", 5 + audioLevel));
    closeCinematic.addChild(clsText6);

    TextObject clsText7 = new TextObject("clsText7", "...");
    clsText7.addChild(new FadinImage( new ImageGameObject("sixEyes", 0, 0, "outro_art.png") ));
    clsText7.addChild(new SoundObject("icky ending", "icky ending.wav", 0 + audioLevel));
    closeCinematic.addChild(clsText7);

    TextObject clsText8 = new TextObject("clsText8", "Thanks for playing...");
    clsText8.addChild(new FadinImage( new ImageGameObject("the End", 0, 0, "the End.png") ));
    clsText8.addChild(new SoundObject("screm", "screm.wav", 5 + audioLevel));
    closeCinematic.addChild(clsText8);

    closingScene.addChild(closeCinematic);
    sceneManager.addScene(  closingScene );

    //=======Credits=======
    //Showing all the beutiful peaple who worked on this game
    sceneManager.addScene( credits );

    //======== Gerneral load stuff ========
    noCursor();
    clicker = new Cursor( "Cursor", PApplet.parseInt(mouseX), PApplet.parseInt(mouseY), "cursorsOpenHand.png", "cursorsWithFinger.png", "cursorsWithoutFinger.png");
    mainCharacter = new Char("MainCharacter", 960, 685, "mainCharacter", blood, knife);

    background = loadImage( "background.png" );
    rain_hallway = new Animation();
    rain_hallway.animSetup("rain_", 4);
    lightning = new Lightning("Lighting", "Lighting.png");

    // ======== Game rules ========
    //Rules are executed every frame
    ruleBook.addRule(new RitualsDone());
  }
}
/*
=====================================================================
 Name: GameDefinition
 Version: 1.0
 Author(s): Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 19/11/2020
 Description: A class to display animation and blinking functionality - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Initial push
=====================================================================
 */

class Animation
{
  //----ANIMATION--------19-11-2020---------------------------
  PImage currentImage = null;              //current frame of the animation which gets displayed  
  int currentAnimIndex = 0;                //current animation index, used to determine which animation frame should play currently.
  PImage[] animImages = new PImage[3];     //all frames of animation  
  int animMillis;                          //Saves the amount of milliseconds passed since start.  

  float tintAlpha = 255;  

  //Setup animation, using keywords.
  //DO NOT FORGET UNDERSCORES OR THE-LIKE.  //prefix = name of file without extension. doBlink decides if blink is done. This affects fSpeed and bHold.
  public void animSetup(String prefix, int animSize)
  {         
    animImages = new PImage[animSize];

    //Add all animations to the new array.
    if (animImages.length > 1)
      for (int i = 0; i < animImages.length; i++)    
        animImages[i] = loadImage(prefix + i + ".png");
    else
    {
      //Skip animation setup process if only 1 animation is given.
      animImages[0] = loadImage(prefix + ".png");
      currentImage = animImages[0];
    }

    //Save the current MilliSeconds since startup to use as a timer.
    animMillis = millis();
  }

  //Animate using the proper position, scale and animation-delay.
  public void doAnim(PVector pos, PVector scale, float animDelay)
  {  
    //Select the current image
    currentImage = animImages[currentAnimIndex];

    //Display image    
    if (currentImage != null)
    {
      tint(255, 255, 255, tintAlpha);
      image(currentImage, pos.x, pos.y, scale.x, scale.y);
    }
    tint(255);

    //Once enough time passed, switch to the next frame.
    if (millis() - animMillis >= animDelay)
    {    
      currentAnimIndex++;
      //reset timer
      animMillis = millis();

      //Loop back to start once animation finishes playing.
      if (currentAnimIndex >= animImages.length)         
        currentAnimIndex = 0;
    }
  }
}
/*
=====================================================================
 Name: sys_Buttons
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for clickable objects - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding final button to get to the end of the game
=====================================================================
 */

// ============ SceneChangeButtonButton ============
class SceneChangeButton extends ImageGameObject {
  private Scene scene;

  SceneChangeButton( String newIdentifier, int newX, int newY, String imageFilename, Scene toScene ) {
    super( newIdentifier, newX, newY, imageFilename );
    scene = toScene;
  }

  public void display() {
    if (isPointInRectangle( mouseX, mouseY, x, y, w, h )) {
      game.clicker.hovering = true;
    }
    super.display();
  }

  SceneChangeButton( String newIdentifier, int newX, int newY, String imageFilename, String hoverImageFilename, Scene toScene ) {
    super(newIdentifier, newX, newY, imageFilename, hoverImageFilename);
    scene = toScene;
  }

  public void mouseClickedAction() {
    println( getIdentifier() + ".handleMouseClicked" );
    game.sceneManager.gotoScene( scene );
  }
}

// =========== ImageButton ============
class ImageButton extends ImageGameObject {
  String textOut1;
  String textOut2;
  String textOut3;
  boolean multiple;

  ImageButton( String newIdentifier, int newX, int newY, String imageFilename, String text) {
    super( newIdentifier, newX, newY, imageFilename );
    displayText = text;
    multiple = false;
  }

  ImageButton( String newIdentifier, int newX, int newY, String imageFilename, String text1, String text2, String text3) {
    super( newIdentifier, newX, newY, imageFilename );
    textOut1 = text1;
    textOut2 = text2;
    textOut3 = text3;
    multiple = true;
  }

  public void display() {
    if (isVisible) {
      if (isPointInRectangle( mouseX, mouseY, x, y, w, h )) {
        game.clicker.hovering = true;
      }
      super.display();
    }
  }

  public void mouseClickedAction() {
    if (multiple) {
      int i =(int)random(0, 3);
      println(i);
      if (i == 0) 
        displayText = textOut1;
      else if (i == 1)
        displayText = textOut2;
      else
        displayText = textOut3;
    }
    super.displayText();
  }
}

// =========== InvButton ============
class InvButton extends RectangularGameObject {

  InvButton( String newIdentifier, int newX, int newY, int newW, int newH, String text) {
    super( newIdentifier, newX, newY );
    displayText = text;
    w = newW;
    h = newH;
  }

  public void display() {
    if (isVisible) {
      if (isPointInRectangle( mouseX, mouseY, x, y, w, h )) {
        game.clicker.hovering = true;
      }
      super.display();
    }
  }

  public void mouseClickedAction() {
    super.displayText();
  }
}

// ============ TextButton ============
class TextButton extends RectangularGameObject {
  private String buttonText;
  private int buttonTextSize;
  private int offset;

  TextButton( String newIdentifier, int newX, int newY, String newButtonText, int newButtonTextSize ) {
    super( newIdentifier, newX, newY );
    x = newX;
    y = newY;
    buttonText = newButtonText;
    buttonTextSize = newButtonTextSize;
    offset = buttonTextSize / 5;
    textSize( buttonTextSize );
    w = (int) textWidth( buttonText ) + 2 * offset;
    h = buttonTextSize;
  }

  public void display() {
    stroke( 0 );
    strokeWeight( 3 );
    fill( 255, 128 );
    rect(  x, y, w, h );
    fill( 0 );
    textSize( buttonTextSize );
    textAlign(LEFT, BOTTOM);
    text( buttonText, x + offset, y + offset + buttonTextSize * 0.9f);

    if (isPointInRectangle( mouseX, mouseY, x, y, w, h )) {
      game.clicker.hovering = true;
    }
  }

  public void mouseClickedAction() {
    super.displayText();
  }
}

// =============== FinalButton ===============
class FinalButton extends ImageButton {
  Scene next;
  String finalText;

  FinalButton( String newIdentifier, int newX, int newY, String imageFilename, String text, String textFinal, Scene Goto ) {
    super(  newIdentifier, newX, newY, imageFilename, text );
    finalText = textFinal;
    next = Goto;
  }

  public void display() {
    if (game.solvedRitual >= 3) {
      displayText = finalText;
    } else if (game.solvedRitual >= 1) {
      displayText = "It will soon be done, my love...";
    }
    super.display();
  }

  public void mouseClickedAction() {
    if (game.solvedRitual >= 3) {
      game.sceneManager.gotoScene( next );
    }
    super.mouseClickedAction();
  }
}

// ============ CyclingTextButton ============
class CyclingTextButton extends TextButton {
  int i;
  Scene nextScene;

  CyclingTextButton( String newIdentifier, int newX, int newY, String newButtonText, int newButtonTextSize, Scene next ) {
    super( newIdentifier, newX, newY, newButtonText, newButtonTextSize );
    i = 0;
    nextScene = next;
  }

  public void display() {
    super.display();
    children.get(i).display();
  }

  public void mouseClickedAction() {
    if (i+1 < children.size()) {
      i++;
    } else {
      game.textDisplay.setText(" ");
      game.sceneManager.gotoScene( nextScene );
    }
  }
}


// ============ HintButton ============
class HintButton extends ImageGameObject {
  TextButton textButton;
  int max_distance;

  HintButton( String newIdentifier, int newX, int newY, String buttonImageFilename, String hintText ) {
    super( newIdentifier, newX, newY, buttonImageFilename );
    textButton = new TextButton( "intText", x - 30, y - 30, hintText, 20 );
    max_distance = normalImage.width * 6;
  }

  public void display() {
    float distance = dist( mouseX, mouseY, x + normalImage.width / 2, y + normalImage.height / 2);
    if ( distance < 30 ) {
      image( normalImage, x, y );
      textButton.display();
    } else if ( distance < max_distance ) {
      tint( 255, map( distance, 50, max_distance, 255, 0 ) );  // Apply transparency without changing color
      image( normalImage, x, y );
      tint( 255, 255 );
    }
  }
}


// ============ ResetButton ============
class ResetButton extends TextButton {
  String buttonText;
  int buttonTextSize;

  int offset;

  ResetButton( String newIdentifier, int newX, int newY, String newButtonText, int newButtonTextSize ) {
    super(  newIdentifier, newX, newY, newButtonText, newButtonTextSize );
  }
  public @Override
    void mouseClickedAction() {
    game.reset();
  }
}
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

  public abstract void create();

  public void reset() {
    create();
  }

  public void update() {  //Every frame
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
  public void handleMousePressed() {
    inventory.checkMousePressed();
    sceneManager.getCurrentScene().checkMousePressed();
  }

  public void handleMouseClicked() {
    if (!clicker.dragging) {
      inventory.checkMouseClicked();
      mainCharacter.checkMouseClicked();
      sceneManager.getCurrentScene().checkMouseClicked();
    }
  }
}
/*
=====================================================================
 Name: sys_GameObjects
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for vieuwable objects,many of wich meant to also be extended upon - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding FadinImage, to portrai ImageGameObjects with a fade effect
=====================================================================
 */

// ============ GameObject ============
//Most things in the game are extended from this class, as they can be given to the scene
abstract class GameObject {
  private String identifier;
  protected int x;
  protected int y;
  protected int w;
  protected int h;

  public String displayText;

  public boolean isVisible = true;

  protected boolean mouseIsHovering;

  public  ArrayList<GameObject> children   = new ArrayList<GameObject>();
  private ArrayList<GameObject> toBePurged = new ArrayList<GameObject>();

  public GameObject( String anIdentifier, int newX, int newY ) {
    identifier = anIdentifier;
    x = newX;
    y = newY;
  }

  public abstract boolean isInside( int px, int py );

  public void display() {
    if ( isVisible ) {
      for ( int i = 0; i < children.size(); i++ ) {
        GameObject child = children.get(i);
        if ( child.isVisible ) {
          child.display();
        }
      }
    }
  }

  public void setX( int inpX) {
    x= inpX;
  }
  public void setY( int inpY) {
    y= inpY;
  }
  public void setW( int inpW) {
    w= inpW;
  }
  public void setH( int inpH) {
    h= inpH;
  }

  public void displayText() {  //A way for the class to display their own displayText
    game.textDisplay.setText(displayText);
  }
  
  public void mouseClickedAction() {
  }


// ========== Children ==========
//Function's for handeling the children LinkedList
  public void addChild( GameObject child ) {
    if ( ! children.contains( child ) ) {
      children.add( child );
    }
  }

  public boolean isChild( GameObject object ) {
    return ! toBePurged.contains( object ) && children.contains( object );
  }

  public void removeChild( GameObject child ) {
    if ( children.contains( child ) && ! toBePurged.contains( child ) ) {
      toBePurged.add( child );
    }
  }

  public void setVisibility( boolean newVisibility ) {
    isVisible = newVisibility;
    setVisibilityChildren( newVisibility );
  }

  public void setVisibilityChildren( boolean newVisibility ) {
    for ( GameObject child : children ) {
      child.setVisibility( newVisibility );
    }
  }

  protected void purgeChildren() {
    children.removeAll( toBePurged );
    toBePurged.clear();
    for ( GameObject child : children ) {
      child.purgeChildren();
    }
  }

  public int nrOfChildren() {
    return children.size() - toBePurged.size();
  }

  public void checkMousePressed() {

    if ( isVisible && isInside( mouseX, mouseY ) ) {
      for (GameObject child : children ) {
        child.checkMousePressed();
      }
    }
  }


  public void checkMouseClicked() {
    // override this method if you need invisible object to be interactable
    if ( isVisible && isInside( mouseX, mouseY ) ) {
      mouseClickedAction();
      for (GameObject child : children) {
        child.checkMouseClicked();
      }
    }
  }

//Somethimes the cursor class wants to give their item to other gameObjects, this function decides if the gameObject (or it's children) wants something to do with that item
  public boolean recieveItem(GameObject recieved) {
    for (GameObject child : children) {
      if (child.recieveItem(recieved)) return true;
    }
    return false;
  }

  public String getIdentifier() {
    return identifier;
  }

  @Override 
    public boolean equals( Object object ) { 
    if ( object == this ) {
      return true;
    } 
    if ( object == null || object.getClass() != this.getClass() ) { 
      return false;
    } 
    GameObject otherGameObject = (GameObject) object; 
    return otherGameObject.getIdentifier().equals( this.identifier );
  } 

  @Override 
    public int hashCode() {
    final int prime = 11;
    return prime * this.identifier.hashCode();
  }

  @Override
    public String toString() {
    return toString( "\n" );
  }


  public String toString( String indentation ) {
    String result = indentation + identifier + "\t(" + x + "," + y + ")["+  w + "," + h + "]\t" + isVisible;
    if ( children.size() > 0 ) {
      indentation += "    ";
      for ( GameObject child : children ) {
        result = result + child.toString( indentation );
      }
    }
    return result;
  }
}


// ============ RectangularGameObject ============
abstract class RectangularGameObject extends GameObject {
  public RectangularGameObject( String newIdentifier, int newX, int newY ) {
    super( newIdentifier, newX, newY );
  }

  public RectangularGameObject( String newIdentifier, int newX, int newY, int newWidth, int newHeight ) {
    super( newIdentifier, newX, newY );
    w = newWidth;
    h = newHeight;
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, w, h );
  }
}


// ============ CircularGameObject ============
abstract class CircularGameObject extends GameObject {
  protected int radius;

  public CircularGameObject( String newIdentifier, int newX, int newY ) {
    super( newIdentifier, newX, newY );
  }

  public CircularGameObject( String newIdentifier, int newX, int newY, int newRadius ) {
    super( newIdentifier, newX, newY );
    radius = newRadius;
    w = h = 2 * radius;
  }

  public boolean isInside( int px, int py ) {
    return isPointInCircle( px, px, x, y, radius );
  }
}


// ============ ImageGameObject ============
class ImageGameObject extends RectangularGameObject {
  protected PImage normalImage;
  protected PImage hoverImage;
  protected String imagePath;

  public ImageGameObject( String newIdentifier, int newX, int newY, String imageFilename ) {
    super( newIdentifier, newX, newY );
    if ( imageFilename.length() > 0 ) {
      normalImage = loadImage( imageFilename );
      imagePath = imageFilename;
      w = normalImage.width;
      h = normalImage.height;
    }
  }

  public ImageGameObject( String newIdentifier, int newX, int newY, String imageFilename, String hoverImageFilename) {
    this( newIdentifier, newX, newY, imageFilename );
    hoverImage = loadImage( hoverImageFilename );
  }

  public void display() {
    if ( isVisible ) {
      if ( hoverImage != null && isPointInRectangle( mouseX, mouseY, x, y, w, h ) && !game.clicker.dragging ) {
        image( hoverImage, x, y, w, h );     //If there is a hover image, and the mouse hovers over the image, display that hover image
      } else {
        image( normalImage, x, y, w, h );      //If there is not a hover image, or the mouse is not hovering over the picture display the normal one
      }
      super.display();    //Use the GameObject display function to display all childs
    }
  }
}


// ============= TextObject ==============
class TextObject extends GameObject {
  boolean loaded;

  public TextObject( String anIdentifier, String text ) {
    super(anIdentifier, 0, 0);
    displayText = text;
    loaded = false;
  }

  public void display() {
    if (!loaded) {
      game.textDisplay.setTextInf(displayText);
      loaded = true;
    }
    super.display();
  }

  public boolean isInside(int x, int y) {
    return false;
  }
}


// =========== FadinImage ============
class FadinImage extends ImageGameObject {
  int alpha = 0;
  float timer = 0;

  FadinImage(ImageGameObject input) {
    super(input.getIdentifier(), input.x, input.y, input.imagePath);
    w = input.w;
    h = input.h;
  }

  public void display() {
    tint(255, alpha);
    super.display();
    tint(255, 255);

    timer += millis() / 1000;
    alpha += timer * 0.08f;

    if (alpha >= 255)
    {
      timer = 0;
    }
  }
}


//============ SoundObject =============
class SoundObject extends GameObject {
  boolean loaded;
  int gain;
  String soundFile;

  public SoundObject( String anIdentifier, String soundPath, int volume ) {
    super(anIdentifier, 0, 0);
    gain = volume;
    soundFile = soundPath;
    loaded = false;
  }

  public void display() {
    if (!loaded) {
      doAudioSample(soundFile, gain);
      loaded = true;
    }
    super.display();
  }

  public boolean isInside(int x, int y) {
    return false;
  }
}


//============= Lightning ==============
class Lightning extends ImageGameObject {
  int duration;
  int counter;

  public Lightning( String newIdentifier, String imageFilename ) {
    super( newIdentifier, 0, 0, imageFilename );
    counter = 0;
  }

  public void display() {
    if (isVisible) {
      if  (counter <= 0) {
        if (duration <= 0 ) {
          strike();
        } else {
          duration--;
        }
      } else {
        counter--;
        super.display();
      }
    }
  }

  public void strike() {
    x = (int)random(50, 1870-w);
    y = (int)random(-(h/3), 0);
    counter = 10;
    duration = (int)random(300, 900);
    doAudioSample("Close Light.wav", 0);
  }
}
/*
=====================================================================
 Name: sys_Inventory
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: A class for items that can be added to your inventory and a class for an inventory - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding sound on pickup
=====================================================================
 */

// ============ ImageGameObject ============
class InventoryItem extends ImageGameObject {
  private boolean pickedUp;
  private int largeW;
  private int largeH;
  private int largeX;
  private int largeY;
  private int smallW;
  private int smallH;
  private int smallX;
  private int smallY;

  InventoryItem( String newIdentifier, int newX, int newY, String imageFilename ) {
    super( newIdentifier, newX, newY, imageFilename );
    largeW = PApplet.parseInt(w *1.1f);
    largeH = PApplet.parseInt(h *1.1f);
    largeX = x - PApplet.parseInt(w *0.05f);
    largeY = y - PApplet.parseInt(h *0.05f);

    smallW = w;
    smallH = h;
    smallX = x;
    smallY = y;
  }

  InventoryItem( String newIdentifier, int newX, int newY, String imageFilename, String text ) {
    super( newIdentifier, newX, newY, imageFilename );
    largeW = PApplet.parseInt(w *1.1f);
    largeH = PApplet.parseInt(h *1.1f);
    largeX = x - PApplet.parseInt(w *0.05f);
    largeY = y - PApplet.parseInt(h *0.05f);

    smallW = w;
    smallH = h;
    smallX = x;
    smallY = y;

    displayText = text;
  }

  @Override
    public void mouseClickedAction() {
    if (!pickedUp) {
      super.displayText();
      pickedUp = true;
      game.sceneManager.getCurrentScene().removeChild( this );
      //game.sceneManager.getCurrentScene().puzzle.removeChild( this );
      game.inventory.addChild( this );
    }
  }

  public void display() {

    if (!pickedUp ) {
      if (isInside( mouseX, mouseY ) && !game.clicker.dragging) {
        w = largeW;
        h = largeH;
        x = largeX;
        y = largeY;
        game.clicker.hovering = true;
      } else {
        w = smallW;
        h = smallH;
        x = smallX;
        y = smallY;
      }
    } else {
      w = smallW;
      h = smallH;
    }

    super.display();
  }
}

// ============ Inventory ============
class Inventory extends RectangularGameObject {
  private int objectWidth;
  private int objectHeight;

  public Inventory( String newIdentifier, int newX, int newY, int newObjectWidth, int newObjectHeight ) {
    super( newIdentifier, newX, newY );
    objectWidth  = newObjectWidth;
    objectHeight = newObjectHeight;
    w = objectWidth + objectWidth / 5;
    isVisible = false;
  }

  @Override
    public void checkMousePressed() {
    if (isInside(mouseX, mouseY)) {
      int i = ( mouseY - y ) / objectHeight ;
      if ( i < children.size() ) {
        GameObject item = children.get( i );
        removeChild( item );
        game.clicker.equip( item );
      }
    }
  }

  public void addChild( GameObject child ) {
    super.addChild( child );
    setVisibility( true );
    h = PApplet.parseInt( objectHeight * nrOfChildren() ) + objectHeight / 5;
  }

  public void removeChild( GameObject child ) {
    println( getIdentifier() + ".removeChild()" );
    super.removeChild( child );
    h = PApplet.parseInt( objectHeight * nrOfChildren() + objectHeight / 5 );
    if ( nrOfChildren() == 0 ) {
      isVisible = false;
    }
    println( "\t" + getIdentifier() + ".removeChild() -> " + nrOfChildren() );
  }

  public void display() {
    if ( isVisible ) {
      fill( 255, 128 );
      stroke( 0 );
      strokeWeight( 2 );
      rect( x, y, w, h );
      int item_x = x + objectWidth  / 10;
      int item_y = y + objectHeight / 10;
      for ( int i = 0; i < children.size(); i++ ) {
        rect(item_x, item_y, objectWidth, objectHeight);
        GameObject item = children.get(i);
        displayItemAt( item, item_x, item_y, objectWidth, objectHeight );
        item_y += objectHeight;
        traceIfChanged( "Inventory" + item.getIdentifier(), item_y + "" );
      }
    }
  }

  private void displayItemAt( GameObject item, int item_x, int item_y, int objectWidth, int objectHeight ) {
    float scale;

    pushMatrix();
    translate( item_x, item_y );

    if (item.w > item.h) {
      scale = objectWidth / (float) item.w;
    } else {
      scale = objectHeight / (float) item.h;
    }
    scale( scale );
    translate( -item.x, -item.y );
    item.display();
    popMatrix();
  }


  public void reset() {
    children.clear();
    h = 0;
    isVisible = false;
  }
}
/*
=====================================================================
 Name: sys_Puzzles
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 27/11/2020
 Description: Several classes for puzzles that can either give or recieve GameObjects/Iventory items in specific surcomstances - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding the last puzzles
=====================================================================
 */

abstract class Puzzle extends GameObject {
  protected ArrayList<String> recivable; 
  public boolean finished;
  protected Scene myScene;

  public Puzzle( String newIdentifier, int newX, int newY, Scene assignedScene ) {
    super( newIdentifier, newX, newY );
    myScene = assignedScene;
    recivable = new ArrayList<String>();
  }

  public void mouseClickedAction() {
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, w, h );
  }

  public boolean recieveItem(GameObject recieved) {
    if (recivable.size() > 0 && isInside(mouseX, mouseY)) {
      if (recivable.contains(recieved.getIdentifier()) ) {
        recivable.remove(recieved);
        addChild(recieved);
        return true;
      }
    }
    return super.recieveItem(recieved);
  }
}


//=========== CandlePuzzle ============
public class CandlePuzzle extends Puzzle {
  ImageButton hole;
  ImageGameObject candle;

  public CandlePuzzle( Scene assignedScene, GameObject input ) {
    super( "CandlePuzzle", 935, 95, assignedScene);
    hole  = new ImageButton("hole", x, y, "hole.png", "Hm, this needs to hold something..");
    candle = new ImageGameObject("topCandle", x+17, y+5, "candleLitGreen.png");
    recivable.add(input.getIdentifier());
    addChild(hole);
    w = hole.w;
    h = hole.h;
  }

  private void testIfFinished() {
    removeChild(hole);
    game.textDisplay.setText("Light her path..");
    addChild(candle);
    game.solvedRitual ++;
    finished = true;
  }

  public boolean recieveItem(GameObject recieved) {
    if (!finished ) {
      if (isInside(mouseX, mouseY)) {
        if (recivable.contains(recieved.getIdentifier())) {
          testIfFinished();
          return true;
        }
      }
    }
    return false;
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, w, h );
  }
}

//=========== BrewPlacementPuzzle ============
public class BrewPlacementPuzzle extends Puzzle {
  //PImage test = loadImage("finishedBrew.png");

  public BrewPlacementPuzzle( Scene assignedScene, GameObject brew ) {
    super( "BrewPlacementPuzzle", 401, 730, assignedScene);
    recivable.add(brew.getIdentifier());
    w = x+145;
    h = y+177;
  }

  public boolean recieveItem(GameObject recieved) {
    recieved.x = 367;
    recieved.y = 752;
    if (super.recieveItem(recieved)) {
      game.solvedRitual ++;
      return true;
    }
    return false;
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, w, h );
  }
}


//=========== CabinetPuzzle ============
public class CabinetPuzzle extends Puzzle {
  ImageButton cabinet;
  GameObject inside;

  public CabinetPuzzle( Scene assignedScene, GameObject head1 ) {
    super( "CabinetPuzzle", 245, 284, assignedScene);
    cabinet = new ImageButton("cabinet", x, y, "right_KitchenDoor.png", "Lets see what is inside");
    addChild(cabinet);
    inside = head1;
    inside.setVisibility(false);
    myScene.addChild(inside);
  }

  public void display() {
    if (!finished) {
      //cabinet.display();
      super.display();
    }
  }

  public void mouseClickedAction() {
    if (isVisible) {
      game.textDisplay.setText("So that’s where you were hiding.");
      setVisibility(false);
      myScene.children.get(myScene.children.indexOf(inside)).setVisibility(true);
      doAudioSample("Creak.wav", 0);
      finished = true;
    }
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, cabinet.w, cabinet.h );
  }
}

//============ StatuePuzzle ============
public class StatuePuzzle extends Puzzle {
  private ImageButton statue;

  private int snapX1;
  private int snapY1;

  private int snapX3;
  private int snapY3;

  private GameObject head1;
  private GameObject head3;

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, statue.x, statue.y, statue.w, statue.h );
  }

  public StatuePuzzle(Scene assignedScene, GameObject head1in, GameObject head3in) {
    super( "StatuePuzzle", 1351, 680, assignedScene);
    statue = new ImageButton( "statue", x, y, "statueHydra.png", "It must be complete first...");
    addChild( statue );
    head1 = head1in;
    head3 = head3in;

    snapX1 = 1420;
    snapY1 = 679;

    snapX3 = 1383;
    snapY3 = 646 ;
  }

  private boolean testIfFinished() {
    if (children.size() >= 3) {
      return true;
    } else {
      return false;
    }
  }

  public boolean recieveItem(GameObject recieved) {
    if (!finished ) {
      if (isInside(mouseX, mouseY)) {
        if (recieved.getIdentifier() == head3.getIdentifier()) {
          recieved.x = snapX1;
          recieved.y = snapY1;
          game.textDisplay.setText("This sends a shiver down my spine...");
          addChild(recieved);
          return true;
        } else if (recieved.getIdentifier() == head1.getIdentifier()) {
          recieved.x = snapX3;
          recieved.y = snapY3;
          if (recieved == head1) game.textDisplay.setText("Its judging me...");
          addChild(recieved);
          return true;
        }
      }
    }
    return false;
  }

  public void display() {
    if (!finished) {
      if (testIfFinished()) {
        finished = true;
        game.textDisplay.setText("Come forth and bring her back to me..");
        statue.displayText = "Come forth and bring her back to me..";
        game.solvedRitual ++;
      }
    }
    super.display();
  }
}

//============ BrewingPuzzle ============
public class BrewingPuzzle extends Puzzle {
  private ImageButton blueWater = new ImageButton( "blueWater", x, y, "blueWater.png", "Just boiling water..." );
  private ImageButton greenWater = new ImageButton( "greenWater", x, y, "greenWater.png", "It smells horrific..." );
  private ImageButton yellowWater = new ImageButton( "yellowWater", x, y, "yellowWater.png", "The Ritual calls for sacrifice..." );
  
  private InventoryItem finishedBrew;
  private InventoryItem finalIngrediant;
  
  String brewColor = "blue";

  public BrewingPuzzle(Scene assignedScene, InventoryItem inpIngridiant1, InventoryItem inpIngridiant2, InventoryItem inpFinalIngrediant, InventoryItem reward) {
    super( "BrewingPuzzle", reward.x, reward.y, assignedScene);
    finishedBrew = reward;

    addChild(blueWater);
    addChild(greenWater);
    addChild(yellowWater);

    recivable.add(inpIngridiant1.getIdentifier());
    recivable.add(inpIngridiant2.getIdentifier());
    recivable.add(inpFinalIngrediant.getIdentifier());
    finalIngrediant = inpFinalIngrediant;
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, blueWater.x, blueWater.y, blueWater.w, blueWater.h );
  }

  public void display() {
    if (!finished) {
      switch(brewColor) {
      case "blue":
        children.get(children.indexOf(blueWater)).isVisible = true;
        children.get(children.indexOf(greenWater)).isVisible = false;
        children.get(children.indexOf(yellowWater)).isVisible = false;
        break;

      case "green":
        children.get(children.indexOf(blueWater)).isVisible = false;
        children.get(children.indexOf(greenWater)).isVisible = true;
        children.get(children.indexOf(yellowWater)).isVisible = false;
        break;

      case "yellow":
        children.get(children.indexOf(blueWater)).isVisible = false;
        children.get(children.indexOf(greenWater)).isVisible = false;
        children.get(children.indexOf(yellowWater)).isVisible = true;
        break;
      }
      super.display();
    }
  }

  public boolean recieveItem(GameObject recieved) { 
    if (!finished ) {
      String recievedName = recieved.getIdentifier();
      if (recivable.contains(recievedName) && isInside(mouseX, mouseY)) {
        if (recievedName != finalIngrediant.getIdentifier()) {
          if (recivable.size() == 3 ) {
            brewColor = "green";
            game.textDisplay.setText("Hm, not finished yet...");
          } else if (recivable.size() == 2 && recievedName != finalIngrediant.getIdentifier()) {
            brewColor = "yellow";
            game.textDisplay.setText("Ah, looks better, but not finished...");
          }
          recivable.remove(recieved.getIdentifier());
          return true;
        } else if (recivable.size() == 1 && recievedName == finalIngrediant.getIdentifier()) {
          finished = true;
          game.textDisplay.setText("My blood for her life...");
          myScene.addChild(finishedBrew);
          return true;
        } else if (recivable.size() != 1 && recievedName == finalIngrediant.getIdentifier()) {
          game.textDisplay.setText("Blood should be added last...");
          game.inventory.addChild(recieved);
          return true;
        }
      }
    }
    return false;
  }
}
/*
=====================================================================
 Name: sys_Rules
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for adding global rules to a game - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding RitualsDone rule
=====================================================================
 */

abstract class Rule {    //A good way to create rules to check each frame, these can be stored in an array and used (Rulebook)
  public abstract boolean condition();
  public void action_if_true() {
  }    //If the condition is true, the rulebook executes this function
  public void action_if_false() {
  }    //If the condition is false, the rulebook executes this function
}

class RitualsDone extends Rule {
  boolean done;

  RitualsDone() {
  }

  public boolean condition() {
    return (game.solvedRitual >= 3) && (!done);
  }

  public void action_if_true( ) {
    game.textDisplay.setText("The ritual... IT IS READY");
    done = true;
  }

  public void action_if_false() {
  }
}

class RuleBook {
  private ArrayList<Rule> rules = new  ArrayList<Rule>();    //An array to store all rules

  public void reset() {    //Clear all rules
    rules = new  ArrayList<Rule>();
  }

  public void addRule( Rule rule ) {    //Add rules
    rules.add( rule );
  }

  public void checkAndExecuteRules() {    //Check and apply all rules
      for ( Rule rule : rules ) {  //Loop for each rules
      if ( rule.condition() ) {  //For each rule: Check if true
        rule.action_if_true();  //If rule is true, run it's true action
      } else {
        rule.action_if_false();  //If rule is false, run it's false action
      }
    }
  }
}
/* //<>//
 =====================================================================
 Name: sys_Scenes
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for adding and managing diffrent scenes of a game - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding cinamatic scenes, meant to stop the background music and portrai the maincharacter in a diffrent way
 =====================================================================
 */




//Scenes are a type of ImageGameObject, like other gameobjects they portray all childs given to them, they get called by the Scenemanager one by one based on inputs from SceneChangeButtons
class Scene extends ImageGameObject {
  AudioPlayer player;
  protected boolean rain;
  protected boolean audio;
  protected boolean animating;
  public boolean displayMainChar;
  public boolean loaded;
  public int charX;

  public Scene( String sceneName, String backgroundImageFile, String audioFilePath, int audioGain, boolean rainbool) {    //When creating a scene the name and the background of that scene are given
    super( sceneName, 0, 0, backgroundImageFile );    //Create use the constructor of ImageGameObject to construct the scene
    rain = rainbool;
    audio = (audioFilePath != "");
    animating = false;
    if (audio) {
      player = minim.loadFile(audioFilePath);
      player.setGain(audioGain);
      player.loop();
      player.pause();
    }
  }

  public Scene( String sceneName, String backgroundImageFile, String audioFilePath, int audioGain, boolean rainbool, int cX) {    //When creating a scene the name and the background of that scene are given

    super( sceneName, 0, 0, backgroundImageFile );    //Create use the constructor of ImageGameObject to construct the scene
    audio = (audioFilePath != "");
    rain = rainbool;
    animating = false;
    if (audio) {
      player = minim.loadFile(audioFilePath);
      player.setGain(audioGain);
      player.loop();
      player.pause();
    }

    displayMainChar = true;
    charX = cX;
  }

  public void display() {
    if (!loaded) {
      onLoad();
    }
    if (rain) {
      super.display();
      if (displayMainChar) {
        game.mainCharacter.animMainChar(charX);
      }
      game.rain_hallway.doAnim(new PVector(0, 0), new PVector(width, height), 80);
    } else {
      game.rain_hallway.doAnim(new PVector(0, 0), new PVector(width, height), 80);
      super.display();
      if (displayMainChar) {
        game.mainCharacter.animMainChar(charX);
      }
    }
  }

  public void onLoad() {
    if (audio) { 
      player.play();
      game.mainCharacter.retNormal();
    }
    loaded = true;
  }

  public void onLeave() {
    if (audio) {
      player.pause();
    }
    loaded = false;
  }

  public String getSceneName() {    //Get the name of the scene
    return getIdentifier();
  }
}

//========= CinamaticScene =========
class CinamaticScene extends Scene {
  float scale = 1;

  public CinamaticScene( String sceneName, String backgroundImageFile, String audioFilePath, int audioGain) {
    super( sceneName, backgroundImageFile, audioFilePath, audioGain, false );
    animating = false;
  }

  public void onLoad() {
    super.onLoad();
    game.music.pause();
    game.lightning.setVisibility(false);
  }

  public void onLeave() {
    super.onLeave();
    game.lightning.setVisibility(true);
    game.music.play();
    game.music.loop();
  }

  public void display() {
    super.display();
    //mainCharacter.display();
  }
}

class EmptyScene extends Scene {    //A way to create empty scenes
  public EmptyScene() {
    super( "", "", "", 0, false);
    w = width;
    h = height;
  }
}

class SceneManager {
  private HashMap<String, Scene> scenes;
  private Stack<Scene> scenesStack;
  final Scene EMPTY_SCENE = new EmptyScene();

  public SceneManager() {
    reset();
  }

  public boolean isEmpty() {
    return scenesStack.peek() == EMPTY_SCENE;
  }
  /**
   Adds a new Scene to the scenes HashMap, but doesn't change the scenesStack if the stack is not empty.
   If the stack is empty, then it will place the current scene as the first one.
   */
  public void addScene( Scene scene ) {
    scenes.put( scene.getSceneName(), scene );
    if ( isEmpty() )
    {
      scenesStack.pop();
      scenesStack.push( scene );
    }
  }

  public void gotoScene( Scene scene ) {
    getCurrentScene().onLeave();
    if ( scenes.containsValue( scene.getSceneName() ) ) {
      addScene( scene );
    }
    scenesStack.push( scene );
  }

  public void gotoScene( String sceneName ) throws Exception {
    if ( sceneName.equals( "" ) ) {
      gotoPreviousScene();
    } else if ( scenes.containsKey( sceneName ) ) {
      scenesStack.push( scenes.get( sceneName ) );
    } else {
      throw new Exception("Scene '" + sceneName + "' not found. Was it added to the sceneManager?" );
    }
  }

  public void gotoPreviousScene() {
    if ( scenesStack.size() > 1 ) {
      scenesStack.pop();
    }
  }

  public Scene getCurrentScene() {
    return scenesStack.peek();
  }

  public void updateScene() {
    getCurrentScene().purgeChildren();
    getCurrentScene().display();
  }

  public void reset() {
    scenes = new HashMap<String, Scene>();
    scenesStack = new Stack<Scene>();
    scenesStack.push( EMPTY_SCENE );
  }
}
/*
 =====================================================================
 Name: sys_Tools
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Date last updated: 26/11/2020
 Description: Several functions for playing sounds and a class for portraying text in a fancy way - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Make the textdisplay function flexible -> display time based on text length, of the text length is really long use multiple lines
=====================================================================
 */

  //Import minim Library. (be sure it's installed by going Tools -> Library -> (search for "minim")

class TextDisplay {
  float timer = 0;
  String text = null;
  float textSize = 40;
  PVector textColor = new PVector(255, 255, 255);    //Make sure coloring is separated from alpha so custom colors are possible.
  int textAlpha = 255;
  float fadeSpeed = 0.08f;
  String fadeState = "off";                          //States: "off", "fading_in", "on_screen", "fading_out".
  float timerDuration = 0f;                          //Duration between completing fade-in and starting fade-out.
  float presetTimerDuration = 2;
  PFont travType;
  float savedMillis;                                 //Saves the amount of milliseconds since setup and compares.
  boolean inf;

  TextDisplay () {
    travType = createFont("TravelingTypewriter.ttf", textSize);
    textFont(travType);
  }

  //Update the text and make it fade in -- even if it's in the process of fading in.
  public void setText(String newText)
  {
    inf = false;
    //Use a custom duration unless not provided (temporary option)
    if (newText != null)
    {
      int l = newText.length();
      timerDuration = l / 13.5f;
    }

    //Set the new text and have it fade in.
    text = newText;
    startFade();
  }

  public void setTextInf(String newText)
  {
    setText(newText);
    inf = true;
  }

  //Check fading call the right function as result.
  public void checkFadeState()
  {  
    if (text != null) {
      if (fadeState == "fading_in")  
        fadeTextIn();  
      else if (fadeState == "on_screen")
        textDisplayTimer();
      else if (fadeState == "fading_out")
        fadeTextOut();

      drawText();
    }
  }


  //Draw the text using various settings.
  public void drawText()
  {
    noStroke();

    if (textAlpha <= 0)
      return;

    fill(20, 15, 6, textAlpha);
    //rect(0, height-45, width, height);

    //Variables for double-line dialog.
    String line1 = "";                //The first line
    String line2 = "";                //The second line (there are only 2. no need to make it larger for this project)
    boolean spaceHappened = false;    //Keeps track of when a space occurs after the condition for starting the second line is met.

    //Be sure to ignore all this if the text is null.
    if (text != null)
    {
      //Create variables to make life easier
      int l = text.length();
      int maxLength = 70;

      //Trigger double-line functionality when a line is deemed too long.
      if (l > maxLength)
      {            
        //Loop through the given text.
        for (int i = 0; i < l; i++)
        {
          //Grab the current character
          char c = text.charAt(i);

          //If character is located in the first half, add it to the top line (line1)
          if (i <= l/2) {
            line1 += c;
          } else
          {
            //If not, check if a space happened. If it does, add it to the second half.
            if (spaceHappened) {
              line2 += c;
              //If a space hasn't happened, check for it.
            } else if (c == ' ') {
              spaceHappened = true;
              //Can't find a space? Then keep adding to the top line. The second line can only start after a space.
            } else {
              line1 += c;
            }
          }
        }
      }
    }

    //Prepare text alignment, size and fill.
    textAlign(CENTER, BOTTOM);
    textSize(textSize);
    fill(textColor.x, textColor.y, textColor.z, textAlpha);

    //If double line isn't prepared, the normal one should be.
    if (line1 == "" || line2 == "") {
      text(text, width / 2, height * 0.99f);
    }
    //If double-line IS prepared, then use that instead.
    else
    {
      //Note different height positions for the 2 lines. The bottom line uses the same as regular lines.
      text(line1, width/2, height*0.96f);
      text(line2, width/2, height*0.99f);
    }
  }

  //----FADING------------------------------
  //Starts the fading. Should be able to cancel active fading animations. This function gets called once.
  public void startFade() {
    //Reset imporant values
    timer = 0;
    textAlpha = 0;
    //Start Fading in
    fadeState = "fading_in";
  }

  //Fading happens, this function gets called repeatedly until the text is completely visible.
  public void fadeTextIn() {  
    //Gradually increase alpha value.
    timer += millis() / 1000;
    textAlpha += timer * fadeSpeed;

    //Text completely visible
    if (textAlpha >= 255)
    {
      //Prepare next state.
      timer = 0;
      fadeState = "on_screen";
      savedMillis = millis();
    }
  }

  //Show text for the given duration.
  public void textDisplayTimer() {  
    //compare the current time with the time saved once fading completed. Should be as long as the timerDuration variable in seconds.
    if (millis() - savedMillis > timerDuration * 1000 && !inf) {
      fadeState = "fading_out";
      timer = 0;
    }
  }

  //Fading out using the same method as in, but reversed.
  public void fadeTextOut() {
    //use the timer to decrease alpha value.
    timer += millis() / 1000;
    textAlpha -= timer * fadeSpeed;

    //If completely invisible again, close the state.
    if (textAlpha <= 0)
    {
      timer = 0;
      fadeState = "off";
      //text = null;
    }
  }
}


//========= AUDIO =========
//Setup Minim and audio settings

Minim minim;

//Setup Minim and audio settings
public void AudioSetup() {           
  minim = new Minim(this);
}

public void startGlobalMusic() {
  game.music = minim.loadFile("songloop.wav");

  game.music.play();
  game.music.setGain(audioLevel);  
  game.music.loop();
}

//Plays file within AudioPlayer. Can loop if told to. Gain means how much volume is ADDED. use negatives to lower volume.
public void doAudioPlayer(String path, boolean doLoop, int gain)
{        
  //Load the given path and prepare for playing.
  AudioPlayer player = minim.loadFile(path);  
  //Start playing from the start of the file.
  player.play();

  //Apply audioPlayer settings
  player.setGain(gain);  

  //Loop if told to loop.
  if (doLoop)
    player.loop();
}

//Plays file with the given path. Can't loop.
public void doAudioSample(String path, int gain)
{
  //Load the given path and prepare to play the file.
  AudioSample player = minim.loadSample(path);  
  //Play the prepared file.
  player.trigger();
  player.setGain(audioLevel + gain);
}
/*
=====================================================================
 Name: sys_Tools
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for the character to interact with the game, either with their avatar or the mouse - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Added blinking when the mouse hovers over
 =====================================================================
 */

class Char extends ImageGameObject {
  Scene dontShow;

  InventoryItem sacrifice;
  InventoryItem sacrificial;

  int normalW;
  int normalH;

  Char( String newIdentifier, int newX, int newY, String imageFilename, InventoryItem shouldSacrifice, InventoryItem sacKnife ) {
    super( newIdentifier, newX, newY, imageFilename+".png" );
    sacrifice = shouldSacrifice;
    sacrificial = sacKnife;
    normalW = w/5;
    normalH = h/5;
  }

  public void retNormal() {
    w = normalW;
    h = normalH;
  }

  public boolean isInside( int px, int py ) {
    return isPointInRectangle( px, py, x, y, w, h );
  }

  float charlesHeight = 0;          //Mofifies Charles' height (and Y-position)
  int maxCharlesHeight = 9;        //Determines maximum amount of additional height Charles gains
  float charlesGrowSpeed = 0.375f/2;  //Determines the speed at which charles 'grows' (breathes)
  boolean growing = true;           //Determines if Charles grows or resets to base (inhale or exhale)
  int charlesNormalH = 0;           //Charles base height value
  int charlesNormalY = 0;           //Charles base Y position value

  //Animate Charles to be breathing
  public void animMainChar(int charX) {
    //Get these values ONCE. (here instead of setup to make it easier for the other programmer to implement)
    if (charlesNormalH == 0) {
      charlesNormalH = game.mainCharacter.h;
      charlesNormalY = game.mainCharacter.y;
    }

    //GROWING
    if (growing) {
      //Increase Charles' height every frame.
      charlesHeight += charlesGrowSpeed;

      //If max height reached, start shrinking
      if (charlesHeight  >= maxCharlesHeight) {
        growing = false;
      }
      //SHRINKING
    } else if (!growing) {
      //Decrease Charles' height every frame
      charlesHeight -= charlesGrowSpeed;

      //If height is back at 0, start growing again.
      if (charlesHeight <= 0)
        growing = true;
    }

    //apply changes and display character
    h = charlesNormalH + (int)charlesHeight;
    y = charlesNormalY - (int)(charlesHeight);
    display(charX);
  }

  public void display(int charX) {
    x = charX;
    if (isPointInRectangle( mouseX, mouseY, x, y, w, h )) {
      game.clicker.hovering = true;
    }
    super.display();
  }

  public boolean recieveItem(GameObject recieved) {
    if (recieved.getIdentifier() == sacrificial.getIdentifier()) {
      sacrifice.pickedUp = true;
      game.inventory.addChild( sacrifice );
      game.textDisplay.setText("This is all for her...");
      return true;
    } else {
      return false;
    }
  }

  public void checkMouseClicked() {
    if (isInside(mouseX, mouseY)) {
      game.textDisplay.setText("I should continue my work");
    }
  }
}

class Cursor extends ImageGameObject {
  public boolean hovering;
  public boolean dragging;
  public GameObject equipped;

  protected PImage draggingImage;

  public Cursor( String newIdentifier, int newX, int newY, String imageFilename, String hoverImageFilename, String draggingImageFilename  ) {
    super( newIdentifier, newX, newY, imageFilename, hoverImageFilename);

    if ( draggingImageFilename.length() > 0 ) {
      draggingImage = loadImage( draggingImageFilename );
    }
    w = 50;
    h = 50;
  }

  public void equip( GameObject equip) {
    equipped = equip;
  }


  public void drop(GameObject dropTo) {
    if (!dropTo.recieveItem(equipped)) {
      game.inventory.addChild(equipped);
      game.textDisplay.setText("That does not fit there");
    }
    equipped = null;
  }

  public void display() {
    x = mouseX;
    y = mouseY;

    if ( isVisible ) {
      if (equipped != null && mousePressed) {
        dragging = true;
        equipped.setX(  x - equipped.w/3  );
        equipped.setY(  y - equipped.h/3  );
        equipped.display();
      } else if (equipped != null && !mousePressed) {
        if (game.mainCharacter.isInside(mouseX, mouseY)) {
          drop(game.mainCharacter);
        } else {
          drop(game.sceneManager.getCurrentScene());
        }
        dragging = false;
      }

      if (draggingImage != null && dragging) {
        image( draggingImage, x, y, w, h );     //When dragging, display the dragging image
      } else if ( hoverImage != null && hovering ) {  
        image( hoverImage, x, y, w, h );     //When hovering, display hover image
        hovering = false;
      } else {
        image( normalImage, x, y, w, h );      //If not, display normal image
      }
    }
  }
}
/*
=====================================================================
 Name: sys_Rules
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 18/11/2020
 Description: Several utility functions - Used in The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: NONE - almost entirely unchanged
=====================================================================
*/

// Collision detection and tracing functions

// Collision detection
// copied and adapted from http://www.jeffreythompson.org/collision-detection/point-rect.php

// POINT/RECTANGLE 
public boolean isPointInRectangle( int px, int py, int rx, int ry, int rw, int rh ) {
  return px >= rx && px <= ( rx + rw ) && py >= ry && py <= ( ry + rh ) ;
}

public boolean isPointInCircle( int px, int py, int cx, int cy, int radius ) {
  float dx = px - cx;
  float dy = py - cy;
  return dx * dx + dy * dy <= radius + radius;
}

// trace functions: 
//    traceWithTime( String traceMessage )
//    traceIfChanged( String id , String logLine )




private static float start = System.nanoTime();
private static Map<String, String> logid2line = new HashMap<String, String>();

/**
 * Only logs data associated with an id, if and only if the *data* has changed.
 */
public void traceIfChanged( String id, String logLine )
{
  if ( !logid2line.containsKey(id) || !logid2line.get(id).equals( logLine ) ) {
    println( id + " = " + logLine );
    logid2line.put( id, logLine );
  }
}


/**
 * Traces a string preceded with the current time.
 * @param traceMessage  Message to be traced.
 */
public void traceWithTime( String traceMessage )
{
  float now = timeSinceStartInSeconds();

  println( now + " > " + traceMessage );
}

/**
 * Returns the number of seconds since the start of the execution.
 */
public int timeSinceStartInSeconds()
{
  return (int) ( ( System.nanoTime() - start ) / 1e9f );
}

/*Soundstuff*/
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "ProjectMansion_2020_11_27_V1_FinalCleanup" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
