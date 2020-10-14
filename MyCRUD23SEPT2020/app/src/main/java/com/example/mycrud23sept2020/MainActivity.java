package com.example.mycrud23sept2020;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by muhammadyusuf on 01/19/2017.
 * kodingindonesia
 */



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSal;
    private EditText editTextName2;
    private EditText editTextIdDesgHidden;

    private Button buttonAdd;
    private Button buttonView;
    private Button buttonFind;
    private Button buttonPosisiView;

    private Spinner spinnerDesg;

    //Ganti "KETIK_IP_DISINI" dengan IP yg di dijadikan laptop. ex: 192.168.1.10
    private static String URL="http://192.168.18.17/Android/pegawai/menu.php";
    ProgressDialog  pDialog;
    JSONArray JsonArrayDesg = null;
    List<String> valueIdDesg = new ArrayList<String>();
    List<String> valueNamaDesg = new ArrayList<String>();
    List<String> valueGajihDesg = new ArrayList<String>();

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi dari View
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextSal = (EditText) findViewById(R.id.editTextSalary);
        editTextName2 = (EditText) findViewById(R.id.editTextName2);
        editTextIdDesgHidden = (EditText) findViewById(R.id.editTextIdDesgHidden);

        spinnerDesg = (Spinner) findViewById((R.id.spinnerDesg));

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonFind = (Button) findViewById(R.id.buttonFind);
        buttonPosisiView = (Button) findViewById(R.id.buttonPosisiView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonFind.setOnClickListener(this);
        buttonPosisiView.setOnClickListener(this);


    }

    public void LoadData(View v){
        new getDataDesg().execute();
    }

    private class getDataDesg extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Mohon Tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            //Membuat Service "ServiceHandler"
            ServiceHandler sh = new ServiceHandler();

            // Memanggil URL untuk mendapatkan respon data
            String jsonStr = sh.makeServiceCall(URL, ServiceHandler.GET); //siswaManager.php?mode=getAllDataSiswa

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Mendapatkan data Array JSON
                    JsonArrayDesg = jsonObj.getJSONArray("result");

                    ArrayList<ModelDesg> listDataDesg = new ArrayList<ModelDesg>();
                    listDataDesg.clear();

                    //Melakukan perulangan untuk memecah data
                    for (int i = 0; i < JsonArrayDesg.length(); i++) {
                        JSONObject obj = JsonArrayDesg.getJSONObject(i);

                        ModelDesg modelDesg = new ModelDesg();
                        modelDesg.setIdDesg(obj.getString("id_posisi"));
                        modelDesg.setDesg(obj.getString("posisi"));
                        modelDesg.setGajihDesg(obj.getString("gajih"));
                        listDataDesg.add(modelDesg);

                        System.out.println("data "+modelDesg.getIdDesg());
                    }

                    valueIdDesg = new ArrayList<String>();
                    valueNamaDesg = new ArrayList<String>();
                    valueGajihDesg = new ArrayList<String>();

                    for (int i = 0; i < listDataDesg.size(); i++) {
                        valueIdDesg.add(listDataDesg.get(i).getIdDesg());
                        valueNamaDesg.add(listDataDesg.get(i).getDesg());
                        valueGajihDesg.add(listDataDesg.get(i).getGajihDesg());

                        System.out.println("data 2 "+listDataDesg.get(i).getDesg());

                    }

                    System.out.println("id_posisi + "+valueIdDesg.get(0));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            // Membuat adapter untuk spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_item, valueNamaDesg);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Mengaitkan adapter spinner dengan spinner yang ada di layout
            spinnerDesg.setAdapter(spinnerAdapter);
            spinnerDesg.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String idDesg = valueIdDesg.get(position);
                    String namaDesg = valueNamaDesg.get(position);
                    String gajihDesg = valueGajihDesg.get(position);
                    Toast.makeText(MainActivity.this, "Anda Memilih ID POSISI: "+idDesg+", Nama: "+namaDesg, Toast.LENGTH_LONG).show();
                    editTextSal.setText(gajihDesg);
                    editTextDesg.setText(namaDesg);
                    editTextIdDesgHidden.setText(idDesg);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        }
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(){

        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String sal = editTextSal.getText().toString().trim();
        final String id_posisi = editTextIdDesgHidden.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_POSISI,desg);
                params.put(konfigurasi.KEY_EMP_GAJIH,sal);
                params.put(konfigurasi.KEY_EMP_ID_POSISI,id_posisi);
                System.out.print(params);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addEmployee();
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaPgw.class));
        }

        if(v == buttonFind){
//            startActivity(new Intent(this,TampilSebagianPgw.class));
            Intent intent = new Intent(this, TampilSebagianPgw.class);
            Bundle b = new Bundle();
            final String name2 = editTextName2.getText().toString().trim();
            b.putString("key", name2);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }

        if(v == buttonPosisiView){
            startActivity(new Intent(this,PosisiActivity.class));
        }
    }
}

