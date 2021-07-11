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
  void animMainChar(int charX) {
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
