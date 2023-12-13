/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    
    @Id
    private Integer id;
    
    @Column(nullable = false)
    private Integer numero_parcela;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_vencimento;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_pagamento;
    
    @Column(nullable = false)
    private Float valor;
    
    @Column(nullable = false)
    private FormaPagamento forma;
    
    Pagamento(Integer id, Integer numero_parcela, Calendar data_vencimento, Calendar data_pagamento, Float valor, FormaPagamento forma) {
        this.id = id;
        this.numero_parcela = numero_parcela;
        this.data_vencimento = data_vencimento;
        this.data_pagamento = data_pagamento;
        this.valor = valor;
        this.forma = forma;
    }
}
