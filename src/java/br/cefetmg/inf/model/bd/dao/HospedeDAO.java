package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.dto.Hospede;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospedeDAO extends ObjetoBaseDAO {

    public HospedeDAO() {
        super();
    }

    @Override
    public void adiciona(Object objDTO) {
        if (objDTO instanceof Hospede) {
            String sql = "INSERT INTO hospede"
                    + "(codCPF,nomHospede,nroTelefone,desEmail)"
                    + " VALUES (?,?,?,?)";
            Hospede hospede = ((Hospede) objDTO);

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, hospede.getCodCPF());
                stmt.setString(2, hospede.getNomHospede());
                stmt.setString(3, hospede.getNroTelefone());
                stmt.setString(4, hospede.getDesEmail());
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Hospede busca(String tabela, String coluna, String chaveBusca) throws SQLException {
        ResultSet rs;
        rs = super.buscaGeneralziada(tabela, coluna, chaveBusca);

        Hospede hospedeEncontrado = new Hospede(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getString(4));
        
        return hospedeEncontrado;
    }
}
