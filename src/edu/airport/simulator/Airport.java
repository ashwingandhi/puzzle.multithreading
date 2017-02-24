package edu.airport.simulator;

/**
 *  Airport test program
 */



public class Airport
{
   
   public static void main( String[] args )
   {     
      AirportServer as = new AirportServer();
      
      Airplane ap[] = new Airplane[ AirportRunways.NUM_AIRPLANES ];
      RunAirplane apTask[] = new RunAirplane[ AirportRunways.NUM_AIRPLANES ];
      Thread apThread[] = new Thread[ AirportRunways.NUM_AIRPLANES ];
   
      // Create and launch the individual Airplane threads
      for ( int i = 0; i < AirportRunways.NUM_AIRPLANES; i++ )
      {
         ap[ i ] = new Airplane( i, as );
         apTask[ i ] = new RunAirplane( ap[ i ] );
         apThread[ i ] = new Thread( apTask[ i ] );
         apThread[ i ].start();
      }
       
   } // end main
  
} // end class Airport

