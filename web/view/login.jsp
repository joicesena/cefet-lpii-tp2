<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Login</title>
        
        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="../css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="../css/login.css"/>
    </head>
    
    <body>
        <header>
            <nav class="white">
                <div class="nav-wrapper container">
                    <a href="../index.html" class="brand-logo">Logo</a>
                    <a href="#" data-activates="menu-mobile" class="button-collapse"><i class="material-icons">menu</i></a>

                    <ul class="right hide-on-med-and-down">
                        <li><a href="../index.html">Home</a></li>
                    </ul>

                    <ul id="menu-mobile" class="side-nav">
                        <li id="menu-mobile-header">
                        <div class="user-view">
                            <div class="background">
                                <img src="../imgs/sidenav-background.jpg"/>
                            </div>
                            <a href="#">HOSTEN</a>
                         </div>
                        <li>
                        <li><a class="waves-effect" href="../index.html"><i class="material-icons grey-text text-darken-3">home</i>Home</a></li>
                        <li><a class="waves-effect" href="login.jsp"><i class="material-icons grey-text text-darken-3">exit_to_app</i>Login</a></li>
                    </ul>
                </div>
            </nav>
        </header>

        <main>
            <div class="valign-wrapper row login-box">
                <div class="col card hoverable s10 pull-s1 m6 pull-m3 l4 pull-l4">
                    <!--<form action="quartos-estados.jsp">-->
                    <form action="http://localhost:8080/cefet-lpii-tp2/login" method="post">
                        <div class="card-content">
                            <div class="center-align">
                                <h3>Hosten</h3>
                            </div>
                            <span class="card-title">Login</span>
                            <div class="row">
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">mail_outline</i>
                                    <label for="email">Email</label>
                                    <input name="email" id="email" type="email" class="validate" required>
                                </div>
                                <div class="input-field col s12">
                                    <i class="material-icons prefix">lock_outline</i>
                                    <label for="password">Senha</label>
                                    <input name="password" id="password" type="password" class="validate" required>
                                </div>
                                <div id="remember-password" class="col s6">
                                    <p>
                                        <input type="checkbox" id="remember-password-ckb" />
                                        <label for="remember-password-ckb">Lembrar senha</label>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="card-action center-align">
                            <button id="login-button" class="btn-large waves-effect waves-light" type="submit">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>

        <footer>
          <div class="footer-copyright">
            <div class="container">
              Feito com  <i class="tiny material-icons">favorite_border</i> por estudantes do CEFET-MG
            </div>
          </div>
        </footer>

        <!--  Script -->
        <!-- Import jQuery before Materialize JS  -->
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="../js/materialize/materialize.js"></script>
        <script type="text/javascript" src="../js/login.js"></script>
    </body>
</html>
