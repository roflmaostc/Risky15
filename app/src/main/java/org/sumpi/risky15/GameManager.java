package org.sumpi.risky15;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lordfelice on 21/03/17.
 */


public class GameManager implements Serializable{
    //manage the balls
    private static ArrayList<Ball> stripedBalls = new ArrayList<Ball>();
    private static ArrayList<Ball> solidBalls = new ArrayList<Ball>();


    //manage the players
    private static ArrayList<Player> players =  new ArrayList<Player>();


    private static ArrayList<Event> events = new ArrayList<Event>();
    /**
     *
     */

    public static void addEvent(Event e){
        events.add(e);
        return;
    }

    /**
     * removes Ball from the event list and add it back to the available balls
     * @param e
     */
    public static void removeEvent(Event e){
        if(e.getBall().getStriped()){
            addBall(stripedBalls,e.getBall());
        }
        else{
            addBall(solidBalls,e.getBall());
        }
        events.remove(e);
    }

    /**
     * adds ball at right position in ArrayList
     * @param list
     * @param ball
     */
    private static void addBall(ArrayList<Ball> list, Ball ball){
        for(int i=0; i<list.size();i++){
            if(ball.getValue()<list.get(i).getValue()){
                list.add(i, ball);
                return;
            }
        }
        list.add(ball);
    }

    public static ArrayList<Player> getPlayer(){
        return players;
    }


    public static void addBallToSolid(Ball b){
        solidBalls.add(b);
        return;
    }


    public static void addBallToStriped(Ball b){
        stripedBalls.add(b);
        return;
    }


    public static void removeBallFromSolid(int i){
        solidBalls.remove(i);
    }

    public static void removeBallFromStriped(int i){
        stripedBalls.remove(i);
    }
    /**
     * adds a player to game
     * @param p
     */
    public static void addPlayer(Player p){
        players.add(p);
        return;
    }


    public static void removePlayer(Player p, int i){
        players.remove(i);
    }

    /**
     * returns list of players
     * @return
     */
    public static ArrayList<Player> returnPlayerList(){
        return players;
    }


    public static void calculatePoints(){
        double seriesCounter = 0.0;
        ArrayList<Integer> striped = new ArrayList<Integer>();
        ArrayList<Integer> solid = new ArrayList<Integer>();
        for(int i=1; i<9; i++){
            solid.add(i);
        }
        for(int i=9; i<16; i++){
            striped.add(i);
        }

        resetPoints();

        double pointsStriped = 0.0;
        double pointsSolid = 0.0;



        for(int i=0; i<events.size(); i++){
            if (i>0 && events.get(i).getPlayer() == events.get(i-1).getPlayer() && events.get(i).getSeries()){
                seriesCounter += 1.0;
            }
            else
            {
                seriesCounter = 0;
            }

            if(events.get(i).getStriped() == true){
                if(events.get(i).returnBallValue() == Collections.max(striped)){
                    if(events.get(i).getFault()){
                        events.get(i).getPlayer().changePoints(-Math.pow(2,seriesCounter)*Math.pow(2,pointsStriped));
                        pointsStriped -= 1.0;
                    }
                    //fault leads to negative point
                    else{
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*Math.pow(2,pointsStriped));
                        pointsStriped -= 1.0;
                    }
                }
                else{
                    if(events.get(i).getFault()) {
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*(-0.1));
                    }
                    else{
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*0.1);
                    }
                }
                removeFromArrayList(events.get(i).returnBallValue(), striped);
            }
            else{
                if(events.get(i).returnBallValue() == Collections.max(solid)){
                    if(events.get(i).getFault()){
                        events.get(i).getPlayer().changePoints(-Math.pow(2,seriesCounter)*Math.pow(2,pointsSolid));
                        pointsSolid -= 1.0;
                    }
                    //fault leads to negative point
                    else{
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*Math.pow(2,pointsSolid));
                        pointsSolid -= 1.0;
                    }
                }
                else{
                    if(events.get(i).getFault()) {
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*(-0.1));
                    }
                    else{
                        events.get(i).getPlayer().changePoints(Math.pow(2,seriesCounter)*0.1);
                    }
                }
                removeFromArrayList(events.get(i).returnBallValue(), solid);
            }
        }
    }


    private static void removeFromArrayList(int value, ArrayList<Integer> array){
            for(int i=0; i<array.size(); i++){
                if(array.get(i) == value){
                    array.remove(i);
                }
            }
            return;
    }

    private static void resetPoints(){
        for(int i=0; i<players.size(); i++){
            players.get(i).resetPoints();
        }
        return;
    }


    public static ArrayList<Ball> getSolidBalls(){
        return solidBalls;
    }

    public static ArrayList<Ball> getStripedBalls(){
        return stripedBalls;
    }

    public static String getSmallestSolid(){
        if(solidBalls.size()==0){
            return "-";
        }
        return solidBalls.get(0).getName();
    }

    public static String getSmallestStriped(){
        if(stripedBalls.size()==0){
            return "-";
        }
        return stripedBalls.get(0).getName();
    }

    public static void resetEvents(){
        events = new ArrayList<>();
    }

    public static void savePoints(){
        for(int i=0; i< players.size(); i++){
            players.get(i).savePoints();
        }
        return;
    }

    public static ArrayList<Event> getEvents(){
        return events;
    }

}
