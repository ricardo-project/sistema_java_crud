/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collection;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author 20221PF.CC00
 */
@Entity
@Table(name = "tb_orcamento")
public class Orcamento {
    
    @Id
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String observacoes;
    
    @Column(nullable = false, length = 100)
    private Calendar data;
    
    @ManyToOne
    @JoinColumn(name = "cpf_funcionario", nullable = false)
    private Funcionario funcionario;
    
    @ManyToOne
    @JoinColumn(name = "cpf_cliente", nullable = false)
    private Cliente cliente;
    
    @ManyToMany
    @JoinTable(name = "tb_orcamento_veiculos", joinColumns = {@JoinColumn(name = "id_orcamento")},
                                       inverseJoinColumns = {@JoinColumn(name = "id_veiculo")})    
    private Collection<Veiculo> veiculos;
    
    @ManyToMany
    @JoinTable(name = "tb_orcamento_maoObra", joinColumns = {@JoinColumn(name = "id_orcamento")},
                                       inverseJoinColumns = {@JoinColumn(name = "id_maoObra")})       
    private Collection<MaoObra> maoObra;
    
    @ManyToMany
    @JoinTable(name = "tb_orcamento_pecas", joinColumns = {@JoinColumn(name = "id_orcamento")},
                                       inverseJoinColumns = {@JoinColumn(name = "id_maoObra")})       
    private Collection<Peca> pecas;
    
    Orcamento() {}
    
    Orcamento(Integer id, Calendar data, Veiculo veiculo, Cliente cliente, Peca peca, MaoObra maoObra) {
        this.id = id;
        this.data = data;
        this.veiculos.add(veiculo);
        this.cliente = cliente;
        this.pecas.add(peca);
        this.maoObra.add(maoObra);
    }
    
    /*String mostrar() {
        String res = "> Orçamento de id " + id + "\n\n *** Veículos ***\n";
        for(int k = 0; k < this.veiculos.size(); k++) {
            res += this.veiculos.get(k).mostrar();
        }
        
        res += "\n\n*** Cliente ***\n" + this.cliente.mostrar();
        
        res += "\n\n*** Peças ***\n";
        for(int k = 0; k < this.pecas.size(); k++) {
            res += this.pecas.get(k).mostrar();
        }
        
        res += "\n\n*** Mão de obra ***\n";
        for(int k = 0; k < this.maoObra.size(); k++) {
            res += this.maoObra.get(k).mostrar();
        }
        return res;
    }
    
    /*Orcamento(Integer id, String observacoes, Calendar data, Funcionario funcionario, Cliente cliente, ArrayList<Peca> pecas, ArrayList<MaoObra> maoObra, ArrayList<Veiculo> veiculos) {
        this.id = id;
        this.observacoes = observacoes;
        this.data = data;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.pecas = pecas;
        this.maoObra = maoObra;
        this.veiculos = veiculos;
    }*/
}
