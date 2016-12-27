<?php
	session_start();
	require('DataBase.php');
	require('bibli.php');

	if(isset($_SESSION['admin'])){
	if($_SESSION['admin']==1){

	}

	$DB = new DataBase();	
	$resultat = $DB->requete_SQL("SELECT lastname, firstname, dateEvent FROM User,Participant WHERE User.login=Participant.login");
	$res = $resultat->fetch_assoc();
	echo($res['user']);

	}
	echo_html_head("organisation","./css/organisation.css");
	foreach($res2 as $res['firstName']){
	echo '<div class="checkbox">';
	echo '<label><input type="checkbox" value="">Mercredi '.$res2." ".$res2." ".$res2.'</label>' ;

	echo '</div>';
	}
	?>


	<script src="bootstrap/js/bootstrap.js"></script>
	</body>
</html>
