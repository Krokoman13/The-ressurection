/* //<>// //<>//
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

import java.util.Stack;
import java.util.HashMap;

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

  boolean isEmpty() {
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
