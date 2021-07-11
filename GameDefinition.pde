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
  void create() {
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
    clicker = new Cursor( "Cursor", int(mouseX), int(mouseY), "cursorsOpenHand.png", "cursorsWithFinger.png", "cursorsWithoutFinger.png");
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
