
package br.edu.ifsul.bcc.lpoo.om.gui;

import java.awt.*;
import javax.swing.*;

public class JFramePrincipal extends JFrame {
    
    public CardLayout cardLayout;
    public JPanel painel;
     
    
    public JFramePrincipal(){
        initComponents();
    }
    
    private void initComponents(){
        

        this.setTitle("Sisteminha para CRUD - Oficina Mecânica"); //seta o título do jframe
        this.setMinimumSize(new Dimension(600,600)); //tamanho minimo quando for reduzido.
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // por padrão abre maximizado.
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );// finaliza o processo quando o frame é fechado.  
        
        
        cardLayout = new CardLayout();//iniciando o gerenciador de layout para esta JFrame
        painel = new JPanel();//inicializacao
        painel.setLayout(cardLayout);//definindo o cardLayout para o paineldeFundo
        this.add(painel);  //adiciona no JFrame o paineldeFundo
         
    }
    
    public void addTela(JPanel p, String nome){   
        painel.add(p, nome); //adiciona uma "carta no baralho".
    }

    public void showTela(String nome){
        cardLayout.show(painel, nome); //localiza a "carta no baralho" e mostra.
    }
    
}
