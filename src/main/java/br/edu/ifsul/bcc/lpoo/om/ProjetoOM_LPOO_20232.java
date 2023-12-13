package br.edu.ifsul.bcc.lpoo.om;
import javax.swing.JOptionPane;

public class ProjetoOM_LPOO_20232 {
    
    private Controle controle;
    public ProjetoOM_LPOO_20232(){
        try {
            controle = new Controle();
            if(controle.conectarBD()){
                controle.initComponents();
            } else{
                JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", 
                        "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: " + ex.getLocalizedMessage(),
                    "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new ProjetoOM_LPOO_20232();
    }
}
