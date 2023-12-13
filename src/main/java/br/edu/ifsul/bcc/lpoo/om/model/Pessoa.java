/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.text.SimpleDateFormat;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {
    
    @Id
    private String cpf;
    
    @Column(nullable = false, length = 50)
    private String nome;
    
    @Column(nullable = false, length = 50)
    private String senha;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_nascimento;
    
    @Column(nullable = true, length = 8)
    private String cep;
    
    @Column(nullable = true, length = 100)
    private String complemento;
    
    @Column(nullable = true, length = 100)
    private String numero;
    
    @Transient
    private String tipo;
    
    Pessoa() {}
    
    Pessoa(String tipo, String cpf, String nome, String senha, Calendar data_nascimento, String cep, String complemento, String numero) {
        this.tipo = tipo;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.cep = cep;
        this.complemento = complemento;
        this.numero = numero;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public String getCPF() {
        return this.cpf;
    }
    
    public String getSenha() {
        return this.senha;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public Calendar getDataNascimento() {
        return this.data_nascimento;
    }
    
    public String getCEP() {
        return this.cep;
    }
    
    public String getComplemento() {
        return this.complemento;
    }
    
    public String getNumero() {
        return this.numero;
    }
    
    String mostrar() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String res = "> " + this.nome + "\n";
        res += " @ CPF: " + this.cpf + "\n";
        res += " @ Data de nascimento: " + sdf.format(this.data_nascimento.getTime()) + "\n";
        res += " @ Complemento: " + this.complemento + "\n";
        res += " @ Complemento: " + this.complemento + "\n";
        return res;
    }
}
