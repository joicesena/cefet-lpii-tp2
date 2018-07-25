package br.cefetmg.inf.model.login;

import java.sql.Connection;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class AcessoPrograma {

    private static Connection con = new ConnectionFactory().getConnection();

    public boolean temAcessoPagina(String codCargo, String desPrograma) throws SQLException {
        Statement stmt0 = con.createStatement();
        String qryMaster = "SELECT idtMaster FROM cargo WHERE codCargo = '" + codCargo + "' ";
        ResultSet rscargo = stmt0.executeQuery(qryMaster);
        if(rscargo.next()) {
            if (rscargo.getBoolean("idtMaster")) {
                return true;
            } else {
                // selecionar o codPrograma na tabela Programa que tem a desPrograma
                Statement stmt = con.createStatement();
                String qry = "SELECT codPrograma FROM programa WHERE desPrograma = '" + desPrograma + "' ";
                ResultSet rs = stmt.executeQuery(qry);

                if (rs.next()) {
                    String qry1 = "SELECT * FROM CargoPrograma WHERE codPrograma = ?  AND codCargo = ?";
                    PreparedStatement pstmt = con.prepareStatement(qry1);

                    pstmt.setString(1, rs.getString("codPrograma"));
                    pstmt.setString(2, codCargo);
                    ResultSet rs1 = pstmt.executeQuery();

                    if(rs1.next()) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}