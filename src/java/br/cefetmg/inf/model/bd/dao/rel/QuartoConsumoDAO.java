package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface QuartoConsumoDAO {

    boolean adiciona(int seqHospedagem, int nroQuarto, Timestamp datConsumo,
            int qtdConsumo, int seqServico, String codUsuarioRegistro)
            throws SQLException;

    // busca();
    //atualiza();
    
    boolean deleta(int seqHospedagem, int nroQuarto, Timestamp datConsumo) throws SQLException;
}
