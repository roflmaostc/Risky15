package org.sumpi.risky15;


import java.io.Serializable;

/**
 * Created by lordfelice on 21/03/17.
 */

public class Ball implements Serializable {
    private int value;
    private boolean striped;

    Ball(int v, boolean s){
        value = v;
        striped = s;
    }

    public String getName(){
        return Integer.toString(value);
    }

    public boolean getStriped(){
        return striped;
    }

    public int getValue(){
        return value;

    }
}
