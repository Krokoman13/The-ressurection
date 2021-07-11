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

  void display() {
    stroke( 0 );
    strokeWeight( 3 );
    fill( 255, 128 );
    rect(  x, y, w, h );
    fill( 0 );
    textSize( buttonTextSize );
    textAlign(LEFT, BOTTOM);
    text( buttonText, x + offset, y + offset + buttonTextSize * 0.9);

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

  void display() {
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
  @Override
    void mouseClickedAction() {
    game.reset();
  }
}
