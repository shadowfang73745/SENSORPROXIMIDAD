package com.example.practicasensores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    Button btnaproxi, btnlumino;
    Sensor sensor;
    SensorManager admistrasensores;
    SensorEventListener escuchuadordeEventos;

    TextView etiresult;
    Button atras;
    ConstraintLayout fondopantalla;
    ConstraintLayout fondopantallaS2;
    Button Vuelve;
    Sensor sensorS;
    SensorManager admistrarsensor;
    SensorEventListener Eventoss;
    TextView etiquetaresultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximidad);
        fondopantalla = findViewById(R.id.fondopantalla);
        etiresult = findViewById(R.id.Result);
        admistrasensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = admistrasensores.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        etiquetaresultados = findViewById(R.id.Result);
        admistrarsensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorS = admistrarsensor.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensor == null) {
            Toast.makeText(MainActivity.this, "No se detecto el sensor ", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(MainActivity.this, "El sensor se a detectado", Toast.LENGTH_SHORT).show();
        }
        if (sensorS == null) {
            Toast.makeText(MainActivity.this, "No se detecto el sensor ", Toast.LENGTH_SHORT).show();
            //  finish();
        } else {
            Toast.makeText(MainActivity.this, "El sensor se a detectado", Toast.LENGTH_SHORT).show();
        }

        escuchuadordeEventos = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    etiresult.setText("Valor de el sensor: " + sensorEvent.values[0]+ "\n Se ha acercado al senser");
                    fondopantalla.setBackgroundColor(Color.RED);
                    etiquetaresultados.setText("Valor de el sensor: " + sensorEvent.values[0] );
                }else {
                    etiresult.setText("Valor de el sensor: " + sensorEvent.values[0]+ "\n Se ha alejado del senser");
                    fondopantalla.setBackgroundColor(Color.GREEN);
                    etiquetaresultados.setText("Valor de el sensor: " + sensorEvent.values[0] );
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
        admistrarsensor.registerListener(Eventoss,sensorS,SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void  detenerSensor(){
        admistrasensores.unregisterListener(escuchuadordeEventos,sensor);
        admistrarsensor.unregisterListener(Eventoss,sensorS);
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