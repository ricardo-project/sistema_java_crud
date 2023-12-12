
package br.edu.ifsul.bcc.lpoo.om.gui.funcionario;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class JPanelFuncionarioFormulario extends JPanel implements ActionListener{
    
    private BorderLayout borderLayout;
    private JPanel pnlCentro;
    
    private GridBagLayout gridBagLayout;
    private GridBagConstraints posicionador;
    
    public Controle controle;
    public JPanelFuncionario pnlFuncionario;
    
    private JLabel lblCPF;
    private JTextField txfCPF;
    
    private JLabel lblNome;
    private JTextField txfNome;
    
    private JLabel lblSenha;
    private JPasswordField psfSenha;
    
    private JLabel lblNumero_ctps; 
    private JTextField txfNumero_ctps;
    private JLabel lblDataAdmissao;
    private JTextField txfDataAdmissao;
    
    private JLabel lblDataNascimento;
    private JTextField txfDataNascimento;
    
    private JLabel lblCargo;
    private JComboBox cbxCargo;
    private DefaultComboBoxModel modeloComboCargo;
    
    private JPanel pnlSul;
    private JButton btnSalvar;
    private JButton btnCancelar;
 
    public JPanelFuncionarioFormulario(Controle controle, JPanelFuncionario pnlFuncionario) {
        this.controle = controle;
        this.pnlFuncionario = pnlFuncionario;
        
        initComponents();
    }
    
    public void populaComboCargo() throws Exception {
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
            /*txfCPF.setText(f.getCPF());
            txfNome.setText(f.getNome());
            psfSenha.setText(f.getSenha());
            txfNumero_ctps.setText(f.getCTPS());
            txfDataNascimento.setText(f.getDataNascimento());
            txfDataAdmissao.setText(f.getDataAdmissao());
            cbxCargo.setSelectedItem(f.getCargo());*/
                    
        }
    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        pnlCentro = new JPanel();
        gridBagLayout = new GridBagLayout();
        pnlCentro.setLayout(gridBagLayout);
        
        
        lblCPF = new JLabel("CPF");
        txfCPF = new JTextField(20);
    
        lblNome = new JLabel("Nome:");
        txfNome = new JTextField(30);
    
        lblSenha = new JLabel("Senha:");
        psfSenha = new JPasswordField(10);
    
        lblNumero_ctps = new JLabel("Numero da CTPS:"); 
        txfNumero_ctps = new JTextField(10);
    
        lblDataAdmissao = new JLabel("Data de Admissão:");
        txfDataAdmissao = new JTextField(10);
        txfDataAdmissao.setEditable(false);
        
        lblDataNascimento = new JLabel("Data de Nascimento:");
        txfDataNascimento = new JTextField(10);
    
        lblCargo = new JLabel("Cargo:");
        cbxCargo = new JComboBox();    
        modeloComboCargo = new DefaultComboBoxModel(new Object[]{"Selecione"});
        cbxCargo.setModel(modeloComboCargo);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblCPF, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(txfCPF, posicionador);
                
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblNome, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(txfNome, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblSenha, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(psfSenha, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblNumero_ctps, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(txfNumero_ctps, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblDataAdmissao, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(txfDataAdmissao, posicionador); 
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblDataNascimento, posicionador);
 
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(txfDataNascimento, posicionador);  
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblCargo, posicionador);
 
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(cbxCargo, posicionador); 
        
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
            
            pnlFuncionario.showTela("tela_funcionario_listagem");
            
        } else if(ae.getActionCommand().equals(btnSalvar.getActionCommand())){
            
            Funcionario f = getFuncionarioFormulario();//recupera os dados do formulario
            
            if(f != null){
                try {
                    pnlFuncionario.getControle().getConexaoJDBC().persist(f);
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlFuncionario.showTela("tela_funcionario_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Jogador! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else{
                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
    }
    
    
    
}
