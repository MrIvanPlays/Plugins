package net.mrivanplays.timeparser;

import java.util.concurrent.TimeUnit;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class TimeParser {

    public static void main(String[] args) {
        System.out.println(convert(parseActualTime("5d")));
    }

    public static String convert(long seconds){
        long day = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        String newSeconds = second == 1 ? "1 second" : second + " seconds";
        String newMinutes = minute == 1 ? "1 minute" : minute + " minutes";
        String newHours = hours == 1 ? "1 hour" : hours + " hours";
        String newDays = day == 1 ? "1 day" : day + " days";
        if(day == 0){
            if(hours == 0){
                if(minute == 0){
                    return newSeconds;
                }else{
                    return newMinutes + " " + newSeconds;
                }
            }else{
                return newHours + " " + newMinutes + " " + newSeconds;
            }
        }else{
            return newDays + " " + newHours + " " + newMinutes + " " + newSeconds;
        }
    }

    public static int parseActualTime(String input){
        int actualTime = 0;
        if(input.endsWith("s")){
            try{
                actualTime = Integer.parseInt(input.replaceAll("s", ""));
            }catch(NumberFormatException e){
                System.out.println("Cannot parse integer");
            }
        }else if(input.endsWith("m")){
            try{
                actualTime = Integer.parseInt(input.replaceAll("m", ""))* 60;
            }catch(NumberFormatException e){
                System.out.println("Cannot parse integer");
            }
        }else if(input.endsWith("h")){
            try{
                actualTime = Integer.parseInt(input.replaceAll("h", ""))* 60 * 60;
            }catch(NumberFormatException e){
                System.out.println("Cannot parse integer");
            }
        }else if(input.endsWith("d")){
            try{
                actualTime = Integer.parseInt(input.replaceAll("d", "")) * 60 * 60 * 24;
            }catch(NumberFormatException exc){
                System.out.println("Cannot parse integer");
            }
        }
        return actualTime;
    }
}