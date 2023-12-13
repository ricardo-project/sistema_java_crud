package br.edu.ifsul.bcc.lpoo.om.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import br.edu.ifsul.bcc.lpoo.om.model.*;

public class PersistenciaJDBC implements InterfacePersistencia {

    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "ricardo";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_om_lpoo_20232";
    private Connection con = null;

    public String CtoS(Calendar data) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(data);
    }
    
    public PersistenciaJDBC() throws Exception{
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA); 
    }

    public Boolean conexaoAberta() {
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        } return false;
    }

    @Override
    public void fecharConexao() {        
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        } catch(SQLException e){
            e.printStackTrace();//gera uma pilha de erro na saida.
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void persist(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void remover(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    /* FUNÇÕES DA PROVA */
    public void adVeiculo(Veiculo V) throws Exception {
        PreparedStatement ps  = this.con.prepareStatement("insert into tb_veiculo values (?, ?, ?, ?)");
        ps.setString(1, V.getPlaca());
        ps.setInt(2, V.getAno());
        ps.setDate(3, new java.sql.Date(V.getData().getTimeInMillis()));
        ps.setString(4, V.getModelo());
        ps.execute();
        ps.close();
    }
    
    public void adCliente(Cliente C) throws Exception {
        // Adiciona a pessoa primeiro
        PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa values (?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, "C");
        ps.setString(2, C.getCPF());
        ps.setString(3, C.getCEP());
        ps.setString(4, C.getComplemento());
        ps.setDate(5, new java.sql.Date(C.getDataNascimento().getTimeInMillis()));
        ps.setString(6, C.getNome());
        ps.setString(7, C.getNumero());
        ps.setString(8, C.getSenha());
        ps.execute();
        
        // Depois, adiciona cliente e relaciona com pessoa
        PreparedStatement psC = this.con.prepareStatement("insert into tb_cliente(cpf, observacoes) values (?, ?)");
        psC.setString(1, C.getCPF());
        psC.setString(2, C.getObs());
        psC.execute();
        
        // Depois, adiciona as relações entre cliente e veículo na tabela
        for(Veiculo v : C.getVeiculos()) {
            PreparedStatement psCV = this.con.prepareStatement("insert into tb_cliente_veiculo values (?, ?)");
            psCV.setString(1, C.getCPF());
            psCV.setString(2, v.getPlaca());
            psCV.execute();
            psCV.close();
        }
        
        ps.close();
        psC.close();
    }
    
    public void removerCliente(String CPF) throws Exception { // Remover funcionário e tudo associado a ele (pessoa, cursos)
        removeByString("tb_cliente_veiculo", "funcionario_cpf", CPF);
        removeByString("tb_cliente", "cpf", CPF);
        removeByString("tb_pessoa", "cpf", CPF);
    }
    
    public Veiculo findVeiculo(String placa) throws Exception {
        PreparedStatement ps = this.con.prepareStatement("select * from tb_veiculo where placa = ?");
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            Veiculo V = new Veiculo(placa, rs.getString("modelo"), Integer.getInteger(rs.getString("ano")),
                    DtoC(rs.getDate("data_ultimo_servico")));
            return V;
        }
        ps.close();
        rs.close();
        return null;
    }
    
    public List<Veiculo> listVeiculo() throws Exception {
        
        List<Veiculo> lista;
        PreparedStatement ps = this.con.prepareStatement("select * from tb_veiculo");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while(rs.next()){
            Veiculo v = new Veiculo(rs.getString("placa"), rs.getString("modelo"),
                    rs.getInt("ano"), DtoC(rs.getDate("data_ultimo_servico")));
            lista.add(v);
        }
        return lista;
    }
    
    public List<Veiculo> listClienteVeiculo(String CPF) throws Exception {
        
        List<Veiculo> listVeiculos = new ArrayList<Veiculo>();
        List<Veiculo> veiculos = listVeiculo();
        
        PreparedStatement psF = this.con.prepareStatement("select * from tb_cliente_veiculo where cliente_cpf = ?");
        psF.setString(1, CPF);
        ResultSet rsC = psF.executeQuery();
                    
        while(rsC.next()) {
            String idV = rsC.getString("veiculo_id");
            for(Veiculo vei : veiculos) {
                if(idV.equals(vei.getPlaca())) {
                    listVeiculos.add(vei);
                    break;
                }
            }
        }
        
        return listVeiculos;
    }
    
    public List<Cliente> listCliente() throws Exception {
        List<Cliente> lista;
        PreparedStatement ps = this.con.prepareStatement("select P.nome, P.data_nascimento, P.cpf, P.cep, P.complemento, P.numero, P.senha, C.observacoes from\n"
        + "tb_cliente C, tb_pessoa P where P.cpf = C.cpf");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        
        while(rs.next()){
            String CPF = rs.getString("cpf");
                    
            Cliente F = new Cliente("C",
                    CPF,
                    rs.getString("nome"),
                    rs.getString("senha"),
                    DtoC(rs.getDate("data_nascimento")),
                    rs.getString("cep"),
                    rs.getString("complemento"),
                    rs.getString("numero"),
                    rs.getString("observacoes"),
                    listClienteVeiculo(CPF));
            lista.add(F);
        }
        return lista;
    }
    
    
    
    
    
    // ******************************
    // FUNÇÕES DE EXIBIR ITENS (LIST)
    // ******************************
    public List<Cargo> listCargo() throws Exception {
        
        List<Cargo> lista;
        PreparedStatement ps = this.con.prepareStatement("select id, descricao from tb_cargo");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while(rs.next()){
            Cargo c = new Cargo(rs.getInt("id"), rs.getString("descricao"));
            lista.add(c);
        }
        return lista;
    }
    
    public List<Curso> listCurso() throws Exception {
        
        List<Curso> lista;
        PreparedStatement ps = this.con.prepareStatement("select * from tb_curso");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while(rs.next()){
            Curso c = new Curso(rs.getInt("id"),
                    rs.getString("descricao"));
                    //, DtoC(rs.getDate("dt_conclusao")),
                    //rs.getInt("cargahoraria"));
            lista.add(c);
        }
        return lista;
    }
    
    public List<MaoObra> listMaoObra() throws Exception {
        
        List<MaoObra> lista;
        PreparedStatement ps = this.con.prepareStatement("select * from tb_maoobra");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while(rs.next()){
            MaoObra c = new MaoObra(rs.getInt("id"), rs.getString("descricao"),
                    rs.getTimestamp("tempo_estimado_execucao"), rs.getFloat("valor"));
            lista.add(c);
        }
        return lista;
    }
    
    public List<Peca> listPeca() throws Exception {
        
        List<Peca> lista;
        PreparedStatement ps = this.con.prepareStatement("select * from tb_peca");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        while(rs.next()){
            Peca c = new Peca(rs.getInt("id"), rs.getString("nome"),
                    rs.getFloat("valor"), rs.getString("fornecedor"));
            lista.add(c);
        }
        return lista;
    }
    
    public List<Curso> listFuncionarioCurso(String CPF) throws Exception {
        
        List<Curso> listCurso = new ArrayList<Curso>();
        List<Curso> cursos = listCurso();
        
        PreparedStatement psF = this.con.prepareStatement("select * from tb_funcionario_curso where funcionario_cpf = ?");
        psF.setString(1, CPF);
        ResultSet rsC = psF.executeQuery();
                    
        while(rsC.next()) {
            int idC = rsC.getInt("curso_id");
            for(Curso cur : cursos) {
                if(cur.getId() == idC) {
                    listCurso.add(cur);
                    break;
                }
            }
        }
        
        return listCurso;
    }
    
    public List<Funcionario> listFuncionario() throws Exception {
        
        List<Funcionario> lista;
        PreparedStatement ps = this.con.prepareStatement("select P.nome, P.data_nascimento, P.cpf, P.cep, P.complemento, P.numero, P.senha, F.data_admmissao, F.data_demissao, F.numero_ctps, F.cargo_id from\n"
        + "tb_funcionario F, tb_pessoa P where P.cpf = F.cpf;");
        ResultSet rs = ps.executeQuery();
        lista = new ArrayList();
        
        List<Cargo> cargos = listCargo();
        
        while(rs.next()){
            int cargoId = rs.getInt("cargo_id");
            for(Cargo c : cargos) {
                if(c.getId() == cargoId) {
                    String CPF = rs.getString("cpf");
                    
                    Funcionario F = new Funcionario("F",
                    CPF,
                    rs.getString("nome"),
                    rs.getString("senha"),
                    DtoC(rs.getDate("data_nascimento")),
                    rs.getString("cep"),
                    rs.getString("complemento"),
                    rs.getString("numero"),
                    rs.getString("numero_ctps"),
                    DtoC(rs.getDate("data_admmissao")),
                    DtoC(rs.getDate("data_demissao")), c,
                    listFuncionarioCurso(CPF));
                    lista.add(F);
                    break;
                }
            }
        }
        return lista;
    }
    
    
    // ***************************************
    // FUNÇÕES DE ADICIONAR OU ATUALIZAR ITENS
    // ***************************************
    public void adOrUpdateCargo(Cargo C, Boolean edit) throws Exception {
        
        PreparedStatement ps;
        if(edit) {
            ps = this.con.prepareStatement("update tb_cargo set descricao = ? where id = ?");
            ps.setInt(2, C.getId());
            ps.setString(1, C.getDescricao());
        } else {
            ps = this.con.prepareStatement("insert into tb_cargo values (?, ?)");
            ps.setInt(1, C.getId());
            ps.setString(2, C.getDescricao());
        }
        ps.execute();
        
        ps.close();
    }
    
    public void adOrUpdateCurso(Curso C, Boolean edit) throws Exception {
        PreparedStatement ps;
        int numAdd = 0;
        if(!edit) {
            ps = this.con.prepareStatement("insert into tb_curso values (?, ?)");
            ps.setInt(1, C.getId());
            numAdd++;
        } else {
            ps = this.con.prepareStatement("update tb_curso set descricao = ? where id = ?");
            ps.setInt(4, C.getId());
        }
        ps.setString((1 + numAdd), C.getDescricao());
        ps.execute();
    }
    
    public void adOrUpdatePeca(Peca P, Boolean edit) throws Exception {
        
        PreparedStatement ps;
        int numAdd = 0;
        if(!edit) {
            ps = this.con.prepareStatement("insert into tb_peca values (?, ?, ?, ?)");
            ps.setInt(1, P.getId());
            numAdd++;
        } else {
            ps = this.con.prepareStatement("update tb_pessoa set fornecedor = ?, nome = ?, valor = ? where id = ?");
            ps.setInt(4, P.getId());
        }
        ps.setString(1 + numAdd, P.getFornecedor());
        ps.setString(2 + numAdd, P.getNome());
        ps.setFloat(3 + numAdd, P.getValor());
        
        ps.execute();
        ps.close();
    }
    
    public void adOrUpdateMaoObra(MaoObra M, Boolean edit) throws Exception {
        
        PreparedStatement ps;
        int numAdd = 0;
        if(!edit) {
            ps = this.con.prepareStatement("insert into tb_maoobra values (?, ?, ?, ?)");
            ps.setInt(1, M.getId());
            numAdd++;
        } else {
            ps = this.con.prepareStatement("update tb_maoobra set descricao = ?, tempo_estimado_execucao = ?, valor = ? where id = ?");
            ps.setInt(4, M.getId());
        }
        ps.setString(1 + numAdd, M.getDescricao());
        ps.setDate(2 + numAdd, new java.sql.Date(M.getTempoEstimado().getTime()));
        ps.setFloat(3 + numAdd, M.getValor());
        
        ps.execute();
        ps.close();
    }
    
    public void adOrUpdateVeiculo(Veiculo V, Boolean edit) throws Exception {
        
        PreparedStatement ps;
        int numAdd = 0;
        if(!edit) {
            ps = this.con.prepareStatement("insert into tb_veiculo values (?, ?, ?, ?)");
            ps.setString(1, V.getPlaca());
            numAdd++;
        } else {
            ps = this.con.prepareStatement("update tb_veiculo set ano = ?, data_ultimo_servico = ?, modelo = ? where placa = ?");
            ps.setString(4, V.getPlaca());
        }
        ps.setInt(1 + numAdd, V.getAno());
        ps.setDate(2 + numAdd, (java.sql.Date)V.getData().getTime());
        ps.setString(3 + numAdd, V.getModelo());
        
        ps.execute();
        ps.close();
    }
    
    public void adOrUpdateFuncionario(Funcionario F, Boolean edit) throws Exception {
        // Adiciona a pessoa primeiro
        PreparedStatement ps;
        int numAdd = 0;
        if(!edit) {
            ps = this.con.prepareStatement("insert into tb_pessoa values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "F");
            ps.setString(2, F.getCPF());
            numAdd++;
        } else {
            ps = this.con.prepareStatement("update tb_pessoa set cep = ?, complemento = ?, data_nascimento = ?, nome = ?, numero = ?, senha = ? where cpf = ?");
            ps.setString(7, F.getCPF());
        }
        ps.setString(1 + 2*numAdd, F.getCEP());
        ps.setString(2 + 2*numAdd, F.getComplemento());
        ps.setDate(3 + 2*numAdd, new java.sql.Date(F.getDataNascimento().getTimeInMillis()));
        ps.setString(4 + 2*numAdd, F.getNome());
        ps.setString(5 + 2*numAdd, F.getNumero());
        ps.setString(6 + 2*numAdd, F.getSenha());
        ps.execute();
        
        // Depois, adiciona funcionário e relaciona com pessoa
        PreparedStatement psF;
        if(!edit) {
            psF = this.con.prepareStatement("insert into tb_funcionario(cpf, data_admmissao, data_demissao, numero_ctps, cargo_id) values (?, ?, ?, ?, ?)");
            psF.setString(1, F.getCPF());
        } else {
            psF = this.con.prepareStatement("update tb_funcionario set data_admmissao = ?, data_demissao = ?, numero_ctps = ?, cargo_id = ? where cpf = ?");
            psF.setString(5, F.getCPF());
        }
        psF.setDate(1 + numAdd, new java.sql.Date(F.getDataAdmissao().getTimeInMillis()));
        psF.setDate(2 + numAdd, new java.sql.Date(F.getDataDemissao().getTimeInMillis()));
        psF.setString(3 + numAdd, F.getCTPS());
        psF.setInt(4 + numAdd, F.getCargo().getId());
        psF.execute();
        
        // Depois, adiciona as relações entre funcionário e curso na tabela
        if(edit) removeByString("tb_funcionario_curso", "funcionario_cpf", F.getCPF()); // Se funcionário estiver sendo editado, remover, em um primeiro momento, todas as relações iniciais de funcionário/curso
        for(Curso c : F.getCursos()) {
            PreparedStatement psFC = this.con.prepareStatement("insert into tb_funcionario_curso values(?, ?)");
            psFC.setString(1, F.getCPF());
            psFC.setInt(2, c.getId());
            psFC.execute();
            psFC.close();
        }
        
        ps.close();
        psF.close();
    }
    
    
    // ********************************
    // FUNÇÕES DE BUSCA DE ITENS (FIND)
    // ********************************
    public Cargo findCargo(int ID) throws Exception {
        PreparedStatement ps = this.con.prepareStatement("select * from tb_cargo where id = ?");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            Cargo C = new Cargo(ID, rs.getString("descricao"));
            return C;
        }
        ps.close();
        rs.close();
        return null;
    }
    
    public Curso findCurso(int ID) throws Exception {
        PreparedStatement ps = this.con.prepareStatement("select * from tb_curso where id = ?");
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            Curso C = new Curso(ID, rs.getString("descricao"));//, DtoC(rs.getDate("dt_conclusao")), rs.getInt("cargahoraria"));
            return C;
        }
        ps.close();
        rs.close();
        return null;
    }
    
    public Funcionario findFuncionario(String CPF, String senha) throws Exception {
        PreparedStatement ps = this.con.prepareStatement("select P.nome, P.data_nascimento, P.cpf, P.cep, P.complemento, P.numero, F.data_admmissao, F.data_demissao, F.numero_ctps, F.cargo_id from\n"
        + "tb_funcionario F, tb_pessoa P where P.cpf = F.cpf and P.cpf = ? and P.senha = ?");
        ps.setString(1, CPF);
        ps.setString(2, senha);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            Funcionario F = new Funcionario("F", CPF, rs.getString("nome"), senha, DtoC(rs.getDate("data_nascimento")), rs.getString("cep"),
    rs.getString("complemento"), rs.getString("numero"), rs.getString("numero_ctps"), DtoC(rs.getDate("data_admmissao")),
            DtoC(rs.getDate("data_demissao")), findCargo(rs.getInt("cargo_id")), listFuncionarioCurso(CPF));
            return F;
        }
        ps.close();
        rs.close();
        return null;
    }
    
    
    // ******************
    // FUNÇÕES DE REMOÇÃO
    // ******************
    
    public void removeAllTable(String name_table, String condit) throws Exception {
        PreparedStatement ps = this.con.prepareStatement("delete from " + name_table + condit);
        ps.execute();
        ps.close();
    }
    
    public void removeByInt(String name_table, String name_PK, int ID) throws Exception { // Funções para remover elementos de tabelas cujo PK é inteiro (Ex.: Cargo, Curso)
        PreparedStatement ps = this.con.prepareStatement("delete from " + name_table + " where " + name_PK + " = ?");
        ps.setInt(1, ID);
        ps.execute();
        ps.close();
    }
    
    public void removeByString(String name_table, String name_PK, String ID) throws Exception { // Funções para remover elementos de tabelas cujo PK é texto (Ex.: Funcionário, Cliente)
        PreparedStatement ps = this.con.prepareStatement("delete from " + name_table + " where " + name_PK + " = ?");
        ps.setString(1, ID);
        ps.execute();
        ps.close();
    }
    
    public void removerFuncionario(String CPF) throws Exception { // Remover funcionário e tudo associado a ele (pessoa, cursos)
        removeByString("tb_funcionario_curso", "funcionario_cpf", CPF);
        removeByString("tb_funcionario", "cpf", CPF);
        removeByString("tb_pessoa", "cpf", CPF);
    }
    
    
    // **********************
    // FUNÇÕES COMPLEMENTARES
    // **********************
    public Calendar DtoC(Date data) {
        Calendar novo = Calendar.getInstance();
        novo.setTime(data);
        return novo;
    }
}
