/*
 * Copyright 2015 Jewel of the East
 */
package FRSJoE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Pritam Rungta
 */

public class SearchManager {

    static String[] table_sj = new String[24];
    static String[] table_sa = new String[25];
    static String[][] FlightTemp = new String[50][11];
    static String[][] s;
    static int booking_date, total_seats, c;
    String qstr, qdate;
    int qNumPass;

    public void SearchFlights(String[] Query) throws IOException{
        qstr = Query[0];
        
        qdate = Query[1].concat(" OCT");
        
        qNumPass = Integer.parseInt(Query[2]);
       
        s = search(qstr,qdate, qNumPass);
        
        /*
         Check if flights are available
         if no flights found and you try to book one
         then both txt files will be corrupted
         and datamanager class needs to be run again to make those files.
         seats will be checked individually at each search
         */

        
    }

    public String[][] getFlightDetails() {
        return s;
    }
    private static String[][] search(String sour, String date, int seats) throws IOException {
        BufferedReader sj = new BufferedReader(new FileReader("spicejet.txt"));
        BufferedReader sa = new BufferedReader(new FileReader("silkair.txt"));
        StringTokenizer date_token = new StringTokenizer(date, " ");
        int dt = Integer.parseInt(date_token.nextToken());
        booking_date = dt - 1;
        total_seats = seats;
        for (int i = 0; i < table_sj.length; i++) {
            table_sj[i] = sj.readLine();
            table_sa[i] = sa.readLine();
        }
        table_sa[24] = sa.readLine();
        sj.close();
        sa.close();
        StringTokenizer flight_sj = new StringTokenizer(table_sj[dt - 1], "|");
        String sj_flights[][] = new String[50][6];
        int flight_count_sj = 0;
        while (flight_sj.hasMoreTokens()) {
            StringTokenizer flight = new StringTokenizer(flight_sj.nextToken(), ",");
            String[] flights = new String[6];
            for (int i = 0; i < 6; i++) {
                flights[i] = flight.nextToken();
            }
            for (int i = 0; i < 6; i++) {
                sj_flights[flight_count_sj][i] = flights[i];
            }
            flight_count_sj++;
        }
        String sa_flights[][][] = new String[2][50][5];
        StringTokenizer flight_sa = new StringTokenizer(table_sa[dt - 1], "|");
        int flight_count_sa = 0;
        while (flight_sa.hasMoreTokens()) {
            StringTokenizer flight = new StringTokenizer(flight_sa.nextToken(), ",");
            String[] flights = new String[5];
            for (int i = 0; i < 5; i++) {
                flights[i] = flight.nextToken();
            }
            for (int i = 0; i < 5; i++) {
                sa_flights[0][flight_count_sa][i] = flights[i];
            }
            flight_count_sa++;
        }
        int flight_count_sa1 = 0;
        StringTokenizer flight_sa1 = new StringTokenizer(table_sa[dt], "|");
        while (flight_sa1.hasMoreTokens()) {
            StringTokenizer flight = new StringTokenizer(flight_sa1.nextToken(), ",");
            String[] flights = new String[5];
            for (int i = 0; i < 5; i++) {
                flights[i] = flight.nextToken();
            }
            int temp_time = Integer.parseInt(flights[2]);
            flights[2] = String.valueOf((((temp_time / 100) + 24) * 100) + (temp_time % 100));
            for (int i = 0; i < 5; i++) {
                sa_flights[1][flight_count_sa1][i] = flights[i];
            }
            flight_count_sa1++;
        }
        c = 0;
        for (int i = 0; i < flight_count_sj; i++) {
            if (!(sj_flights[i][0].equalsIgnoreCase(sour)) || Integer.parseInt(sj_flights[i][5]) < seats) {
                continue;
            }
            for (int j = 0; j < flight_count_sa; j++) {
                if (!(sa_flights[0][j][0].equalsIgnoreCase(sj_flights[i][1])) || Integer.parseInt(sa_flights[0][j][4]) < seats) {
                    continue;
                }
                int temp_arr = Integer.parseInt(sj_flights[i][4]), temp_dep = Integer.parseInt(sa_flights[0][j][2]);
                if (temp_dep - temp_arr < 200 || temp_dep - temp_arr > 600) {
                    continue;
                }
                FlightTemp[c][0] = sj_flights[i][0];
                FlightTemp[c][1] = sj_flights[i][3];
                FlightTemp[c][2] = sj_flights[i][2];
                FlightTemp[c][3] = sj_flights[i][4];
                FlightTemp[c][4] = sj_flights[i][1];
                FlightTemp[c][5] = sa_flights[0][j][2];
                FlightTemp[c][6] = sa_flights[0][j][1];
                FlightTemp[c][7] = sa_flights[0][j][3];
                FlightTemp[c][8] = String.valueOf(i);
                FlightTemp[c][9] = String.valueOf(j);
                FlightTemp[c][10] = "0";
                c++;
            }
            for (int j = 0; j < flight_count_sa1; j++) {
                if (!(sa_flights[1][j][0].equalsIgnoreCase(sj_flights[i][1])) || Integer.parseInt(sa_flights[1][j][4]) < seats) {
                    continue;
                }
                int temp_arr = Integer.parseInt(sj_flights[i][4]), temp_dep = Integer.parseInt(sa_flights[1][j][2]);
                if (temp_dep - temp_arr < 200 || temp_dep - temp_arr > 600) {
                    continue;
                }
                FlightTemp[c][0] = sj_flights[i][0];
                FlightTemp[c][1] = sj_flights[i][3];
                FlightTemp[c][2] = sj_flights[i][2];
                FlightTemp[c][3] = sj_flights[i][4];
                FlightTemp[c][4] = sj_flights[i][1];
                FlightTemp[c][5] = sa_flights[1][j][2];
                FlightTemp[c][6] = sa_flights[1][j][1];
                FlightTemp[c][7] = sa_flights[1][j][3];
                FlightTemp[c][8] = String.valueOf(i);
                FlightTemp[c][9] = String.valueOf(j);
                FlightTemp[c][10] = "1";
                c++;
            }
        } 
        /*
        sorting available flights according to increasing order 
        of total duration
        
        */
        int ch1, ch2, ch3, ch4, time1, time2, time3, time, pos, i, j;
        for (i = 0, pos = 0; i < c; i++) {
            ch1 = Integer.parseInt(FlightTemp[i][1]);
            ch2 = Integer.parseInt(FlightTemp[i][3]);
            ch3 = Integer.parseInt(FlightTemp[i][5]);
            ch4 = Integer.parseInt(FlightTemp[i][7]);
            ch1 = ch1 < 2400 ? ch1 : (((ch1 / 100) % 24) * 100) + (ch1 % 100);
            ch2 = ch2 < 2400 ? ch2 : (((ch2 / 100) % 24) * 100) + (ch2 % 100);
            ch3 = ch3 < 2400 ? ch3 : (((ch3 / 100) % 24) * 100) + (ch3 % 100);
            ch4 = ch4 < 2400 ? ch4 : (((ch4 / 100) % 24) * 100) + (ch4 % 100);
            time3 = ch4 < ch3 ? (ch4 + 2400 - ch3) : (ch4 - ch3);
            time2 = ch3 < ch2 ? (ch3 + 2400 - ch2) : (ch3 - ch2);
            time1 = ch2 < ch1 ? (ch2 + 2400 - ch1) : (ch2 - ch1);
            time = time1 + time2 + time3;
            pos = i;
            for (j = i + 1; j < c; j++) {
                ch1 = Integer.parseInt(FlightTemp[j][1]);
                ch2 = Integer.parseInt(FlightTemp[j][3]);
                ch3 = Integer.parseInt(FlightTemp[j][5]);
                ch4 = Integer.parseInt(FlightTemp[j][7]);
                ch1 = ch1 < 2400 ? ch1 : (((ch1 / 100) % 24) * 100) + (ch1 % 100);
                ch2 = ch2 < 2400 ? ch2 : (((ch2 / 100) % 24) * 100) + (ch2 % 100);
                ch3 = ch3 < 2400 ? ch3 : (((ch3 / 100) % 24) * 100) + (ch3 % 100);
                ch4 = ch4 < 2400 ? ch4 : (((ch4 / 100) % 24) * 100) + (ch4 % 100);
                time3 = ch4 < ch3 ? (ch4 + 2400 - ch3) : (ch4 - ch3);
                time2 = ch3 < ch2 ? (ch3 + 2400 - ch2) : (ch3 - ch2);
                time1 = ch2 < ch1 ? (ch2 + 2400 - ch1) : (ch2 - ch1);
                if (time1 + time2 + time3 < time) {
                    time = time1 + time2 + time3;
                    pos = j;
                }
            }
            for (j = 0; j < 11; j++) {
                FlightTemp[c][j] = FlightTemp[i][j];
                FlightTemp[i][j] = FlightTemp[pos][j];
                FlightTemp[pos][j] = FlightTemp[c][j];
            }
        }
        return FlightTemp;
    }

    public int getFlightSize() {
       // System.out.println("FlightTemp size:" + c);
        return c;
    }
    
    public static void updateSeats(int pos) throws IOException {
        BufferedWriter sj = new BufferedWriter(new FileWriter(new File("spicejet.txt")));
        BufferedWriter sa = new BufferedWriter(new FileWriter(new File("silkair.txt")));
        int sj_index = Integer.parseInt(FlightTemp[pos][8]);
        int sa_index = Integer.parseInt(FlightTemp[pos][9]);
        int bar_count = 0, i = 0;
        for (i = 0; i < table_sj[booking_date].length(); i++) {
            if (table_sj[booking_date].charAt(i) == '|') {
                bar_count++;
            }
            if (bar_count == (sj_index + 1)) {
                break;
            }
        }
        String new_seats = String.valueOf(Integer.parseInt(table_sj[booking_date].substring(i - 2, i)) - total_seats);
        if (new_seats.length() == 1) {
            new_seats = "0" + new_seats;
        }
        table_sj[booking_date] = table_sj[booking_date].substring(0, i - 2) + new_seats + table_sj[booking_date].substring(i);
        for (int j = 0; j < table_sj.length; j++) {
            sj.write(table_sj[j]);
            sj.newLine();
        }
        sj.close();
        if (FlightTemp[pos][10].equals("1")) {
            booking_date += 1;
        }
        bar_count = i = 0;
        for (i = 0; i < table_sa[booking_date].length(); i++) {
            if (table_sa[booking_date].charAt(i) == '|') {
                bar_count++;
            }
            if (bar_count == (sa_index + 1)) {
                break;
            }
        }
        new_seats = String.valueOf(Integer.parseInt(table_sa[booking_date].substring(i - 2, i)) - total_seats);
        if (new_seats.length() == 1) {
            new_seats = "0" + new_seats;
        }
        table_sa[booking_date] = table_sa[booking_date].substring(0, i - 2) + new_seats + table_sa[booking_date].substring(i);
        for (int j = 0; j < table_sa.length; j++) {
            sa.write(table_sa[j]);
            sa.newLine();
        }
        sa.close();
    }
}