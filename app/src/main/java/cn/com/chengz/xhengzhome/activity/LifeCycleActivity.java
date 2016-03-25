package cn.com.chengz.xhengzhome.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.com.chengz.xhengzhome.R;

public class LifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("LifeCycleActivity", "--->onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("LifeCycleActivity", "--->onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        Log.e("LifeCycleActivity", "--->onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("LifeCycleActivity", "--->onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("LifeCycleActivity", "--->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("LifeCycleActivity", "--->onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e("LifeCycleActivity", "--->onStart");
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("LifeCycleActivity", "--->onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
