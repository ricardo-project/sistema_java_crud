
package br.edu.ifsul.bcc.lpoo.om.gui.defaultclass;
import br.edu.ifsul.bcc.lpoo.om.gui.funcionario.*;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
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
    public JPanelDefault pnlDefault;
    
    static public ArrayList<JLabel> lbl;
    static public ArrayList<JTextField> txf;
    
    private JPanel pnlSul;
    private JButton btnSalvar;
    private JButton btnCancelar;
 
    public JPanelDefaultFormulario(Controle controle, JPanelDefault pnlDefault) {
        this.controle = controle;
        this.pnlDefault = pnlDefault;
        
        initComponents();
    }
    
    static public String[] Class = { "Cargo", "Peça", "Curso", "Mão-de-Obra", "Veículo" };
    
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
    public Object getFormulario() {
        
        List<String> T = new ArrayList();
        for(int i = 0; i < txf.size(); i++) T.add(txf.get(i).getText().trim());
        
        
        switch(JPanelDefaultListagem.numEntity) {
            case 0: // Cargo
                if(T.get(0).length() > 0 && T.get(1).length() > 0) {
                    return new Cargo(Integer.valueOf(T.get(0)), T.get(1));
                }
                break;
                
            case 1: // Peça
                if(
                    T.get(0).length() > 0 &&
                    T.get(1).length() > 0 &&
                    T.get(2).length() > 0 &&
                    T.get(3).length() > 0) {
                    return new Peca(
                        Integer.parseInt(T.get(0)), T.get(1),
                        Float.parseFloat(T.get(3)), T.get(2));
                }
                break;
                
            case 2: // Curso
                if(T.get(0).length() > 0 && T.get(1).length() > 0) {
                    return new Curso(Integer.valueOf(T.get(0)), T.get(1));
                }
                break;
            case 3: // Mão-de-Obra
                if(T.get(0).length() > 0 &&
                    T.get(1).length() > 0 &&
                    T.get(2).length() > 0 &&
                    T.get(3).length() > 0) {
                    
                    try {
                    
                        return new MaoObra(
                        Integer.parseInt(T.get(0)), T.get(1),
                        (new SimpleDateFormat("dd-MM-yyyy")).parse(T.get(2)),
                        Float.parseFloat(T.get(3)));
                        
                    } catch(ParseException p) { return null; }
                }
                break;
            case 4: // Veículo
                break;
        }
        
        return null;
    }
    
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
            
            pnlDefault.showTela("tela_default_listagem");
            
        } else if(ae.getActionCommand().equals(btnSalvar.getActionCommand())){
                
            Object o = getFormulario();
            if(o != null) {
                try {
                    
                    PersistenciaJDBC P = pnlDefault.getControle().getConexaoJDBC();
                    if(o instanceof Cargo) P.adOrUpdateCargo((Cargo)o, false);
                    else if(o instanceof Peca) P.adOrUpdatePeca((Peca)o, false);
                    else if(o instanceof Curso) P.adOrUpdateCurso((Curso)o, false);
                    else if(o instanceof MaoObra) P.adOrUpdateMaoObra((MaoObra)o, false);
                    else if(o instanceof Veiculo) P.adOrUpdateVeiculo((Veiculo)o, false);
                            
                    JOptionPane.showMessageDialog(this, "Uma linha de " +
                            Class[JPanelDefaultListagem.numEntity] + " armazenada com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlDefault.showTela("tela_default_listagem");
                    
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao enviar para banco de dados!", "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else JOptionPane.showMessageDialog(this, "Formulário INVÁLIDO!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    
    
}
