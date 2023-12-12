package br.edu.ifsul.bcc.lpoo.om.test;
import java.util.*;
import java.text.*;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.*;
import org.junit.Test;

public class TestPersistenceJDBC {
    
    // *******************************
    // FUNÇÕES DE CONFIGURAÇÃO DE DATA
    // *******************************
    // A data será um parâmetro do tipo DD/MM/AAAA
    public Calendar setC(String DATA) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(DATA);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    public String CtoS(Calendar data) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(data);
    }
    
    
    
    
    
    /* FUNÇÃO DO TRABALHO */    
    @Test
    public void retornarClientes() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            List<Cliente> todos = P.listCliente();
            
            if(todos.size() > 0) {
                for(Cliente c : todos) System.out.println(c.mostrarCliente());
                
                //System.out.println();
                P.removeAllTable("tb_cliente_veiculo", "");
                P.removeAllTable("tb_cliente", "");
                P.removeAllTable("tb_pessoa", " where tipo = 'C'");
            } else {
                // PRIMEIRO CLIENTE
                List<Veiculo> listaC = new ArrayList<Veiculo>();
                listaC.add(P.findVeiculo("ABC1234"));
                listaC.add(P.findVeiculo("UIJ3156"));
                
                Cliente C = new Cliente("C", "27521056065", "Ricardo", "rick", setC("26/04/2004"), "12561256", "", "808080", "Cliente bom", listaC);
                P.adCliente(C);
                
                
                // SEGUNDO CLIENTE
                listaC = new ArrayList<Veiculo>();
                listaC.add(P.findVeiculo("AMO1780"));
                C = new Cliente("C", "62730513887", "Geraldo", "ge10", setC("24/07/2001"), "51723064", "", "909090", "Cliente desagradável", listaC);
                P.adCliente(C);
                
                
                // TERCEIRO CLIENTE
                C = new Cliente("C", "9417069812", "Maria", "mary", setC("09/12/1999"), "67108182",
                        "", "575757", "", listaC);
                P.adCliente(C);
                
                System.out.println(">> Três clientes foram adicionados COM SUCESSO!!!");
            }
            
            P.fecharConexao();
        }
    }
    
    
    
    
    
    // ************************
    // FUNÇÕES BÁSICAS DE TESTE
    // ************************
    
    //@Test
    public void testConexao() throws Exception  {
        
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            System.out.println("Conexão JDBC com banco de dados aberta COM SUCESSO!!!");
            P.fecharConexao();
            System.out.println("Conexão JDBC encerrada...");
        } else {
            System.out.println("Conexão JDBC com banco de dados NÃO foi estabelecida... :(");
        }  
    }
    
    //@Test
    public void testAdOrUpdateCargo() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            Cargo C = new Cargo(22, "Ameixa");
            P.adOrUpdateCargo(C, false);
            C = new Cargo(13, "Abóbora");
            P.adOrUpdateCargo(C, false);
            C = new Cargo(30, "Carlos");
            P.adOrUpdateCargo(C, false);
            P.fecharConexao();
        }
    }
    
    //@Test
    public void testeAdCurso() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            Curso C = new Curso(3, "Arquitetura Grega");//, setC("18/00/2050"), 500);
            P.adOrUpdateCurso(C, false);
            C = new Curso(2, "Engenharia Civil");//, setC("18/01/2050"), 300);
            P.adOrUpdateCurso(C, false);
            C = new Curso(1, "Arquitetura Grega");//, setC("18/02/2050"), 400);
            P.adOrUpdateCurso(C, false);
            P.fecharConexao();
        }
    }
    
    //@Test
    public void testeAdFuncionario() throws Exception {       
        PersistenciaJDBC P = new PersistenciaJDBC();
        
        if(P.conexaoAberta()) {
            // PRIMEIRO FUNCIONÁRIO
            List<Curso> FC = new ArrayList<Curso>();
            FC.add(P.findCurso(1)); // Ciência da Computaria
            FC.add(P.findCurso(3)); // Arquitetura Grega
            Funcionario F = new Funcionario("F", "12345678901",
            "Joselmo", "teste", setC("01/01/2000"), "12345678", "", "808080", "2",
      setC("28/09/2023"), setC("29/09/2023"), P.findCargo(22), FC);
            P.adOrUpdateFuncionario(F, false);
            
            // SEGUNDO FUNCIONÁRIO
            FC = new ArrayList<Curso>();
            FC.add(P.findCurso(3)); // Arquitetura Grega
            F = new Funcionario("T", "89078967813", "Caxumba", "bolao", setC("31/03/2001"), "78901000", "",
                    "2", "808080", setC("26/06/2023"), setC("56/31/2002"), P.findCargo(13), FC);
            P.adOrUpdateFuncionario(F, false);
            
            P.fecharConexao();
        }
    }
    
    //@Test
    public void testUpdateItens() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            // Atualizar curso
            Curso C = new Curso(1, "Ciência da Computaria");//, setC("18/00/2025"), 500);
            P.adOrUpdateCurso(C, true);
            
            // Atualizar funcionário
            List<Curso> FC = new ArrayList<Curso>();
            FC.add(P.findCurso(2));
            FC.add(P.findCurso(3));
            Funcionario F = new Funcionario("F", "89078967813", "Caxumbeiro", "bolinho21", setC("31/03/2001"), "99010888", "Casa legal",
                    "1", "808080", setC("26/06/2023"), setC("56/31/2002"), P.findCargo(13), FC);
            P.adOrUpdateFuncionario(F, true);
            
            P.fecharConexao();
        }
    }
    
    //@Test
    public void testFindItens() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            // Encontrar um cargo
            Cargo C = P.findCargo(13);
            System.out.println("Cargo encontrado: " + C.getDescricao());
            
            // Encontrar um curso
            Curso Cu = P.findCurso(22);
            System.out.println("Curso encontrado: " + Cu.mostrarCurso());
            
            // Encontrar um funcionário
            Funcionario F = P.findFuncionario("12345678901", "");
            if(F == null) System.out.println("Funcionário NÃO encontrado... :(");
            else System.out.println(F.mostrarFuncionario());
            
            P.fecharConexao();
        }
    }
    
    //@Test
    public void testListItens() throws Exception {
        
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()){
            // Listar cargos
            System.out.println("\n\n*** LISTAR CARGOS ***\n");
            List<Cargo> lista = P.listCargo();
            if(!lista.isEmpty()){
                for(Cargo c : lista) System.out.println(c.getDescricao() + " (Id: " + c.getId() + ")");
            } else System.out.println("Não tem cargos registrados... ),:");
            
            
            // Listar cargos
            System.out.println("\n\n*** LISTAR CURSOS ***\n");
            List<Curso> listaC = P.listCurso();
            if(!lista.isEmpty()){
                for(Curso c : listaC) System.out.println(c.mostrarCurso());
            } else System.out.println("Não tem cursos registrados... ),:");
            
            
            // Listar funcionários
            System.out.println("\n\n*** LISTAR FUNCIONÁRIOS ***\n");
            List<Funcionario> listaF = P.listFuncionario();
            if(!listaF.isEmpty()){
                for(Funcionario f : listaF){
                    System.out.println(f.mostrarFuncionario());
                }
            } else System.out.println("Não tem funcionários registrados... ),:");
            
            P.fecharConexao();
        }
    }
    
    //@Test
    public void removerTudo() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            P.removeAllTable("tb_funcionario_curso", "");
            P.removeAllTable("tb_funcionario", "");
            P.removeAllTable("tb_pessoa", "");
            P.removeAllTable("tb_cargo", "");
            P.removeAllTable("tb_curso", "");
            System.out.println("Todos os dados das tabelas removidos com sucesso!!");
        }
    }
    
    /*/@Test
    public void testListPersistenciaFuncionario() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "UTF8"));
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Funcionario> lista = persistencia.listFuncionario();
            if(!lista.isEmpty()){
                for(Funcionario f : lista){
                    System.out.println(f.mostrarFuncionario());
                }
            } else{
                System.out.println("Não tem funcionários registrados... ),:");
            }
            persistencia.fecharConexao();
        } else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }*/
}
