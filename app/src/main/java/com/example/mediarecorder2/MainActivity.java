package com.example.mediarecorder2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivo;
    private Button btngrabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btngrabar=findViewById(R.id.btngrabar);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }
    public void Recorder (View view){
        if(grabacion==null){
            archivo= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Grabacion.mp3";
            grabacion=new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivo);
            try {
                grabacion.prepare();
                grabacion.start();
            }catch (IOException e){

            }
            btngrabar.setBackgroundResource(R.color.red);
            Toast.makeText(getApplicationContext(),"Grabando...",Toast.LENGTH_SHORT).show();
        }else if(grabacion !=null){
            grabacion.stop();
            grabacion.release();
            grabacion=null;
            btngrabar.setBackgroundResource(R.color.azulChido);
            Toast.makeText(getApplicationContext(),"Habemus terminate de grabate.",Toast.LENGTH_SHORT).show();
        }
    }
    public void Reproducir(View view){
        MediaPlayer mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivo);
            mediaPlayer.prepare();
        }catch (IOException e){
        }
        mediaPlayer.start();
        Toast.makeText(getApplicationContext(),"Reproduciendo audio",Toast.LENGTH_SHORT).show();
    }
}