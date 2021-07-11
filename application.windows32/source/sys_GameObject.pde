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

  abstract boolean isInside( int px, int py );

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
