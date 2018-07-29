<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Cadastro de Funcionários</title>

        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="../css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="../css/funcionarios-cadastro.css"/>
    </head>
    
    <body>
        <header>
            <%@include file="menu.jsp"%>
        </header>

        <main>            
            <h4 class="title">Cadastro de Funcionários</h4>
            <form>
                <div id="container">
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">person</i>
                                <label for="nome-funcionario">Nome</label>
                                <input id="nome-funcionario" type="text" class="validate" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">email</i>
                                <label for="email-funcionario">Email</label>
                                <input id="email-funcionario" type="email" class="validate" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">lock</i>
                                <label for="senha-funcionario">Senha</label>
                                <input id="senha-funcionario" type="password" class="validate" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">business_center</i>
                                <select>
                                    <option value="1">Camareiro</option>
                                    <option value="2">Garçom</option>
                                    <option value="3">Massagista</option>
                                    <option value="4">Recepcionista</option>
                                </select>
                                <label>Cargo</label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card-action right-align button-box">
                    <button id="add-button" class="btn waves-effect waves-light" type="submit"><i class="material-icons left">add_circle_outline</i>Salvar Funcionário</button>
                </div>
            </form>
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
        <script type="text/javascript" src="../js/funcionarios-cadastro.js"></script>
    </body>
</html>
