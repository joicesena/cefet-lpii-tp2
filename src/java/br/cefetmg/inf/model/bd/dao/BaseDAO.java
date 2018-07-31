package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

abstract class BaseDAO<ObjetoPOJO> {

    protected static Connection con;

    protected BaseDAO() {
        con = new ConnectionFactory().getConnection();
    }
    protected abstract boolean adiciona(ObjetoPOJO obj) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;

    protected abstract ObjetoPOJO[] busca(String coluna, Object dadoBusca) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;
    protected abstract ObjetoPOJO[] busca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;

    protected abstract boolean atualiza(Object pK, ObjetoPOJO objAtualizado) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;

    protected abstract boolean deleta(Object pK) throws SQLException;
}
