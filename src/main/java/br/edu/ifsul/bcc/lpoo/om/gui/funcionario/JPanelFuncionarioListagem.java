
package br.edu.ifsul.bcc.lpoo.om.gui.funcionario;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author telmo
 */
public class JPanelFuncionarioListagem extends JPanel implements ActionListener{
    
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txtFiltro;
    private JButton btnFiltro;
    private FlowLayout layoutFlowNorte;
    
    private JPanel pnlCentro;
    private JScrollPane scpCentro;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private BorderLayout layoutBorderCentro;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnRemover;
    private FlowLayout layoutFlowSul;
    
    private BorderLayout layoutBorder;
    
    public Controle controle;
    public JPanelFuncionario pnlDefault;

    public JPanelFuncionarioListagem(Controle controle, JPanelFuncionario pnlFuncionario) {
        this.controle = controle;
        this.pnlDefault = pnlDefault;
        
       initComponents();
    }
    
    private void initComponents(){
        layoutBorder = new BorderLayout();
        this.setLayout(layoutBorder);
        
        pnlCentro = new JPanel();
        layoutBorderCentro = new BorderLayout();
        pnlCentro.setLayout(layoutBorderCentro);
        tabela = new JTable();
        
        modeloTabela = new DefaultTableModel(
          new String[]{"CPF", "Nome"}, 0);
          
        tabela.setModel(modeloTabela);
        
        scpCentro = new JScrollPane();
        scpCentro.setViewportView(tabela);
        pnlCentro.add(scpCentro, BorderLayout.CENTER);
        
        this.add(pnlCentro, BorderLayout.CENTER);
        
        pnlSul = new JPanel();
        layoutFlowSul = new FlowLayout();
        pnlSul.setLayout(layoutFlowSul);
        
        btnNovo = new JButton("Novo");
        btnNovo.setActionCommand("comando_novo");
        btnNovo.addActionListener(this);
        pnlSul.add(btnNovo);
        
        btnEditar = new JButton("Editar");
        btnEditar.setActionCommand("comando_editar");
        btnEditar.addActionListener(this);
        pnlSul.add(btnEditar);
        
        btnRemover = new JButton("Remover");
        btnRemover.setActionCommand("comando_remover");
        btnRemover.addActionListener(this);           
        pnlSul.add(btnRemover);
        
        this.add(pnlSul, BorderLayout.SOUTH);
    }
    
    
    public void populaTable() throws Exception{
        
        DefaultTableModel model =  (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        
        Collection<Funcionario> listFuncionarios =  controle.getConexaoJDBC().listFuncionario();
        for(Funcionario f : listFuncionarios){
            model.addRow(new Object[]{f.getCPF(), f.getNome()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getActionCommand().equals(btnNovo.getActionCommand())){
            
            pnlDefault.showTela("tela_funcionario_formulario");
            pnlDefault.getFormulario().setFuncionarioFormulario(null); //limpando o formulário.
            
        } else if(ae.getActionCommand().equals(btnEditar.getActionCommand())){
            
            /*int indice = tabela.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){
                DefaultTableModel model =  (DefaultTableModel) tabela.getModel(); //recuperacao do modelo da table
                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada
                Funcionario f = (Funcionario) linha.get(0); //model.addRow(new Object[]{u, u.getNome(), ...
                pnlDefault.showTela("tela_funcionario_formulario");
                pnlDefault.getFormulario().setFuncionarioFormulario(f); 
            } else{
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }*/
            
            
        } else if(ae.getActionCommand().equals(btnRemover.getActionCommand())){
            
            int indice = tabela.getSelectedRow();//recupera a linha selecionada
            if(indice >= 0){

                DefaultTableModel model = (DefaultTableModel) tabela.getModel(); //recuperacao do modelo da table
                Object o = ((Vector) model.getDataVector().get(indice)).get(0);
                try {
                    PersistenciaJDBC P = pnlDefault.getControle().getConexaoJDBC();
                    
                    if(o instanceof Cargo) P.removeByInt("tb_cargo", "id", ((Cargo)o).getId());
                    else if(o instanceof Curso) P.removeByInt("tb_curso", "id", ((Curso)o).getId());
                    else if(o instanceof MaoObra) P.removeByInt("tb_maoobra", "id", ((MaoObra)o).getId());
                    else if(o instanceof Peca) P.removeByInt("tb_peca", "id", ((Peca)o).getId());
                    
                    JOptionPane.showMessageDialog(this, "Removido com sucesso!", "Funcionario", JOptionPane.INFORMATION_MESSAGE);
                    populaTable(); //refresh na tabela
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao efetuar remoção", "Erro :/", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }                        

            } else{
                JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
    }
    
    
}
