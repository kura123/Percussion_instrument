package com.example.kurata.tanbarinn;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

public class TanbarinActivity extends AppCompatActivity implements SensorEventListener {
    private MediaPlayer tambourine_pan; //たたいた時の音
    private MediaPlayer tambourine_syan; //ふった時の音
    private MediaPlayer castanets_sound;//カスタネットの音
    private MediaPlayer maraca_sound;//マラカスの音
    private SensorManager sensorManager;
    private boolean registerAccelerometer;//加速度センサーがリスナーとして登録済みかどうか
    private float[] accels = new float[3];//加速度センサーが返す値
    int decision;//楽器判定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanbarin);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imButton);
        imageButton.setOnClickListener(sound);

        Intent intent = getIntent();
        decision = intent.getIntExtra("decision", 0);
        if (decision == 1) {
            linearLayout.setBackgroundResource(R.drawable.tanbarin);
        } else if (decision == 2) {
            linearLayout.setBackgroundResource(R.drawable.kasutanetto);
        } else {
            linearLayout.setBackgroundResource(R.drawable.marakasu);
        }

        //音をいれる
        tambourine_pan = MediaPlayer.create(this, R.raw.tambourine_pan);
        tambourine_syan = MediaPlayer.create(this, R.raw.tanbourine_syan);
        castanets_sound = MediaPlayer.create(this, R.raw.castanets);
        maraca_sound = MediaPlayer.create(this, R.raw.maraca);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        registerAccelerometer = false;
        for (int i = 0; i < 3; i++) {
            accels[i] = 0.0f;
        }

    }

    View.OnClickListener sound = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (decision == 1) {
                tambourine_pan.start();
                tambourine_pan.seekTo(0);
            }else if (decision==2){
                castanets_sound.start();
                castanets_sound.seekTo(0);
            }
        }
    };

    @Override
    protected void onPause() {
        if (registerAccelerometer){
            sensorManager.unregisterListener(this);
            registerAccelerometer = false;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (!registerAccelerometer) {
            List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if (sensors.size() > 0) {
                registerAccelerometer = sensorManager.registerListener(
                        this, sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        super.onResume();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            for (int i = 0; i < 3; i++) {
                accels[i] = sensorEvent.values[i];
            }
        }
        if (decision == 1) {
            if (accels[0] > 10) {
                tambourine_syan.start();
                tambourine_syan.seekTo(0);
            } else if (accels[0] < -10) {
                tambourine_syan.start();
                tambourine_syan.seekTo(0);
            }
        }else if (decision==3){
            if (accels[0] > 10) {
                maraca_sound.start();
                maraca_sound.seekTo(0);
            } else if (accels[0] < -10) {
                maraca_sound.start();
                maraca_sound.seekTo(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tambourine_syan.stop();
        tambourine_syan.release();
        tambourine_pan.stop();
        tambourine_pan.release();
        castanets_sound.stop();
        castanets_sound.release();
        maraca_sound.stop();
        maraca_sound.release();
    }

}
