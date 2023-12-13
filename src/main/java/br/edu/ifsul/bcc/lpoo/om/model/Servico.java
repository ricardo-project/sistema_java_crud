/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;

import java.util.Calendar;
import java.util.Collection;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_servico")
public class Servico {
    @Id
    private Integer id;
    
    @Column(nullable = false)
    private Float valor;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_inicio;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_fim;
    
    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;
    
    @OneToMany
    @JoinColumn(name = "id_pagamento")
    private Collection<Pagamento> pagamento;
    
    @Column(nullable = false)
    private StatusServico status;
    
    @ManyToOne
    @JoinColumn(name = "id_orcamento", nullable = false)
    private Orcamento orcamento;
    
    Servico() {}
    
    Servico(Integer id, Float valor, Calendar data_inicio, Calendar data_fim, Equipe equipe, Pagamento pagamento, StatusServico status, Orcamento orcamento) {
        this.id = id;
        this.valor = valor;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.equipe = equipe;
        this.status = status;
        this.orcamento = orcamento;
    }
}
