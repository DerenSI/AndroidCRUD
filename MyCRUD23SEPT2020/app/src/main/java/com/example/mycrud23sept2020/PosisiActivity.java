package com.example.mycrud23sept2020;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

//import android.support.v7.app.AppCompatActivity;

/**
 * Created by muhammadyusuf on 01/19/2017.
 * kodingindonesia
 */



public class PosisiActivity extends AppCompatActivity implements View.OnClickListener {

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextPosisiPosisi;
    private EditText editTextPosisiGajih;

    private Button buttonPosisiAdd;
    private Button buttonPosisiView;
    private Button buttonPegawaiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posisi);

        //Inisialisasi dari View
        editTextPosisiPosisi = (EditText) findViewById((R.id.editTextPosisiPosisi));
        editTextPosisiGajih = (EditText) findViewById((R.id.editTextPosisiGajih));

        buttonPosisiAdd = (Button) findViewById(R.id.buttonPosisiAdd);
        buttonPosisiView = (Button) findViewById(R.id.buttonPosisiView);
        buttonPegawaiView = (Button) findViewById(R.id.buttonPegawaiView);

        //Setting listeners to button
        buttonPosisiAdd.setOnClickListener(this);
        buttonPosisiView.setOnClickListener(this);
        buttonPegawaiView.setOnClickListener(this);
    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addPosisi(){

        final String posisiposisi = editTextPosisiPosisi.getText().toString().trim();
        final String posisigajih = editTextPosisiGajih.getText().toString().trim();

        class AddPosisi extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PosisiActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(PosisiActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_POSISI_POSISI,posisiposisi);
                params.put(konfigurasi.KEY_POSISI_GAJIH,posisigajih);
                System.out.print(params);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_POSISI_ADD, params);
                return res;
            }
        }

        AddPosisi ap = new AddPosisi();
        ap.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonPosisiAdd){
            addPosisi();
        }

        if(v == buttonPosisiView){
            startActivity(new Intent(this,TampilSemuaPosisi.class));
        }

        if(v == buttonPegawaiView){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}

