<?php
 
 /*
 
 penulis: Muhammad yusuf
 website: https://www.kodingindonesia.com/
 
 */
 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$desg = $_POST['posisi'];
		$sal = $_POST['gajih'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tb_posisi (posisi,gajih) VALUES ('$desg','$sal')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Posisi';
		}else{
			echo 'Gagal Menambahkan Posisi';
		}
		
		mysqli_close($con);
	}
?>