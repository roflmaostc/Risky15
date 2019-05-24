package org.sumpi.billiard.billiard_manager;

import java.io.Serializable;

/**
 * Created by lordfelice on 21/03/17.
 */

public class Event implements Serializable {
    private Player player;
    private int id;
    private Ball ball;
    private boolean fault;
    private boolean series;

    public Event (int i, Ball b, boolean f, Player p){
        id = i;
        ball = b;
        fault = f;
        player = p;
    }

    public Event (int i, Ball b, boolean f, Player p, boolean s){
        id = i;
        ball = b;
        fault = f;
        player = p;
        series = s;
    }

    public int returnBallValue(){
        return Integer.parseInt(ball.getName());
    }

    public boolean getStriped(){
        return ball.getStriped();
    }

    public Player getPlayer(){
        return player;
    }

    public boolean getFault(){
        return fault;
    }

    public Ball getBall(){
        return ball;
    }

    public boolean getSeries(){
        return series;
    }

    public void setFault(boolean b){
        fault = b;
    }

    public void setSeries(boolean b){
        series = b;
    }

}
