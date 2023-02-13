package br.com.alurafood.pedidos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private StatusPagamento status;
    private Long pedidoId;
    private Long formaDePagamentoId;
    private List<ItemDoPedidoDto> itens;
}
