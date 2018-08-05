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
    private String senhaSHA256;
    private String codCargo;
    private String codUsuario;

    public boolean loginValido (String email, String senha) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        con = new ConnectionFactory().getConnection();
        String qry = "SELECT * FROM usuario WHERE desEmail = ?";

        PreparedStatement stmt = con.prepareStatement(qry);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String senhaBD = rs.getString("desSenha");
            if (UtilidadesBD.verificaSenha(senha, senhaBD)) {
                codCargo = rs.getString("codCargo");
                codUsuario = rs.getString("codUsuario");
                return true;
            }
        } 
        return false;
    }
 
    public String getCargo () {
        return codCargo;
    }

    public String getCodUsuario () {
        return codUsuario;
    }
}