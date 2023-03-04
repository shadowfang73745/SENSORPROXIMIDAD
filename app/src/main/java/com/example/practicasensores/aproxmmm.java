package com.example.practicasensores;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class aproxmmm extends AppCompatActivity {

    Sensor sensor;
    SensorManager admistrasensores;
    SensorEventListener escuchuadordeEventos;

    TextView etiresult;
    Button atras;
    ConstraintLayout fondopantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximidad);
        fondopantalla = findViewById(R.id.fondopantalla);
        etiresult = findViewById(R.id.Result);
        admistrasensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = admistrasensores.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensor == null) {
            Toast.makeText(aproxmmm.this, "No se detecto el sensor ", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(aproxmmm.this, "El sensor se a detectado", Toast.LENGTH_SHORT).show();
        }
        escuchuadordeEventos = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    etiresult.setText("Valor de el sensor: " + sensorEvent.values[0]+ "\n Se ha acercado al senser");
                    fondopantalla.setBackgroundColor(Color.RED);
                }else {
                    etiresult.setText("Valor de el sensor: " + sensorEvent.values[0]+ "\n Se ha alejado del senser");
                    fondopantalla.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        inicializarSensor();
        atras= findViewById(R.id.Regresa);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void inicializarSensor(){
        admistrasensores.registerListener(escuchuadordeEventos,sensor,(2000*1000));

    }

    public void  detenerSensor(){
        admistrasensores.unregisterListener(escuchuadordeEventos,sensor);
    }

    @Override
    protected void onResume() {
        inicializarSensor();
        super.onResume();
    }

    @Override
    protected void onPause() {
        detenerSensor();
        super.onPause();
    }
}