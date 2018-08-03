<%@page import="br.cefetmg.inf.model.login.AcessoPrograma"%>
<%@page import="java.io.PrintWriter"%>
<%
    AcessoPrograma acesso = new AcessoPrograma();
    String codCargo = (String)request.getSession().getAttribute("codCargo");
    String nomePagina = (String)request.getParameter("nomePagina");

    boolean acessoPermitido =
            acesso.temAcessoPagina(codCargo, nomePagina);

    if (!acessoPermitido) {
		out.println("<script type=\"text/javascript\">");
		out.println("location='http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp';");
		out.println("alert('Você não tem acesso a essa página!');");
		out.println("</script>");            
    }
    
%>
