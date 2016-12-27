<?php
session_start();
require('DataBase.php');
require('bibli.php');
echo_html_head('Inscription','./css/style.css');
?>

  <p>Bienvenue</p>
  <form method="post" action="Inscription.php">
    Prénom : <br>
    <input type="text" name="prenom"/>
    Nom : <br>
    <input type="text" name="nom"/>
    E-mail : <br>
    <input type="mail" name="mail"/>
    Mot de passe : <br>
    <input type="password" name="pass"/>
    Répéter Mot de passe : <br>
    <input type="password" name="pass_repeat"/>
    <input type="submit" value="Inscription">
    <input type="hidden" name="insc" value="insc"/>
  </form>

	<?php
	if(isset($_POST['insc'])){
	
	
	  $erreurNom="";
	  $erreurPrenom="";
	  $erreurMail="";
	  $erreurPwd="";
	  $erreurPwdR="";      
    if(empty($_POST["mail"])){
         $erreurMail =  "Le mail est vide <br>";    
    }elseif (!filter_var($_POST["mail"], FILTER_VALIDATE_EMAIL)) {
           $erreurmail =  "Le mail à un format invalide <br>";
    }
    if (empty($_POST["nom"])) {
         $erreurPrenom = "Le nom est vide <br>";  	   
	   }
    
	   if (empty($_POST["prenom"])) {
         $erreurPrenom = "Le prenom est vide <br>";  	   
	   }
	   if (empty($_POST["pass"])) {
         $erreurPwd = "Le mot de passe est vide <br>";  	   
	   }	
	   if (empty($_POST["pass_repeat"])) {
         $erreurPwdR = "Répéter le mot de passe est vide <br>";  	   
	   }
	
	if($erreurPwd=="" && $erreurPwdR==""){
	      if($_POST["pass"]!=$_POST["pass_repeat"]){
	          $erreurPwd="Les mots de passes ne sont pas identiques <br>";
	      }	
	}
	  
	
	if( $erreurNom=="" ||$erreurPrenom=="" ||$erreurMail=="" ||$erreurPwd=="" ||$erreurPwdR==""){
	  
		      $DB = new DataBase();	
		      $resultat = $DB->requete_SQL("SELECT COUNT(login) as user FROM USER WHERE login=".$POST['mail']);
		      $res = $resultat->fetch_row;
		      if($res['user'] < 1){
				        $resultat2 = $DB->requete_SQL("INSERT INTO `User`(`login`, `pwd`, `lastname`, `firstname`, `adherant`, `admin`) VALUES (".$POST['mail'].",".$POST['pass'].",".$POST['nom'].",".$POST['prenom']."");
			      echo("Demande d'inscription effectué");
			      echo("<script type='text/javascript'>document.location.replace('Connexion.php');</script>");
		      }else{
			      echo("Erreur l'adresse mail est déjà utilisée");
		      }
		}else{
		  echo($erreurNom);
		  echo($erreurPrenom);
		  echo($erreurPwd);
		  echo($erreurPwdR);
		  echo($erreurmail);
		
		
		}
	}

echo_html_end();
?>
