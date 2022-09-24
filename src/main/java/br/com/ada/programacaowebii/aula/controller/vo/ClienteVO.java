package br.com.ada.programacaowebii.aula.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteVO {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    
}