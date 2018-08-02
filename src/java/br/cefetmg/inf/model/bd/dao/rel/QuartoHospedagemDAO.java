package br.cefetmg.inf.model.bd.dao.rel;

import br.cefetmg.inf.model.pojo.rel.QuartoHospedagem;
import java.sql.SQLException;

public interface QuartoHospedagemDAO {

    boolean adiciona(QuartoHospedagem quartoHospedagem) throws SQLException;

    QuartoHospedagem[] busca(String cod, String coluna) throws SQLException;

    //atualiza();
    
    boolean deleta(int seqHospedagem, int nroQuarto) throws SQLException;
    boolean deleta(QuartoHospedagem quartoHospedagem) throws SQLException;
}
