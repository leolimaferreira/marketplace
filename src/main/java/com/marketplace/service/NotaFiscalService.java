package com.marketplace.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.ItemPedido;
import com.marketplace.model.Pagamento;
import com.marketplace.model.Pedido;
import com.marketplace.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.marketplace.utils.Constantes.PAGAMENTO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class NotaFiscalService {

    private final PagamentoRepository pagamentoRepository;

    public byte[] gerarNotaFiscal(UUID pagamentoId) {
        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new NaoEncontradoException(PAGAMENTO_NAO_ENCONTRADO));

        if (!pagamento.getStatus().toString().equals("CONCLUIDO")) {
            throw new IllegalStateException("Pagamento não está concluído");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            Pedido pedido = pagamento.getPedido();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            document.add(new Paragraph("NOTA FISCAL ELETRÔNICA")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            document.add(new Paragraph("NF-e Nº " + pagamento.getId())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            Table tabelaEmitente = new Table(1);
            tabelaEmitente.setWidth(UnitValue.createPercentValue(100));
            tabelaEmitente.addCell(criarCelulaTitulo("DADOS DO EMITENTE"));
            tabelaEmitente.addCell(criarCelulaDados(
                    "Razão Social: " + pedido.getLoja().getNome() + "\n" +
                            "CNPJ: " + pedido.getLoja().getCnpj()
            ));
            document.add(tabelaEmitente.setMarginBottom(15));

            Table tabelaDestinatario = new Table(1);
            tabelaDestinatario.setWidth(UnitValue.createPercentValue(100));
            tabelaDestinatario.addCell(criarCelulaTitulo("DADOS DO DESTINATÁRIO"));
            tabelaDestinatario.addCell(criarCelulaDados(
                    "Nome: " + pedido.getCliente().getNome() + "\n" +
                            "CPF: " + pedido.getCliente().getCpf() + "\n" +
                            "E-mail: " + pedido.getCliente().getEmail()
            ));
            document.add(tabelaDestinatario.setMarginBottom(15));

            Table tabelaNF = new Table(2);
            tabelaNF.setWidth(UnitValue.createPercentValue(100));
            tabelaNF.addCell(criarCelulaTitulo("Data de Emissão"));
            tabelaNF.addCell(criarCelulaTitulo("Forma de Pagamento"));
            tabelaNF.addCell(criarCelulaDados(pagamento.getDataAtualizacao().format(dateFormatter)));
            tabelaNF.addCell(criarCelulaDados(pagamento.getFormaPagamento().toString()));
            document.add(tabelaNF.setMarginBottom(15));

            Table tabelaProdutos = new Table(new float[]{3, 1, 1, 1});
            tabelaProdutos.setWidth(UnitValue.createPercentValue(100));

            tabelaProdutos.addHeaderCell(criarCelulaTitulo("Descrição"));
            tabelaProdutos.addHeaderCell(criarCelulaTitulo("Qtd"));
            tabelaProdutos.addHeaderCell(criarCelulaTitulo("Valor Unit."));
            tabelaProdutos.addHeaderCell(criarCelulaTitulo("Total"));

            for (ItemPedido item : pedido.getItens()) {
                tabelaProdutos.addCell(criarCelulaDados(item.getProduto().getNome()));
                tabelaProdutos.addCell(criarCelulaDados(String.valueOf(item.getQuantidade())));
                tabelaProdutos.addCell(criarCelulaDados("R$ " + item.getValorTotal()));
                tabelaProdutos.addCell(criarCelulaDados("R$ " + item.getValorTotal().multiply(BigDecimal.valueOf(item.getQuantidade()))));
            }

            document.add(tabelaProdutos.setMarginBottom(15));

            Table tabelaTotais = new Table(new float[]{3, 1});
            tabelaTotais.setWidth(UnitValue.createPercentValue(100));
            tabelaTotais.addCell(criarCelulaTitulo("VALOR TOTAL DA NOTA"));
            tabelaTotais.addCell(criarCelulaTitulo("R$ " + pagamento.getValor()).setTextAlignment(TextAlignment.RIGHT));
            document.add(tabelaTotais.setMarginBottom(20));

            document.add(new Paragraph("Este documento é uma representação simplificada da Nota Fiscal Eletrônica.")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar nota fiscal", e);
        }

        return baos.toByteArray();
    }

    private Cell criarCelulaTitulo(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
    }

    private Cell criarCelulaDados(String texto) {
        return new Cell()
                .add(new Paragraph(texto))
                .setPadding(5)
                .setBorder(Border.NO_BORDER);
    }
}
