package br.edu.ifsul.bcc.lpoo.om.test;
import java.text.*;
import java.util.*;
import br.edu.ifsul.bcc.lpoo.om.model.*;
import br.edu.ifsul.bcc.lpoo.om.model.dao.*;
import org.junit.Test;


public class TestPersistenceJPA {
    

    public Calendar setC(String DATA) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(DATA);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    
    @Test 
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        
        if(persistencia.conexaoAberta()){
            System.out.println("Conexão JPA com banco de dados aberta COM SUCESSO!!!");
            persistencia.fecharConexao();
            System.out.println("Conexão JPA encerrada...");
        } else{
            System.out.println("Conexão JPA com banco de dados NÃO foi estabelecida... :(");
        }
        
    }
    
    
    //@Test
    public void criarVeiculos() throws Exception {
        PersistenciaJDBC P = new PersistenciaJDBC();
        if(P.conexaoAberta()) {
            // Adicionado três veículos
            Veiculo V = new Veiculo("ABC1234", "Fiat Siena", 2010, setC("01/01/2023"));
            P.adVeiculo(V);
            
            V = new Veiculo("UIJ3156", "Ford Ka", 2012, setC("01/02/2016"));
            P.adVeiculo(V);
            
            V = new Veiculo("OBA5666", "Peugeot 408", 2015, setC("01/08/2023"));
            P.adVeiculo(V);
            
            V = new Veiculo("AMO1780", "Peugeot 308", 2014, setC("07/09/2022"));
            P.adVeiculo(V);
            
            P.fecharConexao();
        }
    }
    
}
