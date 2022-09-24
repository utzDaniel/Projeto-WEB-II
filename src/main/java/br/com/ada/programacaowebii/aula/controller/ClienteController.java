package br.com.ada.programacaowebii.aula.controller;

import br.com.ada.programacaowebii.aula.controller.dto.ClienteDTO;
import br.com.ada.programacaowebii.aula.controller.vo.ClienteVO;
import br.com.ada.programacaowebii.aula.model.Cliente;
import br.com.ada.programacaowebii.aula.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cliente")
    public String criarCliente(@RequestBody ClienteVO clienteVO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteVO.getNome());
        cliente.setCpf(clienteVO.getCpf());
        cliente.setDataNascimento(clienteVO.getDataNascimento());
        clienteService.criarCliente(cliente);
        return "Cliente criado!";
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id") Long id) {
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok(clienteDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteVO clienteVO) {
        Cliente cliente = this.clienteService.atualizarClientePorId(id, clienteVO);
        if (Objects.nonNull(cliente)) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok().body(clienteDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<String> removerClientePorId(@PathVariable("id") Long id) {
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            this.clienteService.removerClientePorId(cliente.getId());
            return ResponseEntity.ok("Cliente removido!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        List<Cliente> clientes = this.clienteService.listarTodosClientes();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ClienteDTO> clienteDTOS = clientes.stream()
                                    .map(cliente -> {
                                            ClienteDTO clienteDTO = new ClienteDTO();
                                            clienteDTO.setNome(cliente.getNome());
                                            clienteDTO.setCpf(cliente.getCpf());
                                            clienteDTO.setDataNascimento(cliente.getDataNascimento());
                                            return clienteDTO;
                                    })
                                    //.filter(clienteDTO -> clienteDTO.getDataNascimento().isBefore(LocalDate.now())) //filtrando data de nascimento para antes da data atual
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOS);
    }

    @GetMapping("/clientes-por-nome/{nome}")
    public ResponseEntity<List<ClienteDTO>> listarClientesPorNome(@PathVariable("nome") String nome) {
        List<Cliente> clientes = this.clienteService.listarClientesPorNome(nome);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> {
                    ClienteDTO clienteDTO = new ClienteDTO();
                    clienteDTO.setNome(cliente.getNome());
                    clienteDTO.setCpf(cliente.getCpf());
                    clienteDTO.setDataNascimento(cliente.getDataNascimento());
                    return clienteDTO;
                })
                //.filter(clienteDTO -> clienteDTO.getDataNascimento().isBefore(LocalDate.now())) //filtrando data de nascimento para antes da data atual
                .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOS);
    }

}