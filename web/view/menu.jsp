<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="white">
    <div class="nav-wrapper container">
        <a href="#" class="brand-logo center">hosten</a>
        <ul class="left">
            <li><a id="button-menu" data-activates="slide-out"><i class="material-icons">menu</i></a></li>
        </ul>
    </div>
</nav>

<ul id="slide-out" class="side-nav">
    <li>
        <div class="user-view">
            <div class="background">
                <img src="../imgs/sidenav-background.jpg"/>
            </div>
            <a href="#!name"><span class="white-text name">Dominique DiPierro</span></a>
            <a href="#!email"><span class="white-text email">gerentedohotel@gmail.com</span></a>
        </div>
    <li>

    <li><a class="collapsible-header waves-effect" href="cargos.jsp"><i class="material-icons grey-text text-darken-3">business_center</i>Cargos</a></li>

    <li class="no-padding">
        <ul class="collapsible collapsible-accordion">
            <li>
                <a class="collapsible-header waves-effect" href="#"><i class="material-icons grey-text text-darken-3">assignment_ind</i>Funcionários<i class="material-icons right dropdown-icon">arrow_drop_down</i></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="funcionarios-visualizacao.jsp"><i class="material-icons">search</i>Visualizar funcionários</a></li>
                        <li><a href="funcionarios-cadastro.jsp"><i class="material-icons">person_add</i>Cadastrar novo funcionário</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </li>

    <li class="no-padding">
        <ul class="collapsible collapsible-accordion">
            <li>
                <a class="collapsible-header waves-effect" href="#"><i class="material-icons grey-text text-darken-3">local_hotel</i>Quartos<i class="material-icons right dropdown-icon">arrow_drop_down</i></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="quartos-visualizacao.jsp"><i class="material-icons">search</i>Visualizar quartos</a></li>
                        <li><a href="quartos-estados.jsp"><i class="material-icons">assignment</i>Estados dos quartos</a></li>
                        <li><a href="quartos-categorias.jsp"><i class="material-icons">local_offer</i>Categorias de quartos</a></li>
                        <li><a href="itens-conforto.jsp"><i class="material-icons">stars</i>Itens de conforto</a></li>
                    </ul>
              </div>
            </li>
        </ul>
    </li>

    <li class="no-padding">
        <ul class="collapsible collapsible-accordion">
            <li>
                <a class="collapsible-header waves-effect" href="#"><i class="material-icons grey-text text-darken-3">room_service</i>Serviços<i class="material-icons right dropdown-icon">arrow_drop_down</i></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="servicos.jsp"><i class="material-icons">search</i>Visualizar serviços</a></li>
                        <li><a href="servicos-areas.jsp"><i class="material-icons">search</i>Visualizar áreas de serviço</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </li>

    <li class="no-padding">
        <ul class="collapsible collapsible-accordion">
            <li>
                <a class="collapsible-header waves-effect" href="#"><i class="material-icons grey-text text-darken-3">people</i>Hóspedes<i class="material-icons right dropdown-icon">arrow_drop_down</i></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="hospedes-visualizacao.jsp"><i class="material-icons">search</i>Visualizar hóspedes</a></li>
                        <li><a href="#"><i class="material-icons">person_add</i>Cadastrar novo hóspede</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </li>

    <li><a class="collapsible-header waves-effect" href="<%= request.getContextPath() %>/logout"><i class="material-icons grey-text text-darken-3">exit_to_app</i>Logout</a></li>
</ul>
