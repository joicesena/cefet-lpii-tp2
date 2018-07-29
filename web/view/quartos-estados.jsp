<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Estados dos Quartos</title>

        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="../css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="../css/quartos-estados.css"/>
    </head>
    
    <body>
        <header>
            <%@include file="menu.jsp"%>
        </header>

        <main>            
            <h4 class="title">Visualização de Estados dos Quartos</h4>
            
            <div id="container" class="row">
                <div class="col s9 search-box">
                    <div class="input-field">
                        <i class="material-icons prefix">search</i>
                        <label for="search">Buscar por... (selecione um campo ao lado)</label>
                        <input id="search" type="search">
                    </div>
                </div>
                
                <div class="col s3 select-box">
                    <div class="input-field">
                        <select>
                            <option value="1">Número</option>
                            <option value="2">Estado</option>
                            <option value="3">Data Limite da Estadia</option>
                        </select>
                        <label>Filtro</label>
                    </div>
                </div>
            </div>
            
            <table class="striped">
                <thead>
                    <tr>
                        <th>
                            Número
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th>
                            Estado
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th>
                            Data Limite da Estadia (prevista)
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th><center>Ações</center></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>101</td>
                        <td>Ocupado</td>
                        <td>20/07/2018</td>
                        <td>
                            <center>
                                <a href="conta-detalhes.jsp" id="info-button" class="waves-effect waves-light btn"><i class="material-icons left">info_outline</i>Detalhes</a>
                                <a href="checkout.jsp" id="checkout-button" class="waves-effect waves-light btn"><i class="material-icons left">remove_circle_outline</i>Check-out</a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>102</td>
                        <td>Ocupado</td>
                        <td>12/07/2018</td>
                        <td>
                            <center>
                                <a href="conta-detalhes.jsp" id="info-button" class="waves-effect waves-light btn"><i class="material-icons left">info_outline</i>Detalhes</a>
                                <a href="checkout.jsp" id="checkout-button" class="waves-effect waves-light btn"><i class="material-icons left">remove_circle_outline</i>Check-out</a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>103</td>
                        <td>Livre</td>
                        <td><center>-</center></td>
                        <td>
                            <center>
                                <a id="checkin-button" class="waves-effect waves-light btn" onclick="checkin()"><i class="material-icons left">add_circle_outline</i>Check-in</a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>104</td>
                        <td>Livre</td>
                        <td><center>-</center></td>
                        <td>
                            <center>
                                <a id="checkin-button" class="waves-effect waves-light btn" onclick="checkin()"><i class="material-icons left">add_circle_outline</i>Check-in</a>
                            </center>    
                        </td>
                    </tr>
                </tbody>
            </table>
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
        <script type="text/javascript" src="../js/quartos-estados.js"></script>
    </body>
</html>
