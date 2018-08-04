<jsp:include page="/WEB-INF/controleAcesso.jsp" flush="false">
    <jsp:param name="nomePagina" value="Tela de Hóspedes"/>
</jsp:include>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Visualização de Hóspedes</title>

        <!-- CSS -->
        <!-- Google Icon Font -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Materialize CSS -->
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/materialize/materialize.css"/>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/padrao-tipo-1.css"/>
    </head>
    
    <body>
        <header>
            <%@include file="menu.jsp"%>
        </header>

        <main>            
            <h4 class="title">Visualização de Hóspedes</h4>
            
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
                <jsp:include page="hospedes-tabela.jsp"></jsp:include>
            </div>
            
            <div class="card-action right-align button-box">
                <button data-target="modal-add-item" id="add-button" class="btn waves-effect waves-light modal-trigger"><i class="material-icons left">add_circle_outline</i>Novo Hóspede</button>
            </div>
            
            <!-- Modals -->  
            <!-- Adicionar -->
            <div id="modal-add-item" class="modal">
                <div class="modal-content">
                    <h4 class="title">Cadastro de Hóspedes</h4>
                    <form id="frmInsertItem" method="post">
                        <!-- INPUT TYPE HIDDEN PARA ESPECIFICAR A OPERAÇÃO; 2->inserir -->
                        <input type="hidden" id="operacaoItem" name="operacaoItem" value="2">
                        <div id="modal-container">
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">chrome_reader_mode</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="codCPF">CPF</label>
                                        <input id="codCPF" name="codCPF" type="number" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">person</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="nomHospede">Nome</label>
                                        <input id="nomHospede" name="nomHospede" type="text" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">email</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="desEmail">Email</label>
                                        <input id="desEmail" name="desEmail" type="email" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">call</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="desTelefone">Telefone</label>
                                        <input id="desTelefone" name="desTelefone" type="text" class="validate" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-action right-align button-box">
                            <!-- CHAMADA DE MÉTODO PARA REGISTRAR A OPERAÇÃO -->
                            <button id="submit-button" class="btn waves-effect waves-light" onclick="saveInsertDialog()"><i class="material-icons left">check_circle_outline</i>Salvar hóspede</button>
                            <!-- CHAMADA DE MÉTODO PARA FECHAR O MODAL -->
                            <button id="cancel-button" class="btn waves-effect waves-light" onclick="cancelInsertDialog()"><i class="material-icons left">highlight_off</i>Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
            
            <!-- Editar -->
            <div id="modal-edit-item" class="modal">
                <div class="modal-content">
                    <h4 class="title">Edição de Hóspedes</h4>
                    <form id="frmEditItem" method="post">
                        <!-- INPUT TYPE HIDDEN PARA ESPECIFICAR A OPERAÇÃO; 4->editar -->
                        <input type="hidden" id="operacaoItem" name="operacaoItem" value="4">
                        <div id="modal-container">
                                                    <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">chrome_reader_mode</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="codCPF">CPF</label>
                                        <input id="codCPF" name="codCPF" type="number" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">person</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="nomHospede">Nome</label>
                                        <input id="nomHospede" name="nomHospede" type="text" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">email</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="desEmail">Email</label>
                                        <input id="desEmail" name="desEmail" type="email" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">call</i>
                                        <!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
                                        <!-- ID USADO NO JSON -->
                                        <label for="desTelefone">Telefone</label>
                                        <input id="desTelefone" name="desTelefone" type="text" class="validate" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-action right-align button-box">
                            <!-- CHAMADA DE MÉTODO PARA REGISTRAR A OPERAÇÃO -->
                            <button id="submit-button" class="btn waves-effect waves-light" onclick="saveEditDialog()"><i class="material-icons left">check_circle_outline</i>Salvar alterações</button>
                            <!-- CHAMADA DE MÉTODO PARA FECHAR O MODAL -->
                            <button id="cancel-button" class="btn waves-effect waves-light" onclick="cancelEditDialog()"><i class="material-icons left">highlight_off</i>Cancelar</button>
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
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/materialize/materialize.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/hospedes.js"></script>
    </body>
</html>
