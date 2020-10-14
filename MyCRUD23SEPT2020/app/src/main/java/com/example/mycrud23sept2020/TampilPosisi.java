package com.example.mycrud23sept2020;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;

/**
 * Created by muhammadyusuf on 01/19/2017.
 * kodingindonesia
 */

public class TampilPosisi extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextSalary;

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonKembali;
    private Button buttonTambah;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_posisi);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.POS_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonKembali = (Button) findViewById(R.id.buttonKembali);
        buttonTambah = (Button) findViewById(R.id.buttonTambah);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonKembali.setOnClickListener(this);
        buttonTambah.setOnClickListener(this);

        editTextId.setText(id);

        getPosisi();
    }

    private void getPosisi(){
        class GetPosisi extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPosisi.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showPosisi(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_POSISI_GET,id);
                return s;
            }
        }
        GetPosisi gp = new GetPosisi();
        gp.execute();
    }

    private void showPosisi(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(konfigurasi.TAG_POSISI_POSISI);
            String sal = c.getString(konfigurasi.TAG_POSISI_GAJIH);

            editTextName.setText(name);
            editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updatePosisi(){
        final String name = editTextName.getText().toString().trim();
        final String salary = editTextSalary.getText().toString().trim();

        class UpdatePosisi extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPosisi.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilPosisi.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_POSISI_ID,id);
                hashMap.put(konfigurasi.KEY_POSISI_POSISI,name);
                hashMap.put(konfigurasi.KEY_POSISI_GAJIH,salary);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_POSISI_UPDATE,hashMap);

                return s;
            }
        }

        UpdatePosisi up = new UpdatePosisi();
        up.execute();
    }

    private void deletePosisi(){
        class DeletePosisi extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPosisi.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilPosisi.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_POSISI_DELETE, id);
                return s;
            }
        }

        DeletePosisi dp = new DeletePosisi();
        dp.execute();
    }

    private void confirmDeletePosisi(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Posisi ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deletePosisi();
                        startActivity(new Intent(TampilPosisi.this,TampilSemuaPosisi.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updatePosisi();
        }

        if(v == buttonDelete){
            confirmDeletePosisi();
        }

        if(v == buttonKembali){
            startActivity(new Intent(TampilPosisi.this,TampilSemuaPosisi.class));
        }

        if(v == buttonTambah){
            startActivity(new Intent(TampilPosisi.this,PosisiActivity.class));
        }
    }

}
