package br.cefetmg.inf.model.bd.dao.rel;

import br.cefetmg.inf.model.pojo.rel.CargoPrograma;
import java.sql.SQLException;

public interface CargoProgramaDAO {
    boolean adiciona(String codPrograma, String codCargo) throws SQLException;
    
    CargoPrograma[] busca(String cod, String coluna) throws SQLException;
    
    //atualiza();
    
    boolean deleta(String cod, String coluna) throws SQLException;
}
