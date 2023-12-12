/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_maoobra")
public class MaoObra {
    
    @Id
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String descricao;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempo_estimado_execucao;
    
    @Column(nullable = false)
    private Float valor;
    
    public Integer getId() { return this.id; }
    public String getDescricao() { return this.descricao; }
    public Date getTempoEstimado() { return this.tempo_estimado_execucao; }
    public Float getValor() { return this.valor; }
    
    public MaoObra() {}
    
    public MaoObra(Integer id, String descricao, Date data, Float valor) {
        this.id = id;
        this.descricao = descricao;
        this.tempo_estimado_execucao = data;
        this.valor = valor;
    }
    
    String mostrar() {
        String res = "> Mão de obra de id " + this.id + "\n";
        res += " @ Descrição: " + this.descricao + "\n";
        res += " @ Tempo de execução: " + this.tempo_estimado_execucao + "\n";
        res += " @ Valor: " + this.valor + "\n";
        return res;
    }
}
