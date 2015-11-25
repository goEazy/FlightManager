/*
 * Copyright 2015 ArnabRaja209
 */
package FRSJoE;

/**
 *
 * @author Admin
 */
/*
 * Copyright 2015 ArnabRaja209
 */

class CompTimes{
	public int compareTimes(String dom,String intl){
		int intlTime,domTime,check,dur;String str;
		if(intl.length()>4){
			str = intl.substring(0,4);
			intlTime=Integer.parseInt(str);
		}
		else
			{   str=intl.substring(0,4);
                            intlTime=Integer.parseInt(str);}	
		
		String arr1[]=dom.split(" ");
		String arr2[]=arr1[0].split(":");
		if(arr1[1].equals("PM")){
                        check = Integer.parseInt(arr2[0]);
			if(check!=12)
			domTime=(12+Integer.parseInt(arr2[0]))*100+Integer.parseInt(arr2[1]);	
			else
			domTime=12*100+Integer.parseInt(arr2[1]);	
			}
		else{domTime=(Integer.parseInt(arr2[0]))*100+Integer.parseInt(arr2[1]);}
		if(intlTime<domTime){
			dur=2400-domTime+intlTime;
			}
		else	{
			dur=intlTime-domTime;
			}
		if(dur<=600 && dur>=200)
			check=1;
		else
			check=0;	
            return check;
        }	
	/*public String calDuration(String dom,String intl){
		int intlTime,domTime,check,dur;String str;
		if(intl.length()>4){
			str = intl.substring(0,4);
			intlTime=Integer.parseInt(str);
		}
		else
			{   str=intl.substring(0,4);
                            intlTime=Integer.parseInt(str);}	
		
		String arr1[]=dom.split(" ");
		String arr2[]=arr1[0].split(":");
		if(arr1[1].equals("PM")){
                        check = Integer.parseInt(arr2[0]);
			if(check!=12)
			domTime=(12+Integer.parseInt(arr2[0]))*100+Integer.parseInt(arr2[1]);	
			else
			domTime=12*100+Integer.parseInt(arr2[1]);	
			}
		else{domTime=(Integer.parseInt(arr2[0]))*100+Integer.parseInt(arr2[1]);}
		if(intlTime<domTime){
			dur=2400-domTime+intlTime;
			}
		else	{
			dur=intlTime-domTime;
			}
	return dur;
	}*/
public static void main(String args[]){
	CompTimes com = new CompTimes();
	int k = com.compareTimes("4:30 PM","610(+1)");
	if(k>0)
		System.out.println("Possible");
	else
		System.out.println("Not Possible");
	}
}

