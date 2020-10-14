package com.example.mycrud23sept2020;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import android.support.v7.app.AppCompatActivity;

/**
 * Created by muhammadyusuf on 01/19/2017.
 * kodingindonesia
 */

public class TampilSemuaPosisi extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listViewPosisi;

    private String JSON_STRING;
//    private FloatingActionButton fab1_Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_posisi);
        listViewPosisi = (ListView) findViewById(R.id.listViewPosisi);
//        fab1_Add = (FloatingActionButton) findViewById(R.id.fab1_Add);
        listViewPosisi.setOnItemClickListener(this);
//        fab1_Add.setOnClickListener((View.OnClickListener) this);
        getJSON();
    }


    private void showPosisi(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_POSISI_ID);
                String name = jo.getString(konfigurasi.TAG_POSISI_POSISI);

                HashMap<String,String> posisis = new HashMap<>();
                posisis.put(konfigurasi.TAG_POSISI_ID,id);
                posisis.put(konfigurasi.TAG_POSISI_POSISI,name);
                list.add(posisis);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaPosisi.this, list, R.layout.list_item_posisi,
                new String[]{konfigurasi.TAG_POSISI_ID,konfigurasi.TAG_POSISI_POSISI},
                new int[]{R.id.id, R.id.name});

        listViewPosisi.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaPosisi.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showPosisi();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_POSISI_GET_ALL);
                System.out.println("DO IN BACKGROUND = " + s);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilPosisi.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String posId = map.get(konfigurasi.TAG_POSISI_ID).toString();
        intent.putExtra(konfigurasi.POS_ID,posId);
        startActivity(intent);
    }

//    @Override
//    public void onClick(View v) {
//        if(v == fab1_Add){
//            startActivity(new Intent(this,MainActivity.class));
//        }
//    }

}
