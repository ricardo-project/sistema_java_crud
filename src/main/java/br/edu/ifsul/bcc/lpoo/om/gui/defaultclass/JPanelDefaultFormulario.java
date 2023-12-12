
package br.edu.ifsul.bcc.lpoo.om.gui.defaultclass;
import br.edu.ifsul.bcc.lpoo.om.gui.funcionario.*;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class JPanelDefaultFormulario extends JPanel implements ActionListener{
    
    private BorderLayout borderLayout;
    static public JPanel pnlCentro;
    private GridBagLayout gridBagLayout;
    
    public Controle controle;
    public JPanelDefault pnlFuncionario;
    
    static public ArrayList<JLabel> lbl;
    static public ArrayList<JTextField> txf;
    
    private JPanel pnlSul;
    private JButton btnSalvar;
    private JButton btnCancelar;
 
    public JPanelDefaultFormulario(Controle controle, JPanelDefault pnlFuncionario) {
        this.controle = controle;
        this.pnlFuncionario = pnlFuncionario;
        
        initComponents();
    }
    
    /*public void populaComboCargo() throws Exception {
        cbxCargo.removeAllItems();//zera o combo
        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxCargo.getModel();
        model.addElement("Selecione");
        model.addAll(controle.getConexaoJDBC().listCargo());
        
    }
    
    public Funcionario getFuncionarioFormulario(){
        
        if(txfCPF.getText().trim().length() == 11 &&
           txfNome.getText().trim().length() > 0  &&
           new String(psfSenha.getPassword()).trim().length() > 3 &&
           txfNumero_ctps.getText().trim().length() > 3 &&
           cbxCargo.getSelectedIndex() > 0){
             
            
            Funcionario f = new Funcionario(
                "F",
                txfCPF.getText().trim(),
                txfNome.getText().trim(),
                new String(psfSenha.getPassword()).trim(),
                Calendar.getInstance(),
                "", "", "",
                txfNumero_ctps.getText().trim(),
                Calendar.getInstance(),
                Calendar.getInstance(),
                (Cargo) cbxCargo.getSelectedItem(),
                new ArrayList<Curso>()
            );
            return f;
        }
        
        return null;
    }
    
    public void setFuncionarioFormulario(Funcionario f){
        if(f == null){
            txfCPF.setText("");
            txfNome.setText("");
            psfSenha.setText("");
            txfNumero_ctps.setText("");
            txfDataAdmissao.setText("");
            txfDataNascimento.setText("");
            cbxCargo.setSelectedIndex(0);
        }else{
            txfCPF.setText(f.getCPF());
            txfNome.setText(f.getNome());
            psfSenha.setText(f.getSenha());
            txfNumero_ctps.setText(f.getCTPS());
            txfDataNascimento.setText(f.getDataNascimento());
            txfDataAdmissao.setText(f.getDataAdmissao());
            cbxCargo.setSelectedItem(f.getCargo());
                    
        }
    }*/
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        pnlCentro = new JPanel();
        gridBagLayout = new GridBagLayout();
        pnlCentro.setLayout(gridBagLayout);
        
        
        this.add(pnlCentro, BorderLayout.CENTER);
        
        pnlSul = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnSalvar.setActionCommand("botao_salvar");
        btnSalvar.addActionListener(this);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand("botao_cancelar");
        btnCancelar.addActionListener(this);
        pnlSul.setLayout(new FlowLayout());
        pnlSul.add(btnSalvar);
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
    }
    
   

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals(btnCancelar.getActionCommand())){
            
            pnlFuncionario.showTela("tela_default_listagem");
            
        } else if(ae.getActionCommand().equals(btnSalvar.getActionCommand())){
            
            /*Funcionario f = getFuncionarioFormulario();//recupera os dados do formulario
            
            if(f != null){
                try {
                    pnlFuncionario.getControle().getConexaoJDBC().persist(f);
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlFuncionario.showTela("tela_default_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Jogador! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else{
                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }*/
            
        }
    }
    
    
    
}
