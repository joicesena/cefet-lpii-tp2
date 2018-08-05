<jsp:include page="/WEB-INF/controleAcesso.jsp" flush="false">
    <jsp:param name="nomePagina" value="Tela de Check-in"/>
</jsp:include>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Check-in</title>

        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/checkin.css"/>
    </head>
    
    <body>
        <header>
            <%@include file="menu.jsp"%>
        </header>

        <main>            
            <h4 class="title">Check-in</h4>
            
            <div id="container" class="row">
                <div class="col s9 search-box">
                    <div class="input-field">
                        <i class="material-icons prefix">search</i>
                        <label for="search">Buscar hóspede por... (selecione um campo ao lado)</label>
                        <input id="search" type="search">
                    </div>
                </div>
                
                <div class="col s3 select-box">
                    <div class="input-field">
                        <select>
                            <option value="1">CPF</option>
                            <option value="2">Nome</option>
                            <option value="3">Email</option>
                            <option value="4">Telefone</option>
                        </select>
                        <label>Filtro</label>
                    </div>
                </div>
            </div>
            
            <div>
                <jsp:include page="checkin-tabela.jsp"></jsp:include>
            </div>
            
            <form id="frmCheckIn" method="post">                
                <div id="container">
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">date_range</i>
                                <label for="diasDeEstadia">Dias de estadia</label>
                                <input id="diasDeEstadia" name="diasDeEstadia" type="number" class="validate" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">wc</i>
                                <label for="nroAdultos">Quantidade de adultos</label>
                                <input id="nroAdultos" name="nroAdultos" type="number" class="validate" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 form-input">
                            <div class="input-field">
                                <i class="material-icons prefix">face</i>
                                <label for="nroCriancas">Quantidade de crianças</label>
                                <input id="nroCriancas" name="nroCriancas" type="number" class="validate" required>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card-action right-align button-box">
                    <button id="checkin-button" class="btn waves-effect waves-light" onclick="executaCheckIn()"><i class="material-icons left">person_add</i>Check-in</button>
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
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/materialize/materialize.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/checkin.js"></script>
    </body>
</html>
