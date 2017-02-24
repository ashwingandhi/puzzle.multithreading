package edu.airport.simulator;

/**
 *  A thread for the landing loop
 */

public class RunAirplane implements Runnable
{
   private Airplane ap;
   
   public RunAirplane( Airplane a )
   {
      ap = a;
   }

   
   @Override
   public void run()
   {
      ap.land();
      
   } // end run
 
} // end class Airplane

