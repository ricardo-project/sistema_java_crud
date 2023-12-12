
package br.edu.ifsul.bcc.lpoo.om.gui;

import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.gui.defaultclass.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;


public class JMenuBarHome extends JMenuBar implements ActionListener {

    
    private ArrayList<JMenu> JMenuL = new ArrayList<JMenu>();
    private ArrayList<JMenuItem> JMenuItemL = new ArrayList<JMenuItem>();
    
    private Controle controle;
    
    public JMenuBarHome(Controle controle){
        this.controle = controle;
        initComponents();
    } 
    
    private void addJMenu(String nome) {
        JMenu menu = new JMenu(nome);
        menu.setMnemonic(KeyEvent.VK_A);
        menu.setToolTipText(nome);
        menu.setFocusable(true);
        JMenuL.add(menu);
        this.add(menu);
    }
    
    private void addJMenuItem(String nome, String func, int iJM) {
        JMenuItem item = new JMenuItem(nome);
        item.setToolTipText(nome);
        item.setFocusable(true);
        
        item.addActionListener(this);
        item.setActionCommand(func);
        
        JMenuItemL.add(item);
        JMenuL.get(iJM).add(item);
    }
    
    private void initComponents(){
        
        addJMenu("Arquivo");
        addJMenu("Cadastro");
        
        addJMenuItem("Sair", "menu_sair", 0);
        addJMenuItem("Logout", "menu_logout", 0);
        
        addJMenuItem("Cargo", "tela_default_1", 1);
        addJMenuItem("Peça", "tela_default_2", 1);
        addJMenuItem("Curso", "tela_default_3", 1);
        addJMenuItem("Mão-de-Obra", "tela_default_4", 1);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals(JMenuItemL.get(0).getActionCommand())){
            
        } else if(ae.getActionCommand().equals(JMenuItemL.get(1).getActionCommand())){
            int decisao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair ?");
            if(decisao == 0){
                controle.getConexaoJDBC().fecharConexao();
                System.exit(0);
            }
        } else {
            for(int i = 0; i < JMenuItemL.size() - 2; i++) {
                if(ae.getActionCommand().equals(JMenuItemL.get(i + 2).getActionCommand())) {
                    JPanelDefaultListagem.numEntity = i;
                    controle.showTela("tela_default");
                    return;
                }
            }
        }
    }
    
}
