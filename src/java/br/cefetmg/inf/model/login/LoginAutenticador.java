package br.cefetmg.inf.model.login;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class LoginAutenticador {
    private static Connection con;
    private String email;
    private String senha;
    private String codCargo;

    public boolean loginValido (String email, String senha) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        senha = UtilidadesBD.stringParaSHA256(senha);
        
        con = new ConnectionFactory().getConnection();
        String qry = "SELECT * FROM usuario WHERE desEmail = ? AND desSenha = ?";

        PreparedStatement stmt = con.prepareStatement(qry);
        stmt.setString(1, email);
        stmt.setString(2, senha);
                
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            codCargo = rs.getString("codCargo");
            return true;
        } 
        return false;
    }
 
    public String retornaCargo () {
        return codCargo;
    }
}