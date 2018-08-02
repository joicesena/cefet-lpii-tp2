package br.cefetmg.inf.model.bd.dao.rel;

import br.cefetmg.inf.model.pojo.rel.CategoriaItemConforto;
import java.sql.SQLException;

public interface CategoriaItemConfortoDAO {
    boolean adiciona(String codCategoria, String codItem) throws SQLException;
    
    CategoriaItemConforto[] busca(String cod, String coluna) throws SQLException;
    
    //atualiza();
    
    boolean deleta(String codCategoria, String codItem) throws SQLException;
}
