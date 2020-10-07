<?php
/*
 
 penulis: Muhammad yusuf
 website: https://www.kodingindonesia.com/
 
 */
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id = $_POST['id'];
		$desg = $_POST['posisi'];
		$sal = $_POST['gajih'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE tb_posisi SET posisi = '$desg', gajih = '$sal' WHERE id_posisi = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Posisi';
		}else{
			echo 'Gagal Update Data Posisi';
		}
		
		mysqli_close($con);
	}
?>