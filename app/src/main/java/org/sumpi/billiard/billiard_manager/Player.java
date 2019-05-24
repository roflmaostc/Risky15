package org.sumpi.billiard.billiard_manager;

import java.io.Serializable;

/**
 * Created by lordfelice on 21/03/17.
 */

public class Player implements Serializable {
    private double points = 0.0;
    private String name;

    private double finalPoints = 0.0;
    Player(String n){
        name = n;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return name;
    }


    public double getPoints(){
        return points;
    }

    public void changePoints(double value){
        points += value;
        return;
    }

    public void savePoints(){
        finalPoints = points;
        return;
    }

    public void resetPoints(){
        points = 0.0;
        points += finalPoints;
        return;
    }
}
