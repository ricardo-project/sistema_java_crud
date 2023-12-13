/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.edu.ifsul.bcc.lpoo.om.model;
import java.io.Serializable;
import java.sql.*;
import javax.persistence.*;

/**
 *
 * @author 20221PF.CC00
 */
/*public enum Cargo {
    MECANICO,
    GERENTE,
    ATENDENTE
}*/

@Entity
@Table(name = "tb_cargo")
public class Cargo implements Serializable {
    
    @Id
    @SequenceGenerator(name = "seq_cargo", sequenceName = "seq_cargo_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_cargo", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String descricao;
    
    public Cargo(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
}