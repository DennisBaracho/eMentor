package ementor;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.PageSize;
import org.openpdf.text.Rectangle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class GerarRelatorioPDF {

    private void registrarErroLog(String codigo, String descricao) {

        try (FileWriter fw = new FileWriter("erros.dat", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            out.println("[" + dataHora + "] CÓDIGO: "
                    + codigo
                    + " | DESCRIÇÃO: "
                    + descricao);

        } catch (IOException e) {
            System.err.println("Erro crítico ao gravar no log.");
        }
    }

    public void gerarRelatorioAlunos(ArrayList<Aluno> listaAlunos, String caminhoArquivo) {

        Document documento = new Document(PageSize.A4,
            85f,   // margem esquerda (3 cm)
            57f,   // margem direita (2 cm)
            85f,   // margem superior (3 cm)
            57f    // margem inferior (2 cm)
        );
        
        try {

            PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));

            documento.open();

            Font fonteTitulo = new Font(Font.HELVETICA,16,Font.BOLD);
            Font fonteTexto = new Font(Font.HELVETICA,12,Font.NORMAL);
            Font fonteCabecalho = new Font(Font.HELVETICA,12,Font.BOLD);
            Font fonteSubtitulo = new Font(Font.HELVETICA, 12, Font.ITALIC);

            Paragraph titulo = new Paragraph(
                    "eMentor-Plus - Relatório de Alunos",
                    fonteTitulo);

            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            
            documento.add(titulo);

            String dataAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            Paragraph subtitulo = new Paragraph(
                    "Gerado em: " + dataAtual + "\n\n",
                    fonteSubtitulo);

            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(25);
            
            documento.add(subtitulo);

            // Caso não exista nenhum aluno
            if (listaAlunos == null || listaAlunos.isEmpty()) {

                documento.add(new Paragraph(
                        "Nenhum aluno cadastrado.",
                        fonteTexto));

                JOptionPane.showMessageDialog(
                        null,
                        "Relatório gerado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            PdfPTable tabela = new PdfPTable(5);

            tabela.setWidthPercentage(100);

            tabela.setWidths(new float[]{3f, 1.5f, 1.5f, 2f, 2f});

            String[] cabecalhos = {
                "Nome",
                "Matrícula",
                "Período",
                "Média",
                "Situação"
            };

            for (String cabecalho : cabecalhos) {

                PdfPCell celula = new PdfPCell(
                        new Phrase(cabecalho, fonteCabecalho));

                celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tabela.addCell(celula);
            }

            for (Aluno aluno : listaAlunos) {

                tabela.addCell(new Phrase(aluno.Nome, fonteTexto));

                tabela.addCell(new Phrase(
                        String.valueOf(aluno.getMatricula()),
                        fonteTexto));

                tabela.addCell(new Phrase(
                        aluno.getPeriodo() + "º",
                        fonteTexto));

                float soma = 0;
                int quantidadeNotas = 0;

                float[] notas = aluno.getNotas();

                if (notas != null) {

                    for (float nota : notas) {

                        soma += nota;

                        quantidadeNotas++;
                    }
                }

                float media = 0;

                if (quantidadeNotas > 0) {
                    media = soma / quantidadeNotas;
                }

                tabela.addCell(new Phrase(
                        String.format("%.2f", media),
                        fonteTexto));

                String situacao;

                if (media >= 7.0f) {

                    situacao = "Aprovado";

                } else if (media >= 4.0f) {

                    situacao = "Recuperação";

                } else {

                    situacao = "Reprovado";
                }

                tabela.addCell(new Phrase(situacao, fonteTexto   ));

            }
            
            tabela.setSpacingBefore(10);
            tabela.setSpacingAfter(10);
            documento.add(tabela);

            JOptionPane.showMessageDialog(
                    null,
                    "Relatório PDF gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (DocumentException | IOException e) {

            registrarErroLog(
                    "OPENPDF_ERROR",
                    e.getMessage());

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao gerar PDF:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

        } finally {

            if (documento.isOpen()) {
                documento.close();
            }

        }

    }

}