package br.edu.ifsul.bcc.lpoo.om;

import br.edu.ifsul.bcc.lpoo.om.gui.JFramePrincipal;
import br.edu.ifsul.bcc.lpoo.om.gui.JMenuBarHome;
import br.edu.ifsul.bcc.lpoo.om.gui.JPanelHome;
import br.edu.ifsul.bcc.lpoo.om.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.bcc.lpoo.om.gui.funcionario.JPanelFuncionario;
import br.edu.ifsul.bcc.lpoo.om.gui.defaultclass.JPanelDefault;
import br.edu.ifsul.bcc.lpoo.om.gui.peca.JPanelPeca;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
import javax.swing.JOptionPane;



/**
 *
 * @author telmo
 */
public class Controle {
    
    private JFramePrincipal jframe;
    private PersistenciaJDBC conexaoJDBC;
    private JPanelAutenticacao telaAutenticacao;
    private JPanelFuncionario telaFuncionario;
    private JPanelDefault telaDefault;
    private JMenuBarHome menuBar;
    private JPanelHome  telaHome;
    private JPanelPeca telaPeca;
    

    public Controle() {
        
    }
    
    
    public void autenticar(String cpf, String senha) {
        try {
            Funcionario f =  conexaoJDBC.findFuncionario(cpf, senha);
            if(f != null){
                JOptionPane.showMessageDialog(telaAutenticacao, "Funcionário "+f.getCPF()+" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                 jframe.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                 jframe.showTela("tela_home");//muda a tela para o painel de boas vindas (home)
            } else{
                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println(cpf);
        } catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean conectarBD() throws Exception {
            conexaoJDBC = new PersistenciaJDBC();
            if(conexaoJDBC!= null){
                return conexaoJDBC.conexaoAberta();
            }
            return false;
    }
   
    protected void initComponents(){
         
         jframe = new JFramePrincipal();
         telaAutenticacao = new JPanelAutenticacao(this);
         telaHome = new JPanelHome(this);
         menuBar = new JMenuBarHome(this);
         telaFuncionario = new JPanelFuncionario(this);
         telaPeca = new JPanelPeca(this);
         telaDefault = new JPanelDefault(this);
         
         jframe.addTela(telaAutenticacao, "tela_autenticacao");
         jframe.addTela(telaHome, "tela_home");
         jframe.addTela(telaFuncionario, "tela_funcionario");
         jframe.addTela(telaPeca, "tela_peca");
         jframe.addTela(telaDefault, "tela_default");
         jframe.showTela("tela_autenticacao");
         jframe.setVisible(true);
    }
    
    public void showTela(String nomeTela){
         
        //para cada nova tela de CRUD adicionar um elseif
        
         /*if(nomeTela.equals("tela_funcionario")){
            telaFuncionario.showTela("tela_funcionario_listagem");      
         } else if(nomeTela.equals("tela_peca")){
            telaPeca.showTela("tela_peca_listagem");
         }*/
         if(nomeTela.equals("tela_default")) {
             telaDefault.showTela("tela_default_listagem");
         }
         jframe.showTela(nomeTela);
         
    }

    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
    
    
}
