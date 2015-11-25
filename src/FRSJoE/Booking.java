/*
 * Copyright 2015 Jewel of the East
 */
package FRSJoE;

import static FRSJoE.DataManager.bwrite;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author Arnab Sadhukhan
 */
public class Booking {
    private String passName;
    private String refNum;
    private String NumPass;
    private String FltNum;
    private String DepartDate;
    private String Email;
    static long k=1;
    public Booking(String Name, int NumPassenger, String depDate, Flight sFl,String email){
        passName=Name;
        NumPass = String.valueOf(NumPassenger);
        FltNum = sFl.getflNum1().trim()+"-"+sFl.getflNum2().trim();
        DepartDate = depDate + " OCT 15";
        Email = email;
        };
    
    public String getpassName(){
        return passName;
    }
    
    public String getNumPass(){
        return NumPass;
    }
    
    public String getFltNum(){
        return FltNum;
    }
    
    public String getrefNum(){
        return refNum;
    }
    public String getDepartDate(){
        return DepartDate;
    }
    public void save(){
        
        double k = Math.random();
        k=k*10000;
        long n = (long)k; 
        refNum ="FL"+String.valueOf(n); 
        k++;
        String str = "";
        str = str+" BR No: "+refNum+" Name: "+passName+" Flights: "
                +FltNum+" Departure: "+DepartDate+" Passengers: "
                +NumPass+" Email id: "+Email;
        try{
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("BookingSummary.txt", true)));
        
        out.println(str);
       
        out.close();
        }catch (IOException e) {System.out.println("Error while closing");}

       
    }
}
