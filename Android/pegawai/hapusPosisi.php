<?php 
 
 /*
 
 penulis: Muhammad yusuf
 website: https://www.kodingindonesia.com/
 
 */
 
 //Mendapatkan Nilai ID
 $id = $_GET['id'];
 
 //Import File Koneksi Database
 require_once('koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM tb_posisi WHERE id_posisi=$id;";
 
 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Posisi';
 }else{
 echo 'Gagal Menghapus Posisi';
 }
 
 mysqli_close($con);
 ?>