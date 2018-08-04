package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DespesasPDFControllerServlet extends HttpServlet {
    private HttpServletRequest requestInterno;
    private Document document;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestInterno = request;
        response.setContentType("application/pdf");
        try {
            document = new Document(PageSize.A4, 90, 90, 90, 90);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            montaArquivo();
            document.close();
        } catch (DocumentException ex) {
            //
            //
            //
        } catch (SQLException ex) {
            //
            //
            //
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void montaArquivo() throws DocumentException, SQLException {
        int seqHospedagem = Integer.parseInt(requestInterno.getParameter("seqHospedagem"));
        
        // busca a view
        // retorna um resultSet
        int nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        ResultSet rs = UtilidadesBD.retornaRelatorioDespesas(seqHospedagem, nroQuarto);

        // monta o arquivo
        DottedLineSeparator separator = new DottedLineSeparator();

        Font fonteUltraViolet = new Font(HELVETICA, 20, 1, new BaseColor(95, 75, 139));
        Font fonteRedViolet = new Font(HELVETICA, 18, 1, new BaseColor(163, 87, 118));
        Font fonteSparklingGrape = new Font(HELVETICA, 14, 1, new BaseColor(125, 63, 124));
        Font fonteMulberry = new Font(HELVETICA, 14, 0, new BaseColor(167, 108, 151));
        Font fonteChateauRose = new Font(HELVETICA, 14, 0, new BaseColor(210, 115, 143));
        Font fonteChateauRoseNegrito = new Font(HELVETICA, 14, 1, new BaseColor(210, 115, 143));
        
        Chunk c = new Chunk(separator);
        Paragraph p = new Paragraph();
        
        // título
        p.setFont(fonteUltraViolet);
        p.add("Fatura da hospedagem");
        document.add(p);
        p.clear();
        
        // subtítulo com o nome do cliente
        String nomeHospede = null;
        if (rs.next()) {
            nomeHospede = rs.getString("nomHospede");
        }
        p.setFont(fonteRedViolet);
        p.setSpacingBefore(20);
        p.setSpacingAfter(20);
        p.add(nomeHospede);
        document.add(p);
        p.clear();
        
        rs.beforeFirst();
        Double vlrTotal = 0.0;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        
        // um parágrafo para cada item/servico consumido
        while (rs.next()) {
            int qtdServico = rs.getInt("qtdconsumo");
            String desServico = rs.getString("desserviço");
            Double vlrServico = rs.getDouble("vlrunit");
            
            vlrTotal += vlrServico*qtdServico;

            String strVlrServico = currencyFormatter.format(vlrServico);

            p.setFont(fonteMulberry);
            p.setSpacingAfter(2);
            p.setSpacingBefore(0);
            p.add(String.valueOf(qtdServico));
            p.add(desServico);
            p.add(c);
            p.setFont(fonteSparklingGrape);
            p.add(strVlrServico);
            document.add(p);
            p.clear();
        }

        // parágrafo com as informações da diária
        rs.beforeFirst();
        if (rs.next()) {
            int nroAdultos = rs.getInt("nroAdultos");
            int nroCriancas = rs.getInt("nroCriancas");
            Double vlrDiaria = rs.getDouble("vlrDiaria");
            
            Timestamp datCheckIn = rs.getTimestamp("datcheckin");
            Timestamp datCheckOut = rs.getTimestamp("datcheckout");
            long msDiferenca = (datCheckOut.getTime()) - (datCheckIn.getTime());
            long segundos = msDiferenca/1000;
            long minutos = segundos/60;
            long horas = minutos/60;
            long dias = horas/24;
            
            Double valorDiarias = dias*vlrDiaria;
            vlrTotal += valorDiarias;

            p.setFont(fonteChateauRose);
            p.setSpacingAfter(2);
            p.setSpacingBefore(0);

            p.add("Número de adultos");
            p.add(c);
            p.add(String.valueOf(nroAdultos));
            document.add(p);
            p.clear();

            p.add("Número de crianças");
            p.add(c);
            p.add(String.valueOf(nroCriancas));
            document.add(p);
            p.clear();

            p.add("Dias de estadia");
            p.add(c);
            p.add(String.valueOf(dias));
            document.add(p);
            p.clear();

            p.add("Valor total das diárias");
            p.add(c);
            String strValorDiarias = currencyFormatter.format(valorDiarias);
            p.setFont(fonteChateauRoseNegrito);
            p.add(strValorDiarias);
            document.add(p);
            p.clear();
        }
        
        // parágrafo com o valor total
        String strValorTotal = currencyFormatter.format(vlrTotal);
        p.setFont(fonteRedViolet);
        p.setSpacingBefore(20);
        p.add("Valor total");
        p.add(c);
        p.add(strValorTotal);
        document.add(p);
        
    }

}
