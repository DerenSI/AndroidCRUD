package com.example.mycrud23sept2020;

public class konfigurasi {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD="http://192.168.18.17/Android/pegawai/tambahPgw.php";
    public static final String URL_GET_ALL = "http://192.168.18.17/Android/pegawai/tampilSemuaPgw.php";
    public static final String URL_GET_EMP = "http://192.168.18.17/Android/pegawai/tampilPgw.php?id=";
    public static final String URL_UPDATE_EMP = "http://192.168.18.17/Android/pegawai/updatePgw.php";
    public static final String URL_DELETE_EMP = "http://192.168.18.17/Android/pegawai/hapusPgw.php?id=";
    public static final String URL_GET_EMP_BY_KEYWORD = "http://192.168.18.17/Android/pegawai/tampilSebagianPgw.php?key=";

    public static final String URL_POSISI_ADD = "http://192.168.18.17/Android/pegawai/tambahPosisi.php";
    public static final String URL_POSISI_GET_ALL = "http://192.168.18.17/Android/pegawai/tampilSemuaPosisi.php";
    public static final String URL_POSISI_GET = "http://192.168.18.17/Android/pegawai/tampilPosisi.php?id=";
    public static final String URL_POSISI_UPDATE = "http://192.168.18.17/Android/pegawai/updatePosisi.php";
    public static final String URL_POSISI_DELETE = "http://192.168.18.17/Android/pegawai/hapusPosisi.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "desg"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_GAJIH = "salary"; //salary itu variabel untuk gajih
    public static final String KEY_EMP_ID_POSISI = "id_posisi";

    public static final String KEY_POSISI_ID = "id";
    public static final String KEY_POSISI_POSISI = "posisi";
    public static final String KEY_POSISI_GAJIH = "gajih";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJIH = "salary";
    public static final String TAG_ID_POSISI = "id_posisi";

    public static final String TAG_POSISI_ID = "id";
    public static final String TAG_POSISI_POSISI = "posisi";
    public static final String TAG_POSISI_GAJIH = "gajih";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
    public static final String POS_ID = "pos_id";
}
