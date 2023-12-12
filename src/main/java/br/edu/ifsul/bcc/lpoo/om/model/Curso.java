package br.edu.ifsul.bcc.lpoo.om.model;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_curso")
public class Curso {
    
    @Id
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String descricao;
    
    /*@Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dt_conclusao;
    
    @Column(nullable = false)
    private Integer cargaHoraria;*/
    
    public Curso(Integer id, String descricao) {//, Calendar dt_conclusao, Integer cargaHoraria) {
        this.id = id;
        this.descricao = descricao;
        //this.dt_conclusao = dt_conclusao;
        //this.cargaHoraria = cargaHoraria;
    }
    
    public Integer getId() { return this.id; }
    public String getDescricao() { return this.descricao; }
    
    public void setId(Integer id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String mostrarCurso() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return (this.descricao + " (Id: " +
                this.id + ")");// + "; Carga horária: " + this.cargaHoraria + ")");
    }
}
