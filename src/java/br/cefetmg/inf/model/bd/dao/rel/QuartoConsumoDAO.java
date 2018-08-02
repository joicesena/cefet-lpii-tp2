package br.cefetmg.inf.model.bd.dao.rel;

import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface QuartoConsumoDAO {

    boolean adiciona(QuartoConsumo quartoConsumo) throws SQLException;

    QuartoConsumo[] busca(String cod, String coluna) throws SQLException;
    
    //atualiza();
    
    boolean deleta(int seqHospedagem, int nroQuarto, Timestamp datConsumo) throws SQLException;
    boolean deleta(QuartoConsumo quartoConsumo) throws SQLException;
}
