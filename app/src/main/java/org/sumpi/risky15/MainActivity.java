package org.sumpi.risky15;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //this Game Manager controlls a game relevant informations


    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private ArrayList<Player> drawn = new ArrayList<>();

    private TableLayout listPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate Game_Manager


        //create menue options
        createMenu();
        //first we have to define all balls
        createBalls();

        //we need some players too
        createPlayer();

        //show Players
        showPlayers();

        //show next balls
        showNextBalls();

    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        GameManager.calculatePoints();

        showPlayers();
        showNextBalls();
        checkIfFinished();

    }


    private void checkIfFinished(){
        if(GameManager.getSolidBalls().size() == 0 && GameManager.getStripedBalls().size()==0) {

            new AlertDialog.Builder(this)
                    .setTitle("Satz vorbei")
                    .setMessage("Alle Kugeln wurden wieder hinzugefügt")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            //calculation and saving
            GameManager.calculatePoints();
            GameManager.savePoints();
            //reseting
            GameManager.resetEvents();
            createBalls();
            onResume();

        }
    }

    private void createMenu(){
        // entries in burger menue
        mNavItems.add(new NavItem("Player", "Manage Players"));
        mNavItems.add(new NavItem("Events", "Delete and change Events"));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

    }


    private void selectItemFromDrawer(int position) {
        //Fragment fragment = new PreferencesFragment();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, new PreferencesFragment()).commit();

       // FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction()
       //         .replace(R.id.mainContent, fragment)
        //        .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);


        //for serving game manager


        if(position == 1){
            Intent myIntent = new Intent(this, LoggingSetup.class);
            startActivity(myIntent);
        }
        else{
            Intent myIntent = new Intent(this, PlayerSetup.class);
            startActivity(myIntent);
        }
    }


    /**
     * This methods adds Balls to the game manager
     */
    private void createBalls(){
        Ball ball;
        for(int i=1; i<16; i++){
            if( i>8 ){
                ball = new Ball(i, true);
                GameManager.addBallToStriped(ball);
            }
            else{
                ball = new Ball(i, false);
                GameManager.addBallToSolid(ball);
            }
        }
        return;
    }


    /**
     * This methods adds Player to the game
     */
    private void createPlayer(){
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        if( GameManager.returnPlayerList().contains(p1)){
        }
        else{
            GameManager.addPlayer(p1);
        }
        if( GameManager.returnPlayerList().contains(p2)){
        }
        else{
            GameManager.addPlayer(p2);
        }

        return;
    }


    private void showNextBalls(){

        TextView striped = (TextView) findViewById(R.id.nextStriped);
        striped.setText(""+GameManager.getSmallestStriped());

        TextView solid = (TextView) findViewById(R.id.nextSolid);
        solid.setText(""+GameManager.getSmallestSolid());

    }


    /**
     * This methods provides the displaying of active players
     */
    private void showPlayers(){
        listPlayers = (TableLayout)findViewById(R.id.listPlayers);

        listPlayers.removeAllViews();

        final ArrayList<Player> pL = GameManager.returnPlayerList();
        int height = 150;
        for(int i=0; i<pL.size(); i++){
            View tableRow = LayoutInflater.from(this).inflate(R.layout.player_item,null,false);
            TextView playerName  = (TextView) tableRow.findViewById(R.id.PlayerName);
            TextView playerPoints  = (TextView) tableRow.findViewById(R.id.PlayerPoints);

            final int j = i;
            Button button = (Button) tableRow.findViewById(R.id.PlayerAction);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getBaseContext(), ListBalls.class);
                    myIntent.putExtra("player", j);
                    startActivity(myIntent);
                }
            });


            playerName.setText(""+pL.get(i).getName());
            playerName.setTextSize(25);
            playerName.setHeight(height);
            playerPoints.setText(""+String.format("%.4f",pL.get(i).getPoints()));
            //playerPoints.setText(""+pL.get(i).getPoints());
            playerPoints.setHeight(height);
            playerName.setTextSize(20);
            listPlayers.addView(tableRow);
        }
    }


    //for setting buttons
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle) {
            mTitle = title;
            mSubtitle = subtitle;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
      //      ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
       //     iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }
}

