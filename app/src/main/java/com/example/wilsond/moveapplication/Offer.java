package com.example.wilsond.moveapplication;

/**
 * Created by WILSOND on 12/28/15.
 */
public class Offer {
    private long price;
    private int time; //minutes

    public Offer(long price, int time){
        this.price = price;
        this.time = time;
    }
    public String priceToString(){
        String ans = new String("$");
        ans = ans.concat(String.valueOf(this.price));
        return ans;
    }
    public String timeToString(){
        String ans = new String();
        int auxtime = time;
        int days  = 0;
        int hours = 0;
        int minutes = 0;
        if (auxtime >= 60*24) {
            days = auxtime/(60*24);
            auxtime = auxtime%(60*24);
        }
        if (auxtime >= 60) {
            hours = auxtime/60;
            auxtime = auxtime%60;
        }
        minutes =  auxtime;
        if (days == 1) {
            ans = ans.concat("1 dia, ");
        } else if (days != 0) {
            ans = ans.concat(String.valueOf(days)+" dias, ");
        }
        if (hours == 1) {
            ans = ans.concat("1 hora, ");
        } else if (hours != 0) {
            ans = ans.concat(String.valueOf(hours)+" horas, ");
        }
        if (minutes != 1) {
            ans = ans.concat(String.valueOf(minutes) + " minutos");
        } else {
            ans = ans.concat("1 minuto");
        }
        return ans;
    }
}
