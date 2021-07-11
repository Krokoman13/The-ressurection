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
  void animSetup(String prefix, int animSize)
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
  void doAnim(PVector pos, PVector scale, float animDelay)
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
