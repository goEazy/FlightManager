/*
 * Copyright 2015 Jewel of the East
 */

package FRSJoE;

/**
 *
 * @author Harsh Agarwal
 */

public class Flight {
    private final String origin,flNum1,flnum2,Dep1,Dep2,Arr1,Arr2,via,duration;
    private int NumberofSeats;
    
    public Flight(String origin,String Dep1,String flNum1,String Arr1,String via,String Dep2,
            String flNum2,String Arr2,String duration){
        this.origin = origin;
        this.Dep1=Dep1;
        this.flNum1=flNum1;
        this.Arr1=Arr1;
        this.via=via;
        this.Dep2=Dep2;
        this.flnum2=flNum2;
        this.Arr2=Arr2;
        this.duration=duration;
        this.NumberofSeats = 15;
    }
    
    public String getOrigin(){
        return origin;
    }
    public String getDep1(){
        return Dep1;
    }
    public String getflNum1(){
        return flNum1;
    }
    public String getArr1(){
        return Arr1;
    }
    public String getVia(){
        return via;
    }
    public String getArr2(){
        return Arr2;
    }
    public String getDep2(){
        return Dep2;
    }
    public String getflNum2(){
        return flnum2;
    }
    
    public int getNumberofAvailableSeats(){
        return NumberofSeats;
    }
    public void setNumberOfSeats(int a){
        this.NumberofSeats += a;
    }
    public String getDuration(){
        return duration;
    }
   
}
