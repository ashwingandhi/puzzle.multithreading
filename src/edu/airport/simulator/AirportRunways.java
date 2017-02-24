package edu.airport.simulator;

/**
 *  Class AirportRunways provides definitions of contants and helper methods for the Aiport simulation.
 *  Notes: The keyword "final" in this class definition means that no creation of instances are needed to use the class.
 *         The "synchronized" keyword applied to a method means that mutual exclusion is enforced on all calls to any such method in this class.
 */

public final class AirportRunways
{
   public static final int NUM_RUNWAYS = 6;     // Number of runways in this simulation
   public static final int NUM_AIRPLANES = 7;   // Number of airplanes in this simulation
   public static final int MAX_LANDING_REQUESTS = 6; // Maximum number of simultaneous landing requests that Air Traffic Control can handle  
   // Runway numbers  
   public static final int RUNWAY_4L = 0;
   public static final int RUNWAY_4R = 1;
   public static final int RUNWAY_9 = 2;
   public static final int RUNWAY_14 = 3;
   public static final int RUNWAY_15L = 4;
   public static final int RUNWAY_15R = 5;
   
   
   public static String runwayName( int runwayNumber )
   {
      switch (runwayNumber)
      {
         case RUNWAY_4L:
            return "4L";
         case RUNWAY_4R:
            return "4R";
         case RUNWAY_9:
            return "9";
         case RUNWAY_14:
            return "14";
         case RUNWAY_15L:
            return "15L";
         case RUNWAY_15R:
            return "15R";        
         default:
            return "Unknown runway " + runwayNumber;   
      } // end switch
      
   } // end colorName()
   
   
   /**
    *  The following variables and methods are used to detect violations of the rules of this simulation.
    */
   
   private static int[] runwayInUse = new int[ NUM_RUNWAYS ]; // Keeps track of how many airplanes are attempting to land on a given runway
   
   private static int numLandingRequests = 0; // Keeps track of the number of simultaenous landing requests

   
   /**
    *  requestRunway() and finishedWithRunway() are helper methods for keeping track of the airport status
    */
    
   public synchronized static void requestRunway( int runwayNumber )
   {
      runwayInUse[ runwayNumber ]++;
      numLandingRequests++;
      
   } // end useRunway()
   
   
   public synchronized static void finishedWithRunway( int runwayNumber )
   {
      runwayInUse[ runwayNumber ]--;
      numLandingRequests--;

   } // end finishedWithRunway()
   
   
   /**
    *  Check the status of the aiport with respect to any violation of the rules.
    */
   public synchronized static void checkAirportStatus( int requestedRunway )
   {
      boolean crash = false; // Set to true if any rule is violated
      
      System.out.println( "\nChecking airport status for requested Runway " + runwayName( requestedRunway ) + "..." + requestedRunway);
      
      requestRunway( requestedRunway );
      
      // Check the number of landing requests
      System.out.println( "Number of simultaneous landing requests == " + numLandingRequests );
      
      if ( numLandingRequests > MAX_LANDING_REQUESTS )
      {
         System.out.println( "***** The number of simultaneous landing requests exceeds Air Traffic Control limit of " + MAX_LANDING_REQUESTS + "!" );
         crash = true;
      }
      
      // Check the occupancy of each runway
      for ( int i = 0; i < NUM_RUNWAYS; i++ )
      {
         System.out.println( "Number of planes landing on runway " + runwayName( i ) + " == " + runwayInUse[ i ] );
         
         if ( runwayInUse[ i ] > 1 )
         {
            System.out.println( "***** The number of planes landing on runway " + runwayName( i ) + " is greater than 1!" );
            crash = true;
         }
      }   
         
      // Check individual restrictions on each runway
      if ( (runwayInUse[ RUNWAY_9 ] > 0)
           && ((runwayInUse[ RUNWAY_4R ] > 0) || (runwayInUse[ RUNWAY_15R ] > 0)) )
      {
        System.out.println( "***** Runways 9, 4R, and/or 15R may not be used simultaneously!" );
        crash = true;
      }
      
      if ( ((runwayInUse[ RUNWAY_15L ] > 0) || (runwayInUse[ RUNWAY_15R ] > 0))
           && ((runwayInUse[ RUNWAY_4L ] > 0) || (runwayInUse[ RUNWAY_4R ] > 0 )) )
      {
         System.out.println( "***** Runways 15L or 15R may not be used simultaenously with Runways 4L or 4R!" );
         crash = true;
      }
      
      // If any of the rules have been violated, terminate the simulation
      if ( crash )
      {
         System.out.println( "***** CRASH! One or more rules have been violated. Due to the crash, the airport is closed!");
         System.exit( -1 ); // Abnormal program termination
      }   
      
      // Status check is normal
      System.out.println( "Status check complete, no rule violations (yay!)\n" );
      
   } // end checkAirportStatus()
      
} // end class AirportRunways

