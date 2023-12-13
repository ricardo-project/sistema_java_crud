/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;

import java.text.SimpleDateFormat;
import javax.persistence.*;
import java.util.*;

/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_cliente")
@DiscriminatorValue("C")
public class Cliente extends Pessoa {
    
    @Column(nullable = false, length = 100)
    private String observacoes;
    
    @ManyToMany
    @JoinTable(name = "tb_cliente_veiculos", joinColumns = {@JoinColumn(name = "cpf_cliente")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "placa_veiculo")})       
    private Collection<Veiculo> veiculos;
    
    Cliente() {}
    
    public Cliente(String tipo, String cpf, String nome, String senha, Calendar data_nascimento, String cep, String complemento, String numero, String observacoes, Collection<Veiculo> veiculos) {
        super(tipo, cpf, nome, senha, data_nascimento, cep, complemento, numero);
        this.observacoes = observacoes;
        this.veiculos = veiculos;
    }
    
    public String mostrarCliente()  {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        String func = " +--------------------+---------------------\n"
        + " | Nome               | " + this.getNome() + "\n"
        + " | Senha              | " + this.getSenha() + "\n"
        + " | Data de nascimento | " + format.format(this.getDataNascimento().getTime()) + "\n"
        + " | Numero             | " +this.getNumero() + "\n"
        + " | CPF                | " + this.getCPF() + "\n"
        + " | CEP                | " + this.getCEP() + "\n"
        + " | Complemento        | " + this.getComplemento() + "\n"
        + " | Observacoes        | " + this.getObs() + "\n"
        + " +--------------------+---------------------\n"
        + " | Veiculos do cliente \n";
        
        for(Veiculo V : this.veiculos) {
            func += V.mostrar() + "\n";
        }
        
        return func;
    }
    
    public String getObs() { return this.observacoes; }
    public Collection<Veiculo> getVeiculos() { return this.veiculos; }
}
