/*
 * Copyright 2015 Jewel of the East
 */

package FRSJoE;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Deepak Kumar
 */
public class DisplayMgr {

    private FRSMgr FRSManager;
    private DataManager DataMgr;
    private SearchManager SearchMgr;
    private BookingMgr BookingManager;
    private Screen1 Scr1;
    private Screen2 Scr2;
    private Screen3 Scr3;
    private Screen4 Scr4;
    private String depart,Numpass;
    public int row_len;
    public DisplayMgr(FRSMgr MgrObjFRS) {
        FRSManager = MgrObjFRS;
    }

    void showscreen1() {

        Scr1 = new Screen1();
        Scr1.invokeScreen1(this);
    }

    void showScreen2(String[] Query) {

        //System.out.println(Query[0] + " " + Query[1] + " " + Query[2]);
        SearchMgr = FRSManager.getSrchMgr();
        try{
        SearchMgr.SearchFlights(Query);
        }catch(IOException e){System.out.println("Problem while opening file");}
        String[][] ResultFlights;
        ResultFlights = SearchMgr.getFlightDetails();
        row_len=SearchMgr.getFlightSize();
        Scr2 = new Screen2(ResultFlights);
        Scr2.invokeScreen2(this);
    }

    void showScreen3(Screen2 s,Flight selectFlight,int rowindex) {

        Scr3= new Screen3(s);
        Scr3.invokeScreen3(this,selectFlight,Numpass,depart,rowindex);
    }
    void setDateScreen4(String depdate)
    {
        depart=depdate;
    
    }
    
    void setNumPassScreen3(String numpass){
    
        Numpass=numpass;
    }
    void showScreen4(String Name, int NumPass, String depDate, Flight selectFlight,int rowindex,Screen2 s,String email) {
        Scr4 = new Screen4(s);

        BookingManager = FRSManager.getBookMgr();

        Booking bookerfinal = BookingManager.Book(Name, NumPass, depDate, selectFlight,rowindex,SearchMgr,email);

        Scr4.invokeScreen4(this, bookerfinal,depart);
    }

}
