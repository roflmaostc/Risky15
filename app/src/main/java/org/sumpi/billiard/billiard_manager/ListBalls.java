package org.sumpi.billiard.billiard_manager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListBalls extends AppCompatActivity {

    TableLayout listStriped;

    TableLayout listSolid;

    int pressed = -1;

    int id=0;

    ArrayList<Button> buttonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_balls);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //final Switch sB = (Switch) findViewById(R.id.fault);

        final Player player = GameManager.getPlayer().get(getIntent().getIntExtra("player", 0));


        FloatingActionButton acc = (FloatingActionButton) findViewById(R.id.acceptBalls);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pressed == -1){

                }
                else {
                    completeAction(player);
                }

                /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

        showBalls();
    }

    public void completeAction(Player player){
        if (pressed < GameManager.getSolidBalls().size()) {
            GameManager.addEvent(new Event(id, GameManager.getSolidBalls().get(pressed),
                        ((Switch) findViewById(R.id.fault)).isChecked(), player, ((Switch) findViewById(R.id.series)).isChecked()));

            GameManager.removeBallFromSolid(pressed);
            pressed = -1;
        }
        else {
                GameManager.addEvent(new Event(id, GameManager.getStripedBalls().get(pressed - GameManager.getSolidBalls().size()),
                        ((Switch) findViewById(R.id.fault)).isChecked(), player, ((Switch) findViewById(R.id.series)).isChecked()));
            GameManager.removeBallFromStriped(pressed - GameManager.getSolidBalls().size());
            pressed = -1;
        }
        finish();
    }

    void updateSelected(int i){
        Button button;
        if(pressed != -1){
            button = buttonList.get(pressed);
           // button.setSelected(false);
            button.getBackground().clearColorFilter();
            //button.setBackgroundResource(android.R.drawable.btn_default);

        }
        pressed = i;
        button = buttonList.get(i);
      //  button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        //  button.setSelected(true);
    }


    private void showBalls(){
        pressed = -1;
        listStriped = (TableLayout)findViewById(R.id.listStriped);
        listStriped.removeAllViews();

        listSolid = (TableLayout)findViewById(R.id.listSolid);
        listSolid.removeAllViews();

        final ArrayList<Ball> stripeds = GameManager.getStripedBalls();
        final ArrayList<Ball> solids = GameManager.getSolidBalls();

        int height = 10;
        for(int i=0; i<solids.size()+stripeds.size(); i++){

            View tableRow = LayoutInflater.from(this).inflate(R.layout.player_item,null,false);
            Button button = (Button) tableRow.findViewById(R.id.PlayerAction);
            buttonList.add(button);

            final int j = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSelected(j);
                }
            });

            button.setHeight(height);
            if(i<solids.size()){
                button.setText(solids.get(i).getName());
                listSolid.addView(tableRow);
            }
            else{
                button.setText(stripeds.get(i-solids.size()).getName());
                listStriped.addView(tableRow);
            }
        }
    }


}
