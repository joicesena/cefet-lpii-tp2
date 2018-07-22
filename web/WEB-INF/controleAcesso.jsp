<%@page import="br.cefetmg.inf.model.login.AcessoPrograma"%>
<%
    AcessoPrograma acesso = new AcessoPrograma();
    String codCargo = (String)request.getSession().getAttribute("codCargo");
    String nomePagina = (String)request.getParameter("nomePagina");

    boolean acessoPermitido =
            acesso.temAcessoPagina(codCargo, nomePagina);

    if (!acessoPermitido) {
        request.setAttribute("mensagemAcessoNegado", "Não é permitido o acesso a essa página");
        request.getRequestDispatcher("/view/visualizacaoEstadoQuartos.jsp").forward(request, response);
    }
    
%>
