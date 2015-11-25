/*
 * Copyright 2015 Jewel of the East
 */
package FRSJoE;

/*
 first run this class to make the txt files

 storing the seats - 15

*/
import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author Harsh Agarwal
 */
public class DataManager
{
    static String month[] = {"JAN", "FEB","MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    static String day[] = {"Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};
    static int dates[] = new int[24];
    static String[] table = new String[dates.length];
    static BufferedReader br, bread;
    static BufferedWriter bw, bwrite;
    
    public void startProcess(String spicepath,String silkpath)
    {   
        try{
        File frspice = new File("spicejet.txt");
        File frsilk = new File("silkair.txt");
        
        if(!frspice.exists()){
            makeSpicejet(spicepath);
           
        }
        
        if(!frsilk.exists()){
            makeSilkair(silkpath);
            
        }
            
    }catch(IOException e){System.out.println("Error while file reding/writing");}
        
    }

    private static boolean getEffect(String fno,String from, String to) 
    {
        int my_year = 15;
        int my_month = 9;
        int my_date_from = 1;
        int my_date_to = 24;
        StringTokenizer st_from = new StringTokenizer(from," ");
        StringTokenizer st_to = new StringTokenizer(to," ");    
        int from_date = Integer.parseInt(st_from.nextToken());
        String from_month = st_from.nextToken();
        int from_year = Integer.parseInt(st_from.nextToken());
        int to_date = Integer.parseInt(st_to.nextToken());
        String to_month = st_to.nextToken();
        int to_year = Integer.parseInt(st_to.nextToken());
        
        int from_mnth = 0, to_mnth = 0;
        for(int i = 0; i < 12; i++)
        {
            if(from_month.equalsIgnoreCase(month[i]))
                from_mnth = i;
            if(to_month.equalsIgnoreCase(month[i]))
                to_mnth = i;
        }
        int mfrom = (my_year-15)*372 + (my_month)*31 + my_date_from;
        int mto = mfrom + 23;
        int ufrom = (from_year-15)*372 + (from_mnth)*31 + from_date;
        int uto = (to_year-15)*372 + (to_mnth)*31 + to_date;
        if(ufrom <= mto && uto >= mfrom)
            return true;
        return false;
//To change body of generated methods, choose Tools | Templates.    }
    }

    private static void makeSpicejet(String spicepath)throws IOException {
        br = new BufferedReader(new FileReader(spicepath));
        bw = new BufferedWriter(new FileWriter(new File("spicejet.txt")));
        for (int i = 0; i < dates.length; i++)
            bw.newLine();
        bw.close();
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        String lines;
        while((lines = br.readLine()) != null)
        {
            String temp[] = new String[9];
            StringTokenizer st = new StringTokenizer(lines,"|");
            for(int i = 0; i < temp.length; i++)
                temp[i] = st.nextToken();
            if(!getEffect(temp[3],temp[7],temp[8]))
                continue;
            if (temp[2].equalsIgnoreCase("DAILY"))
                temp[2] = "SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY";
            StringTokenizer dt = new StringTokenizer(temp[2], ", ");
            String s;
            for (int i = 0; i < dates.length; i++)
                dates[i] = 0;
            while (dt.hasMoreTokens()) 
            {
                s = dt.nextToken();
                for (int i = 0; i < dates.length; i++)
                    if (s.equalsIgnoreCase(day[i%7]))
                        dates[i] = 1;
            }
            bread = new BufferedReader(new FileReader("spicejet.txt"));
            for(int i = 0; i < table.length; i++)
                table[i] = bread.readLine();
            bread.close();
            int time_a = Integer.parseInt(temp[4].substring(0, 2));
            int time_b = Integer.parseInt(temp[4].substring(3, 5));
            if (temp[4].substring(6).equalsIgnoreCase("PM"))
                time_a += 12;
            time_a = time_a * 100 + time_b;
            int time_c = Integer.parseInt(temp[5].substring(0, 2));
            int time_d = Integer.parseInt(temp[5].substring(3, 5));
            if (temp[5].substring(6).equalsIgnoreCase("PM"))
                time_c += 12;
            time_c = time_c * 100 + time_d;
            for (int i = 0; i < dates.length; i++) {
                if(dates[i] == 0)
                    continue;
                table[i] += temp[0].trim()+","+temp[1]+","+temp[3]+","+time_a+","+time_c+",15|";
            }
            bwrite = new BufferedWriter(new FileWriter(new File("spicejet.txt")));
            for (int i = 0; i < table.length; i++) {
                bwrite.write(table[i]);
                bwrite.newLine();
            }
            bwrite.close();
        }
        br.close();
    }

    private static void makeSilkair(String silkpath) throws IOException {
        br = new BufferedReader(new FileReader(silkpath));
        bw = new BufferedWriter(new FileWriter(new File("silkair.txt")));
        for (int i = 0; i < dates.length; i++)
            bw.newLine();
        bw.close();
        br.readLine();
        br.readLine();
        br.readLine();
        String lines;
        while((lines = br.readLine()) != null) {
            String temp[] = new String[4];
            StringTokenizer st = new StringTokenizer(lines,"|");
            for(int i = 0; i < temp.length; i++)
                temp[i] = st.nextToken();
            String s = temp[0];
            temp[0] = s.substring(0, s.length()-6).toUpperCase();
            StringTokenizer dt = new StringTokenizer(temp[1], ",");
            for (int i = 0; i < dates.length; i++)
                dates[i] = 0;
            while (dt.hasMoreTokens()) 
            {
                s = dt.nextToken();
                for (int i = 0; i < dates.length; i++)
                    if (day[i%7].startsWith(s))
                        dates[i] = 1;
            }
            bread = new BufferedReader(new FileReader("silkair.txt"));
            for(int i = 0; i < table.length; i++)
                table[i] = bread.readLine();
            bread.close();
            StringTokenizer time_token = new StringTokenizer(temp[3], "/");
            String dep = time_token.nextToken();
            String arr = time_token.nextToken();
            int arr_time = Integer.parseInt(arr.substring(0, 4));
            if (arr.length() == 6)
                arr_time = ((arr_time/100) + 24)*100 + (arr_time%100);
            for (int i = 0; i < dates.length; i++) {
                if(dates[i] == 0)
                    continue;
                table[i] += temp[0]+","+temp[2]+","+dep+","+arr_time+",15|";
            }
            bwrite = new BufferedWriter(new FileWriter(new File("silkair.txt")));
            for (int i = 0; i < table.length; i++) {
                bwrite.write(table[i]);
                bwrite.newLine();
            }
            bwrite.write("|");
            bwrite.newLine();
            bwrite.close();
        }
    }
}
