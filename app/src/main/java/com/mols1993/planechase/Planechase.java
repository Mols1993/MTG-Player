package com.mols1993.planechase;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.instants.mols1993.planechase.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Planechase extends AppCompatActivity {

    String folder, orientation;
    int counter = -1;
    List<String> list = new ArrayList<>();

    private void setImage(String path, ImageView imv) throws IOException {

        Bitmap img = BitmapFactory.decodeStream(getAssets().open(path));
        imv.setImageBitmap(img);
        Log.i("Current Plane", path);
    }

    private void next(){
        ImageView imv = (ImageView) findViewById(R.id.imageView);
        counter++;
        if(counter == list.size()){
            counter = 0;
        }
        try {
            Log.i("Counter", Integer.toString(counter));
            setImage(folder + "/" + list.get(counter), imv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void previous(){
        ImageView imv = (ImageView) findViewById(R.id.imageView);
        counter--;
        if(counter < 0){
            counter = list.size() - 1;
        }
        try {
            Log.i("Counter", Integer.toString(counter));
            setImage(folder + "/" + list.get(counter), imv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Manage Configuration (only orientation actually) change
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if(orientation.equals("portrait")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else if(orientation.equals("landscape")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planechase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Get data from Intent
        Bundle extras = getIntent().getExtras();
        folder = extras.getString("folder");
        orientation = extras.getString("orientation");

        if(orientation.equals("portrait")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else if(orientation.equals("landscape")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //Get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        int height = size.y;
        Log.i("Size", Integer.toString(height) + " " + Integer.toString(width));

        //Add touch event to ImageView
        ImageView imv = (ImageView) findViewById(R.id.imageView);
        imv.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Log.i("Coordinates", "(" + x + ", " + y + ")");
                    if(x > width/2){
                        next();
                    }
                    else{
                        previous();
                    }
                }
                return true;
            }
        });

        //List planes in a list
        try {
            Collections.addAll(list, getAssets().list(folder));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Randomly shuffle the planes list
        Collections.shuffle(list);

        //Initialize a plane
        next();
    }

}
