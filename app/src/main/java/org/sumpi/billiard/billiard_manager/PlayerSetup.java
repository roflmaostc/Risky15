package org.sumpi.billiard.billiard_manager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;



public class PlayerSetup extends AppCompatActivity  {
    GameManager gm;
    String text = "Hans";

    private TableLayout listPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player__setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchName();
                Log.d("name", ""+text);

              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });
    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        showPlayers();
    }


    private void fetchName() {
        final EditText txtUrl = new EditText(this);
// Set the default text to a link of the Queen
        txtUrl.setHint("Give name");

        new AlertDialog.Builder(this)
                .setTitle("Players Name")
                .setMessage("")
                .setView(txtUrl)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        text = txtUrl.getText().toString();
                        GameManager.addPlayer(new Player(text));
                        onResume();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }


    /**
     * This methods provides the displaying of active players
     */
    private void showPlayers(){
        listPlayers = (TableLayout)findViewById(R.id.listPlayersEditing);

        listPlayers.removeAllViews();



        final ArrayList<Player> pL = GameManager.returnPlayerList();
        Log.d("blub",""+pL.size());
        int height = 80;
        for(int i=0; i<pL.size(); i++){
            View tableRow = LayoutInflater.from(this).inflate(R.layout.player_item,null,false);
            Button button = (Button) tableRow.findViewById(R.id.PlayerAction);
            button.setText("Delete");


            final int j = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameManager.removePlayer(pL.get(j),j);
                    onResume();
                }
            });


            TextView playerName  = (TextView) tableRow.findViewById(R.id.PlayerName);

            playerName.setText(pL.get(i).getName());
            playerName.setHeight(height);

            listPlayers.addView(tableRow);
        }
    }

}
