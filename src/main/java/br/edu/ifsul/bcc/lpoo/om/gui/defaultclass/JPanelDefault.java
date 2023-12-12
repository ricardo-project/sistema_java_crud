
package br.edu.ifsul.bcc.lpoo.om.gui.defaultclass;

import br.edu.ifsul.bcc.lpoo.om.gui.funcionario.*;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class JPanelDefault extends JPanel {
    
    private CardLayout cardLayout;
    private Controle  controle;
    
    private JPanelDefaultFormulario formulario;
    private JPanelDefaultListagem listagem;
    
    public JPanelDefault(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
        
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelDefaultFormulario(getControle(), this);
        listagem = new JPanelDefaultListagem(getControle(), this);
        
        this.add(formulario, "tela_default_formulario");
        this.add(listagem, "tela_default_listagem");
                
    }
    
    public void showTela(String nomeTela){
        
        try {
            if(nomeTela.equals("tela_default_listagem")){
                listagem.populaTable();
            } else if(nomeTela.equals("tela_default_formulario")){
                //getFormulario().populaComboCargo();
            }
            ((CardLayout) this.getLayout()).show(this, nomeTela);
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Atenção", "Erro ao acessar dados: " + e.getLocalizedMessage(), JOptionPane.ERROR);
            e.printStackTrace();
        }
    }

    public JPanelDefaultFormulario getFormulario() { return formulario; }
    public Controle getControle() { return controle; }
    
    
}
