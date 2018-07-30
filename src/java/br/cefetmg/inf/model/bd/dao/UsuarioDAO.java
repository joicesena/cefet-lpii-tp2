package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO extends BaseDAO<Usuario> {

    private static UsuarioDAO instancia;

    private UsuarioDAO() {
        super();
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(Usuario usuario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String qry = "INSERT INTO Usuario"
                + "(codUsuario, nomUsuario, codCargo, desSenha, desEmail)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, usuario.getCodUsuario());
        pStmt.setString(2, usuario.getNomUsuario());
        pStmt.setString(3, usuario.getCodCargo());
        pStmt.setString(4, usuario.getDesSenha());
        pStmt.setString(5, usuario.getDesEmail());

        return pStmt.execute();
    }

    @Override
    public Usuario[] busca(String coluna, Object dadoBusca) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        int i = 0;

        String qry = "SELECT * FROM Usuario "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        Usuario[] usuarioEncontrados = new Usuario[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            usuarioEncontrados[i] = new Usuario();
            usuarioEncontrados[i].setCodUsuario(rs.getString(1));
            usuarioEncontrados[i].setNomUsuario(rs.getString(2));
            usuarioEncontrados[i].setCodCargo(rs.getString(3));
            usuarioEncontrados[i].setDesSenhaSemSHA256(rs.getString(4));
            usuarioEncontrados[i].setDesEmail(rs.getString(5));
            i++;
        }

        return usuarioEncontrados;
    }
    
    @Override
    public Usuario[] busca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Usuario";
        ResultSet rs = stmt.executeQuery(qry);

        Usuario[] usuariosEncontrados
                = new Usuario[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            usuariosEncontrados[i] = new Usuario();
            usuariosEncontrados[i].setCodUsuario(rs.getString(1));
            usuariosEncontrados[i].setNomUsuario(rs.getString(2));
            usuariosEncontrados[i].setCodCargo(rs.getString(3));
            usuariosEncontrados[i].setDesSenhaSemSHA256(rs.getString(4));
            usuariosEncontrados[i].setDesEmail(rs.getString(5));
            i++;
        }

        return usuariosEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, Usuario usuarioAtualizado) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String qry = "UPDATE Usuario "
                + "SET codUsuario = ?, nomUsuario = ?, codCargo = ?, desSenha= ?, desEmail = ? "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, usuarioAtualizado.getCodUsuario());
        pStmt.setString(2, usuarioAtualizado.getNomUsuario());
        pStmt.setString(3, usuarioAtualizado.getCodCargo());
        pStmt.setString(4, usuarioAtualizado.getDesSenha());
        pStmt.setString(5, usuarioAtualizado.getDesEmail());
        if (pK instanceof String) {
            pStmt.setString(6, pK.toString());
        } else {
            pStmt.setInt(6, Integer.parseInt(pK.toString()));
        }

        return pStmt.execute();
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Usuario "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }
        
        return pStmt.execute();
    }
}
