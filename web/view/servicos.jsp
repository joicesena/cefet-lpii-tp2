<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Serviços</title>

        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="../css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="../css/padrao-tipo-1.css"/>
    </head>
    
    <body>
        <header>
            <%@include file="menu.jsp"%>
        </header>

        <main>            
            <h4 class="title">Visualização de Serviços</h4>
            
            <div id="container" class="row">
                <div class="col s9 search-box">
                    <div class="input-field">
                        <i class="material-icons prefix">search</i>
                        <label for="search">Buscar serviço por... (selecione um campo ao lado)</label>
                        <input id="search" type="search">
                    </div>
                </div>
                
                <div class="col s3 select-box">
                    <div class="input-field">
                        <select>
                            <option value="1">Descrição</option>
                            <option value="2">Valor</option>
                            <option value="3">Área</option>
                        </select>
                        <label>Filtro</label>
                    </div>
                </div>
            </div>
            
            <table class="striped">
                <thead>
                    <tr>
                        <th>
                            Descrição
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th>
                            Valor
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th>
                            Área
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                            <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                        </th>
                        <th><center>Ações</center></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Massagem</td>
                        <td>R$ 50,00</td>
                        <td>Spa</td>
                        <td>
                            <center>
                                <a href="#"><i class="material-icons table-icon-edit" onclick="editItem()">create</i></a>
                                <a href="#"><i class="material-icons table-icon-delete" onclick="deleteItem()">delete</i></a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>Limpeza de Pele</td>
                        <td>R$ 40,00</td>
                        <td>Spa</td>
                        <td>
                            <center>
                                <a href="#"><i class="material-icons table-icon-edit" onclick="editItem()">create</i></a>
                                <a href="#"><i class="material-icons table-icon-delete" onclick="deleteItem()">delete</i></a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>Refeição</td>
                        <td>R$ 30,00</td>
                        <td>Restaurante</td>
                        <td>
                            <center>
                                <a href="#"><i class="material-icons table-icon-edit" onclick="editItem()">create</i></a>
                                <a href="#"><i class="material-icons table-icon-delete" onclick="deleteItem()">delete</i></a>
                            </center>    
                        </td>
                    </tr>
                    <tr>
                        <td>Café da manhã</td>
                        <td>R$ 15,00</td>
                        <td>Restaurante</td>
                        <td>
                            <center>
                                <a href="#"><i class="material-icons table-icon-edit" onclick="editItem()">create</i></a>
                                <a href="#"><i class="material-icons table-icon-delete" onclick="deleteItem()">delete</i></a>
                            </center>    
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="card-action right-align button-box">
                <button id="add-button" class="btn waves-effect waves-light" onclick="addItem()"><i class="material-icons left">add_circle_outline</i>Novo Serviço</button>
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
        <script type="text/javascript" src="../js/servicos.js"></script>
    </body>
</html>
