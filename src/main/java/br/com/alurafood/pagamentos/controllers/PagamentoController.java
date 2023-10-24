package br.com.alurafood.pagamentos.controllers;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDTO pagamentoDTO = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO dto, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamentoDTO = pagamentoService.criarPagamento(dto);
        URI uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(pagamentoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO pagamentoDTO) {
        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, pagamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> remover(@PathVariable @NotNull Long id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
