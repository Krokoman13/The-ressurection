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
    largeW = int(w *1.1);
    largeH = int(h *1.1);
    largeX = x - int(w *0.05);
    largeY = y - int(h *0.05);

    smallW = w;
    smallH = h;
    smallX = x;
    smallY = y;
  }

  InventoryItem( String newIdentifier, int newX, int newY, String imageFilename, String text ) {
    super( newIdentifier, newX, newY, imageFilename );
    largeW = int(w *1.1);
    largeH = int(h *1.1);
    largeX = x - int(w *0.05);
    largeY = y - int(h *0.05);

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
    h = int( objectHeight * nrOfChildren() ) + objectHeight / 5;
  }

  public void removeChild( GameObject child ) {
    println( getIdentifier() + ".removeChild()" );
    super.removeChild( child );
    h = int( objectHeight * nrOfChildren() + objectHeight / 5 );
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


  void reset() {
    children.clear();
    h = 0;
    isVisible = false;
  }
}
