
package br.edu.ifsul.bcc.lpoo.om.gui.defaultclass;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.util.Collection;
import java.util.Vector;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author telmo
 */
public class JPanelDefaultListagem extends JPanel implements ActionListener{
    
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
    private GridBagConstraints posicionador;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnRemover;
    private FlowLayout layoutFlowSul;
    
    private BorderLayout layoutBorder;
    
    public Controle controle;
    public JPanelDefault pnlDefault;
    static public int numEntity = -1;
    static public String[][] N = {
        { "Código", "Descrição" }, // Cargo
        { "Código", "Nome", "Fornecedor", "Valor (R$)" }, // Peça
        { "Código", "Descrição" }, // Curso
        { "Código", "Descrição", "Tempo estimado", "Valor (R$)"} // Mão-de-Obra
    };

    public JPanelDefaultListagem(Controle controle, JPanelDefault pnlDefault) {
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
        
        String[] Nv = N[numEntity];
        modeloTabela = new DefaultTableModel(Nv, 0);
        tabela.setModel(modeloTabela);
        
        DefaultTableModel model =  (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        
        
        
        JPanelDefaultFormulario.pnlCentro.removeAll();
        JPanelDefaultFormulario.lbl = new ArrayList<JLabel>();
        JPanelDefaultFormulario.txf = new ArrayList<JTextField>();
        
        for(int i = 0; i < Nv.length; i++) {
            JPanelDefaultFormulario.lbl.add(new JLabel(Nv[i]));
            JPanelDefaultFormulario.txf.add(new JTextField(20));
            
            posicionador = new GridBagConstraints();
            posicionador.gridy = i;
            posicionador.gridx = 0;
            posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
            JPanelDefaultFormulario.pnlCentro.add(JPanelDefaultFormulario.lbl.get(i), posicionador);
            
            posicionador = new GridBagConstraints();
            posicionador.gridy = i;
            posicionador.gridx = 1;
            posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
            JPanelDefaultFormulario.pnlCentro.add(JPanelDefaultFormulario.txf.get(i), posicionador);
        }
        
        
        switch(numEntity) {
            case 0: // Cargo
                Collection<Cargo> listCargo = controle.getConexaoJDBC().listCargo();
                for(Cargo C : listCargo) model.addRow(new Object[]{C.getId(), C.getDescricao()});
                break;
                
            case 1: // Peça
                Collection<Peca> listPeca = controle.getConexaoJDBC().listPeca();
                for(Peca P : listPeca) model.addRow(new Object[]{P.getId(), P.getNome(), P.getFornecedor(), P.getValor()});
                break;
                
            case 2: // Curso
                Collection<Curso> listCurso = controle.getConexaoJDBC().listCurso();
                for(Curso C : listCurso) model.addRow(new Object[]{C.getId(), C.getDescricao()});
                break;
                
            case 3: // Mão-de-Obra
                Collection<MaoObra> listMO = controle.getConexaoJDBC().listMaoObra();
                for(MaoObra M : listMO) model.addRow(new Object[]{M.getId(), M.getDescricao(), M.getTempoEstimado(), M.getValor()});
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getActionCommand().equals(btnNovo.getActionCommand())){
            
            pnlDefault.showTela("tela_default_formulario");
            
        } else if(ae.getActionCommand().equals(btnEditar.getActionCommand())){
            
            int indice = tabela.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){
                DefaultTableModel model =  (DefaultTableModel) tabela.getModel();
                Vector linha = (Vector) model.getDataVector().get(indice);
                Funcionario f = (Funcionario) linha.get(0);
                pnlDefault.showTela("tela_default_formulario");
            } else{
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } else if(ae.getActionCommand().equals(btnRemover.getActionCommand())){
            
            int indice = tabela.getSelectedRow();//recupera a linha selecionada
            if(indice >= 0){

                DefaultTableModel model = (DefaultTableModel) tabela.getModel(); //recuperacao do modelo da table
                Object o = ((Vector) model.getDataVector().get(indice)).get(0);
                try {
                    PersistenciaJDBC P = pnlDefault.getControle().getConexaoJDBC();
                    
                    switch(numEntity) {
                        case 0: // Cargo
                            P.removeByInt("tb_cargo", "id", (int)o);
                            break;
                        case 1: // Peça
                            P.removeByInt("tb_peca", "id", (int)o);
                            break;
                        case 2: // Curso
                            P.removeByInt("tb_curso", "id", (int)o);
                            break;
                        case 3: // Mão-de-Obra
                            P.removeByInt("tb_maoobra", "id", (int)o);
                            break;
                    }
                    
                    JOptionPane.showMessageDialog(this, JPanelDefaultFormulario.Class[numEntity]
                            + " removido com sucesso!", "Exclusão", JOptionPane.INFORMATION_MESSAGE);
                    populaTable();
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
