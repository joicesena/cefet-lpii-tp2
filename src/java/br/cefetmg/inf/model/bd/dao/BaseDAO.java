package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

abstract class BaseDAO<ObjetoDTO> {

    protected static Connection con;

    protected BaseDAO() {
        con = new ConnectionFactory().getConnection();
    }
    protected abstract void adiciona(ObjetoDTO obj) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;

    protected abstract ObjetoDTO[] busca(String coluna, String dadoBusca) throws SQLException;

    public abstract void atualiza(String pK, ObjetoDTO objAtualizado) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException;

    public abstract void deleta(String pK) throws SQLException;
}
