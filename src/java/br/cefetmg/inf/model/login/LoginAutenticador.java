package br.cefetmg.inf.model.login;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginAutenticador {
    private String email;
    private String senha;
    private String codCargo;

    public LoginAutenticador(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
    
    public boolean loginValido () throws SQLException {
        Connection conexao = new ConnectionFactory().getConnection();
        Statement stmt = conexao.createStatement();
        conexao.setAutoCommit(false);
        
        String query = "SELECT * FROM Usuario WHERE desEmail = '" + email + "' AND desSenha = '" + senha + "' ";
        
        ResultSet rs = stmt.executeQuery(query);
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
