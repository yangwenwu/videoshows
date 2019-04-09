package com.lemon.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OneActivity extends Activity implements View.OnClickListener {

    private Button mGoFlutterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_one);

        mGoFlutterBtn = findViewById(R.id.go_flutter);

//        FlutterView flutterView = new FlutterView(this,null);
//        flutterView.setInitialRoute("route1");

        mGoFlutterBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_flutter:
                Intent intent = new Intent(this, FlutterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
