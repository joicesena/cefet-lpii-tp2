package br.cefetmg.inf.model.login;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class LoginAutenticador {
    private static Connection con;
    private String email;
    private String senha;
    private String codCargo;

    public LoginAutenticador(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
    
    public boolean loginValido () throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        con = new ConnectionFactory().getConnection();
        Statement stmt = con.createStatement();
        
        senha = UtilidadesBD.stringParaSHA256(senha);
        String qry = "SELECT * FROM Usuario WHERE desEmail = " + email + " AND desSenha = " + senha;
        
        ResultSet rs = stmt.executeQuery(qry);
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
