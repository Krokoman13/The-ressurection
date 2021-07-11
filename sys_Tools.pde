/* //<>//
 =====================================================================
 Name: sys_Tools
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Date last updated: 26/11/2020
 Description: Several functions for playing sounds and a class for portraying text in a fancy way - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Make the textdisplay function flexible -> display time based on text length, of the text length is really long use multiple lines
=====================================================================
 */

import ddf.minim.*;  //Import minim Library. (be sure it's installed by going Tools -> Library -> (search for "minim")

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
  void setText(String newText)
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

  void setTextInf(String newText)
  {
    setText(newText);
    inf = true;
  }

  //Check fading call the right function as result.
  void checkFadeState()
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
  void drawText()
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
  void startFade() {
    //Reset imporant values
    timer = 0;
    textAlpha = 0;
    //Start Fading in
    fadeState = "fading_in";
  }

  //Fading happens, this function gets called repeatedly until the text is completely visible.
  void fadeTextIn() {  
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
  void textDisplayTimer() {  
    //compare the current time with the time saved once fading completed. Should be as long as the timerDuration variable in seconds.
    if (millis() - savedMillis > timerDuration * 1000 && !inf) {
      fadeState = "fading_out";
      timer = 0;
    }
  }

  //Fading out using the same method as in, but reversed.
  void fadeTextOut() {
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
void AudioSetup() {           
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
