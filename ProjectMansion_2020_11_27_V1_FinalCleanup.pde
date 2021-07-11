/* //<>//
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

void settings() {
  fullScreen();
  //size( 1920, 1080, FX2D  );
}

void setup() {
  AudioSetup();
  game = new TheResurrection();
}

void draw() {
  frameRate(60);
  background(12, 20, 69);
  game.update();
}


// ============ EVENT HANDLERS ============

void mouseClicked() { 
  game.handleMouseClicked();
}

void mousePressed() { 
  game.handleMousePressed();
}
