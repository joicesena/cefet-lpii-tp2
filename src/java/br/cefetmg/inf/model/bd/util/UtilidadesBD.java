package br.cefetmg.inf.model.bd.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class UtilidadesBD {

    private static Connection con = new ConnectionFactory().getConnection();

    public static int contaLinhasResultSet(ResultSet rs) throws SQLException {
        rs.last();
        int nroLinhas = rs.getRow();

        return nroLinhas;
    }

    public static String stringParaSHA256(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest ALGORITMOSHA256 = MessageDigest.getInstance("SHA-256");

        byte messageDigestSenhaAdmin[] = ALGORITMOSHA256.digest(senha.getBytes("UTF-8"));

        StringBuilder hexStringSenhaAdmin = new StringBuilder();
        for (byte b : messageDigestSenhaAdmin) {
            hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
        }
        String senhaEmSHA256 = hexStringSenhaAdmin.toString();

        return senhaEmSHA256;
    }

    public static boolean verificaSenha(String senha, String senhaConvertida)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return senhaConvertida.equals(stringParaSHA256(senha));
    }

    public static int buscaUltimoRegistroRelacionadoAoQuarto(int nroQuarto)
            throws SQLException {
        String qry = "SELECT A.seqHospedagem "
                + "FROM Hospedagem A "
                + "JOIN QuartoHospedagem B ON A.seqHospedagem = B.seqHospedagem "
                + "WHERE B.nroQuarto = ? "
                + "ORDER BY A.datCheckIn DESC "
                + "LIMIT 1";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, nroQuarto);
        ResultSet rs = pStmt.executeQuery();

        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }
    
    public static ResultSet retornaRelatorioDespesas(int seqHospedagem, int nroQuarto) throws SQLException {
        String qry = "SELECT * "
                + "FROM  relatorioDespesas "
                + "WHERE seqHospedagem = ? AND nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        ResultSet rs = pStmt.executeQuery();

        return rs;
    }

    public static void apagarBDHosten() throws SQLException {
        Statement stmt = con.createStatement();
        stmt.execute("DROP SCHEMA public CASCADE;"
                + "CREATE SCHEMA public;"
                + "GRANT ALL ON SCHEMA public TO postgres;"
                + "GRANT ALL ON SCHEMA public TO public;");
    }

    public static void constroiBDHosten() throws SQLException {
        Statement stmt = con.createStatement();
        stmt.execute("-- Create tables section -------------------------------------------------\n"
                + "\n"
                + "-- Table Programa\n"
                + "\n"
                + "CREATE TABLE Programa(\n"
                + " codPrograma Character(3) NOT NULL,\n"
                + " desPrograma Character varying(40) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Programa\n"
                + "\n"
                + "ALTER TABLE Programa ADD CONSTRAINT Key1 PRIMARY KEY (codPrograma)\n"
                + ";\n"
                + "\n"
                + "-- Table Cargo\n"
                + "\n"
                + "CREATE TABLE Cargo(\n"
                + " codCargo Character(3) NOT NULL,\n"
                + " nomCargo Character varying(40) NOT NULL,\n"
                + " idtMaster Boolean NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Cargo\n"
                + "\n"
                + "ALTER TABLE Cargo ADD CONSTRAINT Key2 PRIMARY KEY (codCargo)\n"
                + ";\n"
                + "\n"
                + "-- Table CargoPrograma\n"
                + "\n"
                + "CREATE TABLE CargoPrograma(\n"
                + " codPrograma Character(3) NOT NULL,\n"
                + " codCargo Character(3) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table CargoPrograma\n"
                + "\n"
                + "ALTER TABLE CargoPrograma ADD CONSTRAINT Key3 PRIMARY KEY (codPrograma,codCargo)\n"
                + ";\n"
                + "\n"
                + "-- Table Usuario\n"
                + "\n"
                + "CREATE TABLE Usuario(\n"
                + " codUsuario Character(4) NOT NULL,\n"
                + " nomUsuario Character varying(90) NOT NULL,\n"
                + " codCargo Character(3) NOT NULL,\n"
                + " desSenha Character(64),\n"
                + " desEmail Character varying(60)\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Create indexes for table Usuario\n"
                + "\n"
                + "CREATE INDEX IX_Relationship6 ON Usuario (codCargo)\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Usuario\n"
                + "\n"
                + "ALTER TABLE Usuario ADD CONSTRAINT Key5 PRIMARY KEY (codUsuario)\n"
                + ";\n"
                + "\n"
                + "-- Table Quarto\n"
                + "\n"
                + "CREATE TABLE Quarto(\n"
                + " nroQuarto Smallint NOT NULL,\n"
                + " codCategoria Character(3) NOT NULL,\n"
                + " idtOcupado Boolean\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Create indexes for table Quarto\n"
                + "\n"
                + "CREATE INDEX IX_Relationship9 ON Quarto (codCategoria)\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Quarto\n"
                + "\n"
                + "ALTER TABLE Quarto ADD CONSTRAINT Key7 PRIMARY KEY (nroQuarto)\n"
                + ";\n"
                + "\n"
                + "-- Table Categoria\n"
                + "\n"
                + "CREATE TABLE Categoria(\n"
                + " codCategoria Character(3) NOT NULL,\n"
                + " nomCategoria Character varying(40) NOT NULL,\n"
                + " vlrDiaria Numeric(7,2) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Categoria\n"
                + "\n"
                + "ALTER TABLE Categoria ADD CONSTRAINT Key8 PRIMARY KEY (codCategoria)\n"
                + ";\n"
                + "\n"
                + "-- Table ItemConforto\n"
                + "\n"
                + "CREATE TABLE ItemConforto(\n"
                + " codItem Character(3) NOT NULL,\n"
                + " desItem Character varying(40) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table ItemConforto\n"
                + "\n"
                + "ALTER TABLE ItemConforto ADD CONSTRAINT Key9 PRIMARY KEY (codItem)\n"
                + ";\n"
                + "\n"
                + "-- Table CategoriaItemConforto\n"
                + "\n"
                + "CREATE TABLE CategoriaItemConforto(\n"
                + " codCategoria Character(3) NOT NULL,\n"
                + " codItem Character(3) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table CategoriaItemConforto\n"
                + "\n"
                + "ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Key10 PRIMARY KEY (codCategoria,codItem)\n"
                + ";\n"
                + "\n"
                + "-- Table Hospede\n"
                + "\n"
                + "CREATE TABLE Hospede(\n"
                + " codCPF Character(11) NOT NULL,\n"
                + " nomHospede Character varying(90) NOT NULL,\n"
                + " desTelefone Character varying(15) NOT NULL,\n"
                + " desEmail Character varying(90) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Hospede\n"
                + "\n"
                + "ALTER TABLE Hospede ADD CONSTRAINT Key11 PRIMARY KEY (codCPF)\n"
                + ";\n"
                + "\n"
                + "-- Table Hospedagem\n"
                + "\n"
                + "CREATE TABLE Hospedagem(\n"
                + " seqHospedagem Serial NOT NULL,\n"
                + " datCheckIn Timestamp NOT NULL,\n"
                + " datCheckOut Timestamp,\n"
                + " vlrPago Numeric(7,2),\n"
                + " codCPF Character(11)\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Create indexes for table Hospedagem\n"
                + "\n"
                + "CREATE INDEX IX_Relationship26 ON Hospedagem (codCPF)\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Hospedagem\n"
                + "\n"
                + "ALTER TABLE Hospedagem ADD CONSTRAINT Key14 PRIMARY KEY (seqHospedagem)\n"
                + ";\n"
                + "\n"
                + "-- Table Servico\n"
                + "\n"
                + "CREATE TABLE Servico(\n"
                + " seqServico Smallserial NOT NULL,\n"
                + " desServico Character varying(40) NOT NULL,\n"
                + " vlrUnit Numeric(7,2) NOT NULL,\n"
                + " codServicoArea Character(3) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Create indexes for table Servico\n"
                + "\n"
                + "CREATE INDEX IX_Relationship20 ON Servico (codServicoArea)\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table Servico\n"
                + "\n"
                + "ALTER TABLE Servico ADD CONSTRAINT Key15 PRIMARY KEY (seqServico)\n"
                + ";\n"
                + "\n"
                + "-- Table ServicoArea\n"
                + "\n"
                + "CREATE TABLE ServicoArea(\n"
                + " codServicoArea Character(3) NOT NULL,\n"
                + " nomServicoArea Character varying(40) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table ServicoArea\n"
                + "\n"
                + "ALTER TABLE ServicoArea ADD CONSTRAINT Key16 PRIMARY KEY (codServicoArea)\n"
                + ";\n"
                + "\n"
                + "-- Table QuartoConsumo\n"
                + "\n"
                + "CREATE TABLE QuartoConsumo(\n"
                + " seqHospedagem Integer NOT NULL,\n"
                + " nroQuarto Smallint NOT NULL,\n"
                + " datConsumo Timestamp NOT NULL,\n"
                + " qtdConsumo Smallint NOT NULL,\n"
                + " seqServico Smallint NOT NULL,\n"
                + " codUsuarioRegistro Character(4) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Create indexes for table QuartoConsumo\n"
                + "\n"
                + "CREATE INDEX IX_Relationship16 ON QuartoConsumo (seqServico)\n"
                + ";\n"
                + "\n"
                + "CREATE INDEX IX_Relationship17 ON QuartoConsumo (codUsuarioRegistro)\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table QuartoConsumo\n"
                + "\n"
                + "ALTER TABLE QuartoConsumo ADD CONSTRAINT Key17 PRIMARY KEY (datConsumo,seqHospedagem,nroQuarto)\n"
                + ";\n"
                + "\n"
                + "-- Table QuartoHospedagem\n"
                + "\n"
                + "CREATE TABLE QuartoHospedagem(\n"
                + " seqHospedagem Integer NOT NULL,\n"
                + " nroQuarto Smallint NOT NULL,\n"
                + " nroAdultos Smallint NOT NULL,\n"
                + " nroCriancas Smallint NOT NULL,\n"
                + " vlrDiaria Numeric(7,2) NOT NULL\n"
                + ")\n"
                + ";\n"
                + "\n"
                + "-- Add keys for table QuartoHospedagem\n"
                + "\n"
                + "ALTER TABLE QuartoHospedagem ADD CONSTRAINT Key18 PRIMARY KEY (seqHospedagem,nroQuarto)\n"
                + ";\n"
                + "-- Create foreign keys (relationships) section -------------------------------------------------\n"
                + "\n"
                + "ALTER TABLE CargoPrograma ADD CONSTRAINT Relationship2 FOREIGN KEY (codPrograma) REFERENCES Programa (codPrograma) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE CargoPrograma ADD CONSTRAINT Relationship3 FOREIGN KEY (codCargo) REFERENCES Cargo (codCargo) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE Usuario ADD CONSTRAINT Relationship6 FOREIGN KEY (codCargo) REFERENCES Cargo (codCargo) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Relationship7 FOREIGN KEY (codCategoria) REFERENCES Categoria (codCategoria) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Relationship8 FOREIGN KEY (codItem) REFERENCES ItemConforto (codItem) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE Quarto ADD CONSTRAINT Relationship9 FOREIGN KEY (codCategoria) REFERENCES Categoria (codCategoria) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship16 FOREIGN KEY (seqServico) REFERENCES Servico (seqServico) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship17 FOREIGN KEY (codUsuarioRegistro) REFERENCES Usuario (codUsuario) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE Servico ADD CONSTRAINT Relationship20 FOREIGN KEY (codServicoArea) REFERENCES ServicoArea (codServicoArea) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE QuartoHospedagem ADD CONSTRAINT Relationship21 FOREIGN KEY (seqHospedagem) REFERENCES Hospedagem (seqHospedagem) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE QuartoHospedagem ADD CONSTRAINT Relationship22 FOREIGN KEY (nroQuarto) REFERENCES Quarto (nroQuarto) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship24 FOREIGN KEY (seqHospedagem, nroQuarto) REFERENCES QuartoHospedagem (seqHospedagem, nroQuarto) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "ALTER TABLE Hospedagem ADD CONSTRAINT Relationship26 FOREIGN KEY (codCPF) REFERENCES Hospede (codCPF) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                + ";\n"
                + "\n"
                + "-- CRIA A VIEW TOP Q A LÍVIA FEZ ----------------------------------\n"
                + "\n"
                + "CREATE VIEW relatorioDespesas AS\n"
                + "SELECT A.seqHospedagem, A.nroQuarto, A.nroAdultos, A.nroCriancas, A.vlrDiaria,\n"
                + "       B.datCheckIn, B.datCheckOut, B.vlrPago,\n"
                + "       C.nomHospede,\n"
                + "       D.seqServico, D.qtdConsumo,\n"
                + "       E.desServico, E.vlrUnit\n"
                + "FROM\n"
                + "	QuartoHospedagem A\n"
                + "	JOIN Hospedagem B ON A.seqHospedagem = B.seqHospedagem\n"
                + "	JOIN Hospede C ON B.codCPF = C.codCPF\n"
                + "	JOIN QuartoConsumo D ON A.seqHospedagem = D.seqHospedagem AND A.nroQuarto = D.nroQuarto\n"
                + "	JOIN Servico E ON D.seqServico = E.seqServico;");
    }
}
