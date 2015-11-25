/*
 * Copyright 2015 Jewel of the East
 */
package FRSJoE;

import java.io.IOException;

/**
 *
 * @author Arnab Sadhukhan
 */
public class BookingMgr {
    
    public Booking Book(String Name, int NumPass, String depDate,
                        Flight selectFlight,int rowindex,
                        SearchManager Srchmgr,String email){
        Booking booker = new Booking(Name, NumPass, depDate, selectFlight,email);
        booker.save();
        try{
        Srchmgr.updateSeats(rowindex);
        }catch(IOException e){System.out.println("Error while updating");}
        
        return booker;
    }
    
}
