/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;
/**
 *
 * @author 20221PF.CC00
 */

@Entity
@Table(name = "tb_veiculo")
public class Veiculo {
    
    @Id
    private String placa;
    
    @Column(nullable = false, length = 100)
    private String modelo;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_ultimo_servico;
    
    Veiculo() {}
    
    public Veiculo(String placa, String modelo, Integer ano, Calendar data) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.data_ultimo_servico = data;
    }
    
    public String getPlaca() { return this.placa; }
    public String getModelo() { return this.modelo; }
    public Integer getAno() { return this.ano; }
    public Calendar getData() { return this.data_ultimo_servico; }
    
    String mostrar() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String res = " | >> Veículo de placa " + this.placa + "\n";
        res += " |  @ Modelo: " + this.modelo + "\n";
        res += " |  @ Ano: " + this.ano + "\n";
        res += " |  @ Data do último serviço: " + sdf.format(this.data_ultimo_servico.getTime()) + "\n";
        return res;
    }
}
