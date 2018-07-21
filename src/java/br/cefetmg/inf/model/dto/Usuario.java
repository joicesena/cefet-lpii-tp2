package br.cefetmg.inf.model.dto;

import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Usuario {

    private String codUsuario;
    private String nomUsuario;
    private String codCargo;
    private String desSenha;
    private String desEmail;

    public Usuario(String codUsuario, String nomUsuario, String codCargo, String desSenha, String desEmail) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.codUsuario = codUsuario;
        this.nomUsuario = nomUsuario;
        this.codCargo = codCargo;
        this.desSenha = UtilidadesBD.stringParaSHA256(desSenha);
        this.desEmail = desEmail;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(String codCargo) {
        this.codCargo = codCargo;
    }

    public String getDesSenha() {
        return desSenha;
    }

    public void setDesSenha(String desSenha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(desSenha.length() == 64) {
            this.desSenha = desSenha;
        } else {
            this.desSenha = UtilidadesBD.stringParaSHA256(desSenha);
        }
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }
}
