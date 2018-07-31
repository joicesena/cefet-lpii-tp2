<jsp:include page="/WEB-INF/controleAcesso.jsp" flush="false">
    <jsp:param name="nomePagina" value="Tela de Áreas de Serviço"/>
</jsp:include>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Mostra para o browser que o site é otimizado para mobile -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Áreas de Serviços</title>

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
            <h4 class="title">Visualização de Áreas de Serviços</h4>
            
            <div id="container" class="row">
                <div class="col s9 search-box">
                    <div class="input-field">
                        <i class="material-icons prefix">search</i>
                        <label for="search">Buscar área de serviço por... (selecione um campo ao lado)</label>
                        <input id="search" type="search">
                    </div>
                </div>
                
                <div class="col s3 select-box">
                    <div class="input-field">
                        <select>
                            <option value="1">Código</option>
                            <option value="2">Nome</option>
                        </select>
                        <label>Filtro</label>
                    </div>
                </div>
            </div>
            
            <div>
                <jsp:include page="servicos-areas-tabela.jsp"></jsp:include>
            </div>
            
            <div class="card-action right-align button-box">
                <button data-target="modal-add-item" id="add-button" class="btn waves-effect waves-light modal-trigger"><i class="material-icons left">add_circle_outline</i>Nova Área de Serviço</button>
            </div>
            
            <!-- Modals -->  
            <!-- Adicionar -->
            <div id="modal-add-item" class="modal">
                <div class="modal-content">
                    <h4 class="title">Cadastro de Áreas de Serviço</h4>
                    <form id="frmInsertItem" method="post">
						<!-- INPUT TYPE HIDDEN PARA ESPECIFICAR A OPERAÇÃO; 2->inserir -->
						<input type="hidden" id="operacaoItem" name="operacaoItem" value="2">
                        <div id="modal-container">
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">filter_3</i>
										<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
										<!-- ID USADO NO JSON -->
                                        <label for="codServicoArea">Código</label>
                                        <input id="codServicoArea" name="codServicoArea" type="number" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">description</i>
										<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
										<!-- ID USADO NO JSON -->
                                        <label for="nomServicoArea">Nome</label>
                                        <input id="nomServicoArea" name="nomServicoArea" type="text" class="validate" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-action right-align button-box">
							<!-- CHAMADA DE MÉTODO PARA REGISTRAR A OPERAÇÃO -->
                            <button id="submit-button" class="btn waves-effect waves-light" onclick="saveInsertDialog()"><i class="material-icons left">check_circle_outline</i>Salvar área de serviço</button>
							<!-- CHAMADA DE MÉTODO PARA FECHAR O MODAL -->
                            <button id="cancel-button" class="btn waves-effect waves-light" onclick="cancelInsertDialog()"><i class="material-icons left">highlight_off</i>Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
            
            <!-- Editar -->
            <div id="modal-edit-item" class="modal">
                <div class="modal-content">
                    <h4 class="title">Edição de Áreas de Serviço</h4>
                    <form id="frmEditItem" method="post">
						<!-- INPUT TYPE HIDDEN PARA ESPECIFICAR A OPERAÇÃO; 4->editar -->
						<input type="hidden" id="operacaoItem" name="operacaoItem" value="4">
                        <div id="modal-container">
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">filter_3</i>
										<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
										<!-- ID USADO NO JSON -->
                                        <label for="codServicoArea">Código</label>
                                        <input id="codServicoArea" name="codServicoArea" type="number" class="validate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">description</i>
										<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
										<!-- ID USADO NO JSON -->
                                        <label for="nomServicoArea">Nome</label>
                                        <input id="nomServicoArea" name="nomServicoArea" type="text" class="validate" required>
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
            
            <!-- Excluir -->
            <div id="modal-delete-item" class="modal">
                <div class="modal-content">
                    <h4 class="title">Exclusão de Áreas de Serviço</h4>
                    <form id="frmDeleteItem" method="post">
						<!-- INPUT TYPE HIDDEN PARA ESPECIFICAR A OPERAÇÃO; 5->excluir -->
						<input type="hidden" id="operacaoItem" name="operacaoItem" value="5">
						<!-- INPUT TYPE HIDDEN PARA ESPECIFICAR O REGISTRO A EXCLUIR -->
						<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
						<!-- ID USADO NO JSON -->
						<input type="hidden" id="codServicoArea" name="codServicoArea">
                        <div id="modal-container">
                            <p>Tem certeza que deseja excluir a área de serviço selecionada? Se sim, confirme sua senha no campo abaixo:</p>
                            <div class="row">
                                <div class="col s12 form-input">
                                    <div class="input-field">
                                        <i class="material-icons prefix">lock</i>
										<!-- O ID E O NAME DEVEM SER OS MESMOS QUE SERÃO INFORMADOS NO SERVLET! MANTER PADRAO CAMEL CASE-->
										<!-- ID USADO NO JSON -->
                                        <label for="senhaFuncionario">Senha</label>
                                        <input id="senhaFuncionario" name="senhaFuncionario" type="password" class="validate" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-action right-align button-box">
							<!-- CHAMADA DE MÉTODO PARA REGISTRAR A OPERAÇÃO -->
                            <button id="submit-button" class="btn waves-effect waves-light" onclick="executeDeleteDialog()"><i class="material-icons left">check_circle_outline</i>Excluir</button>
							<!-- CHAMADA DE MÉTODO PARA FECHAR O MODAL -->
                            <button id="cancel-button" class="btn waves-effect waves-light" onclick="cancelDeleteDialog()"><i class="material-icons left">highlight_off</i>Cancelar</button>
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
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/servicos-areas.js"></script>
    </body>
</html>
