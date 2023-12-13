/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;

import javax.persistence.*;
import java.util.*;
import java.text.*;

@Entity
@Table(name = "tb_funcionario")
@DiscriminatorValue("F")
public class Funcionario extends Pessoa {
    
    @Column(nullable = false, length = 100)
    private String numero_ctps;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_admmissao;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_demissao;
    
    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;
    
    @ManyToMany
    @JoinTable(name = "tb_funcionario_curso",
        joinColumns = { @JoinColumn(name = "funcionario_cpf") },
        inverseJoinColumns = { @JoinColumn(name = "curso_id") })
    private Collection<Curso> cursos;
    
    public String getCTPS() {
        return this.numero_ctps;
    }
    
    public Calendar getDataAdmissao() {
        return this.data_admmissao;
    }
    
    public Calendar getDataDemissao() {
        return this.data_demissao;
    }
    
    public Cargo getCargo() {
        return this.cargo;
    }
    
    public Collection<Curso> getCursos() {
        return this.cursos;
    }
    
    public Funcionario(String tipo, String cpf, String nome, String senha, Calendar data_nascimento, String cep, String complemento, String numero, String numero_ctps, Calendar data_admmissao, Calendar data_demissao, Cargo cargo, Collection<Curso> cursos) {
        super(tipo, cpf, nome, senha, data_nascimento, cep, complemento, numero);
        this.numero_ctps = numero_ctps;
        this.data_admmissao = data_admmissao;
        this.data_demissao = data_demissao;
        this.cargo = cargo;
        this.cursos = cursos;
    }
    
    public String mostrarFuncionario()  {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        String func = "Meu nome é " + this.getNome() + ", nasci em " + format.format(this.getDataNascimento().getTime()) + " e essas sao minhas informacoes basicas\n"
        + " >> CPF: " + this.getCPF() + "\n"
        + " >> CEP: " + this.getCEP() + "\n"
        + " >> Complemento: " + this.getComplemento() + "\n"
        + " >> Numero CTPS: " + this.getCTPS() + "\n"
        + "Me empregaram em " + format.format(this.getDataAdmissao().getTime()) + " como "
        + this.getCargo().getDescricao() + ", mas fui demitido em " + format.format(this.getDataDemissao().getTime()) + "... :(\n\n"
        + ">> Os cursos da pessoa são: \n";
        
        for(Curso cur : this.cursos) {
            func += "** " + cur.mostrarCurso() + "\n";
        }
        
        return func;
    }
}