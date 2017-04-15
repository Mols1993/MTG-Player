package com.mols1993.planechase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mols1993.planechase.R;

public class MainActivity extends AppCompatActivity {

    public void Planechase(View view){
        Intent intent = new Intent(this, Planechase.class);
        intent.putExtra("folder", "planes");
        intent.putExtra("orientation", "landscape");
        startActivity(intent);
    }

    public void Archenemy(View view){
        Intent intent = new Intent(this, Planechase.class);
        intent.putExtra("folder", "schemes");
        intent.putExtra("orientation", "portrait");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
