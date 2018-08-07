package br.cefetmg.inf.model.bd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final String url =  "jdbc:postgresql://localhost:5432/hosten";
    private final String usuario = "postgres";
    private final String senha = "l0i3v0i7";
    
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
