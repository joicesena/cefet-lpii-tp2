package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void adiciona(Usuario usuario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String qry = "INSERT INTO Usuario"
                + "(codUsuario, nomUsuario, codCargo, desSenha, desEmail)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, usuario.getCodUsuario());
        pStmt.setString(2, usuario.getNomUsuario());
        pStmt.setString(3, usuario.getCodCargo());
        pStmt.setString(4, usuario.getDesSenha());
        pStmt.setString(5, UtilidadesBD.stringParaSHA256(usuario.getDesEmail()));

        pStmt.execute();
    }

    @Override
    public Usuario[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Usuario "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
        ResultSet rs = pStmt.executeQuery();

        Usuario[] usuarioEncontrados = new Usuario[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            usuarioEncontrados[i] = new Usuario(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5));
            i++;
        }

        return usuarioEncontrados;
    }

    @Override
    public void atualiza(String pK, Usuario usuarioAtualizado) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String qry = "UPDATE Usuario "
                + "SET codUsuario = ?, nomUsuario = ?, codCargo = ?, desSenha= ?, desEmail = ? "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, usuarioAtualizado.getCodUsuario());
        pStmt.setString(2, usuarioAtualizado.getNomUsuario());
        pStmt.setString(3, usuarioAtualizado.getCodCargo());
        pStmt.setString(4, usuarioAtualizado.getDesSenha());
        if(usuarioAtualizado.getDesSenha().length() != 64)
            pStmt.setString(5, UtilidadesBD.stringParaSHA256(usuarioAtualizado.getDesEmail()));
        else 
            pStmt.setString(5, usuarioAtualizado.getDesEmail());
        pStmt.setString(6, pK);

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM Usuario "
                + "WHERE codUsuario = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
