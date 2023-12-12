/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "tb_equipe")
public class Equipe {
    
    @Id
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 100)
    private String especialidades;
    
    @ManyToMany
    @JoinTable(name = "tb_equipe_funcionario", joinColumns = {@JoinColumn(name = "id_equipe")},
                                       inverseJoinColumns = {@JoinColumn(name = "id_funcionario")})
    private Collection<Funcionario> funcionarios;
    
    @Column(nullable = false)
    private FormaPagamento forma;
    
    Equipe() {}
    
    Equipe(Integer id, String nome, String especialidades, ArrayList<Funcionario> funcionarios, FormaPagamento forma) {
        this.id = id;
        this.nome = nome;
        this.especialidades = especialidades;
        this.funcionarios = funcionarios;
        this.forma = forma;
    }
}
