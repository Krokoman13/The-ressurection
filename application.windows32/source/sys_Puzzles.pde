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
