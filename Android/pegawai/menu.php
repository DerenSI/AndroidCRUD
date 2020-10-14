<?php 
	/* ===== www.dedykuncoro.com ===== */
	include_once "koneksi.php";

	$query = mysqli_query($con, "SELECT * FROM tb_posisi ORDER BY id_posisi ASC");
	$json = array();	
	while($row = mysqli_fetch_assoc($query)){
	 	$json[] = $row;
	}
	
	echo json_encode(array('result'=>$json));
	
	mysqli_close($con);
?>