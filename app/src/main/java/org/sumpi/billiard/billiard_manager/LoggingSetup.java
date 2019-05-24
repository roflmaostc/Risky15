package org.sumpi.billiard.billiard_manager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LoggingSetup extends AppCompatActivity {
    private TableLayout listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showEvents();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        showEvents();
    }



    private void showEvents(){
            listEvents = (TableLayout)findViewById(R.id.listEvents);

            listEvents.removeAllViews();

            final ArrayList<Event> ev = GameManager.getEvents();
            int height = 150;
            for(int i=0; i<ev.size(); i++){
                View tableRow = LayoutInflater.from(this).inflate(R.layout.event_row,null,false);
                Button button = (Button) tableRow.findViewById(R.id.deleteEvent);
                final Switch fault = (Switch) tableRow.findViewById(R.id.faultEvent);
                final Switch series = (Switch) tableRow.findViewById(R.id.seriesEvent);

                fault.setChecked(ev.get(i).getFault());

                series.setChecked(ev.get(i).getSeries());

                final int j = i;
                fault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        GameManager.getEvents().get(j).setFault(fault.isChecked());
                    }
                });

                series.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        GameManager.getEvents().get(j).setSeries(series.isChecked());
                    }
                });


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameManager.removeEvent(ev.get(j));
                        onResume();
                    }
                });




                TextView playerName  = (TextView) tableRow.findViewById(R.id.eventBall);

                playerName.setText(ev.get(i).getBall().getName());
                playerName.setHeight(height);

                listEvents.addView(tableRow);
            }
    }

}
