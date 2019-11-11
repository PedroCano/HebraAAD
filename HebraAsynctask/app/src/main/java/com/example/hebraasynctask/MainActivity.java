package com.example.hebraasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btLanzar;
    private Hebra hebra;
    private HebraThread hebraThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hebra = new Hebra();

        initComponents();
    }

    private void initComponents() {
        btLanzar = findViewById(R.id.btLanzar);

        btLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(hebra.getStatus()== AsyncTask.Status.RUNNING){
                    hebra.cancel(true);
                }else{
                    hebra = new Hebra();
                    hebra.execute();
                }*/

                lanzarHebra();
            }
        });
    }

    class Hebra extends AsyncTask<Void, Integer, Boolean> {

        private TextView tvCuenta = findViewById(R.id.tvCuenta);

        @Override
        protected void onPreExecute() {
            //Código que se va a ejecutar justo antes de que se lance la hebra

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 20; i >= 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }

            }
            //publishProgress(25); //Tipo del segundo valor del AsyncTask
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tvCuenta.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
    }

    class HebraThread extends Thread{

        private volatile boolean activo = true;
        private TextView tvCuenta = findViewById(R.id.tvCuenta);

        public boolean isActivo() {
            return activo;
        }

        public HebraThread setActivo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public HebraThread detener(){
            return setActivo(false);
        }

        @Override
        public void run() {
            for (int i = 20; i >=0; i--) {
                progreso(i);
                if(!activo){
                    break;
                }
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    //Log.v(TAG, getString(R.string.mensajecatch));
                    //interrupt();
                    //break;
                    //return;
                }
                if(isInterrupted()){
                    //interrupt();
                    break;
                }

            }
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }

        private void progreso(final int i) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvCuenta.setText(String.valueOf(i));
                }
            });
        }
    }



    public void lanzarHebra(){
        HebraThread hebra = new HebraThread();
        hebra.start();
    }


}


