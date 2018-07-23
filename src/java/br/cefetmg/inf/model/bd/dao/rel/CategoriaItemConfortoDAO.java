package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;

public interface CategoriaItemConfortoDAO {
    void adiciona(String codCategoria, String codItem) throws SQLException;
    
    //busca();
    
    //atualiza();
    
    void deleta(String codCategoria, String codItem) throws SQLException;
}
