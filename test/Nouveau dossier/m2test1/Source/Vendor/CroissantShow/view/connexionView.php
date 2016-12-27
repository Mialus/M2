/**
 * Created by PhpStorm.
 * User: Pierre
 * Date: 13/12/2016
 * Time: 02:30
 */

<!DOCTYPE html>
<html>
<head>
    <title>Connexion CroissantShow</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../CroissantShow/lib/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="../CroissantShow/lib/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../CroissantShow/css/connexion.css"/>
</head>
<body>


<div class="container">
    <div class="card card-container">
        <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
        <p id="profile-name" class="profile-name-card"></p>
<form class="form-signin" method="POST">
    <input type="text" name="login" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Mot de passe" required>
    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Se connecter</button>
</form><!-- /form -->
<a href="#" class="forgot-password">
    Mot de passe oublié ?
</a>
</div>
</div>


<script src="../CroissantShow/lib/bootstrap/js/bootstrap.js"></script>
<script src="../CroissantShow/lib/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>