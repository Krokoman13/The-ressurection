/*
=====================================================================
 Name: sys_Rules
 Version: 1.0
 Auther: Bram "Krokoman13" ten Haken & Sep "That duck dude" Horsthuis 
 Based on: Work by Douwe van Twillert
 Date last updated: 18/11/2020
 Description: Several utility functions - Used in The Resurrection: A point and click game for the CMGT second project "First Contact"
 Last update: NONE - almost entirely unchanged
=====================================================================
*/

// Collision detection and tracing functions

// Collision detection
// copied and adapted from http://www.jeffreythompson.org/collision-detection/point-rect.php

// POINT/RECTANGLE 
boolean isPointInRectangle( int px, int py, int rx, int ry, int rw, int rh ) {
  return px >= rx && px <= ( rx + rw ) && py >= ry && py <= ( ry + rh ) ;
}

boolean isPointInCircle( int px, int py, int cx, int cy, int radius ) {
  float dx = px - cx;
  float dy = py - cy;
  return dx * dx + dy * dy <= radius + radius;
}

// trace functions: 
//    traceWithTime( String traceMessage )
//    traceIfChanged( String id , String logLine )

import java.util.Map;
import java.util.HashMap;

private static float start = System.nanoTime();
private static Map<String, String> logid2line = new HashMap<String, String>();

/**
 * Only logs data associated with an id, if and only if the *data* has changed.
 */
void traceIfChanged( String id, String logLine )
{
  if ( !logid2line.containsKey(id) || !logid2line.get(id).equals( logLine ) ) {
    println( id + " = " + logLine );
    logid2line.put( id, logLine );
  }
}


/**
 * Traces a string preceded with the current time.
 * @param traceMessage  Message to be traced.
 */
void traceWithTime( String traceMessage )
{
  float now = timeSinceStartInSeconds();

  println( now + " > " + traceMessage );
}

/**
 * Returns the number of seconds since the start of the execution.
 */
int timeSinceStartInSeconds()
{
  return (int) ( ( System.nanoTime() - start ) / 1e9 );
}

/*Soundstuff*/
