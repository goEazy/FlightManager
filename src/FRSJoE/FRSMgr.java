/*
 * Copyright 2015 Jewel of the East
 */
package FRSJoE;

import java.io.IOException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arnab Sadhukhan
 */
public class FRSMgr {
    private DataManager Mgr1;
    private SearchManager Mgr2;
    public BookingMgr Mgr3;
    private DisplayMgr Mgr4;

    FRSMgr(String spicepath, String silkpath){
        DataManager MgrNew = new DataManager();
       
        Mgr2 = new SearchManager();
        
        Mgr3 = new BookingMgr();
        
        MgrNew.startProcess(spicepath, silkpath); 
     
        this.Mgr1 = MgrNew;
        
    }
    
    public SearchManager getSrchMgr() {
        return Mgr2;
    }

    public BookingMgr getBookMgr() {
        return Mgr3;
    }
    
    public static void main(String args[]){
        SplashScreen s = new SplashScreen();
        s.setVisible(true);
        Thread t = Thread.currentThread();
        try{
           t.sleep(6500);
        }catch(Exception ex){}
        s.dispose();
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
             
             FRSMgr FM = new FRSMgr(args[0], args[1]);
        
             FM.Mgr4 = new DisplayMgr(FM);
        
             FM.Mgr4.showscreen1();
            }
        });
        //FRSMgr FM = new FRSMgr();
        
    }
}
