package br.cefetmg.inf.model.login;

import java.sql.Connection;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class AcessoPrograma {

    private static Connection con = new ConnectionFactory().getConnection();

    public static boolean temAcessoPagina(String codCargo, String desPrograma) throws SQLException {
        // selecionar o codPrograma na tabela Programa que tem a desPrograma
        Statement stmt = con.createStatement();
        String qry = "SELECT B.codCargo "
                + "FROM Programa A "
                + "JOIN CargoPrograma B ON A.codPrograma = B.codPrograma "
                + "WHERE A.desPrograma = " + desPrograma;
        ResultSet rs = stmt.executeQuery(qry);

        rs.first();
        while (rs.next()) {
            if(codCargo.equals(rs.getString("codCargo"))) {
                return true;
            }
        }
        // ver se esse codPrograma está associado ao cargo na tabela CargoPrograma
        // retornar se sim ou nao
        return false;
    }
}
