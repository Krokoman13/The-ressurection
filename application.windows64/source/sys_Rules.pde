/*
=====================================================================
 Name: sys_Rules
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 26/11/2020
 Description: Several classes for adding global rules to a game - Designed for The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: Adding RitualsDone rule
=====================================================================
 */

abstract class Rule {    //A good way to create rules to check each frame, these can be stored in an array and used (Rulebook)
  abstract boolean condition();
  void action_if_true() {
  }    //If the condition is true, the rulebook executes this function
  void action_if_false() {
  }    //If the condition is false, the rulebook executes this function
}

class RitualsDone extends Rule {
  boolean done;

  RitualsDone() {
  }

  boolean condition() {
    return (game.solvedRitual >= 3) && (!done);
  }

  void action_if_true( ) {
    game.textDisplay.setText("The ritual... IT IS READY");
    done = true;
  }

  void action_if_false() {
  }
}

class RuleBook {
  private ArrayList<Rule> rules = new  ArrayList<Rule>();    //An array to store all rules

  public void reset() {    //Clear all rules
    rules = new  ArrayList<Rule>();
  }

  public void addRule( Rule rule ) {    //Add rules
    rules.add( rule );
  }

  public void checkAndExecuteRules() {    //Check and apply all rules
      for ( Rule rule : rules ) {  //Loop for each rules
      if ( rule.condition() ) {  //For each rule: Check if true
        rule.action_if_true();  //If rule is true, run it's true action
      } else {
        rule.action_if_false();  //If rule is false, run it's false action
      }
    }
  }
}
