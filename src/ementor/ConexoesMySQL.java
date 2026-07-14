package ementor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class ConexoesMySQL {
    
    /* Seção de atributos/variáveis iniciais */
    private final String caminho = "localhost"; 
    private final String porta = "3306"; 
    private final String nome = "ementor-Plus"; 
    private final String usuario = "root"; 
    private final String senha = "admin"; 
    private final String fusoHorario = "?useTimezone=true&serverTimezone=UTC";
    private final String URL = "jdbc:mysql://" + caminho + ":" + porta + "/" + nome + fusoHorario; 
    
    private void registrarErroLog(String codigo, String descricao) {
        try (FileWriter fw = new FileWriter("erros.dat", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            out.println("[" + dataHora + "] CÓDIGO: " + codigo + " | DESCRIÇÃO: " + descricao);
            
        } catch (IOException e) {
            System.err.println("Erro crítico: Não foi possível gravar no arquivo erros.dat. " + e.getMessage());
        }
    }
    
    public Connection realizaConexaoMySQL() {
        try {
            return DriverManager.getConnection(URL, usuario, senha); 
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro de Conexão: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro de conexão: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            return null;
        }        
    }
    
    public void desconectaMySQL(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao desconectar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao desconectar: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }   
    }

    // ========================================================================
    // PESSOA - GRAVAR E ALTERAR

    public void inserePessoa(Pessoa pessoa) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sql = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pessoa.getCPF());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getDataNascimento()); 
            ps.setString(4, pessoa.getTelefone());
            ps.setString(5, pessoa.getRua());
            ps.setString(6, pessoa.getBairro());
            ps.setString(7, pessoa.getCidade());
            ps.setString(8, pessoa.getEstado());
            
            ps.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Pessoa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao inserir Pessoa: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Pessoa: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    // ========================================================================
    // ALUNO - GRAVAR, RECUPERAR E ALTERAR

    public void insereAluno(Aluno aluno) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAluno = "INSERT INTO Aluno (Matricula, Periodo, CPF_Pessoa, Codigo_Turma) VALUES (?, ?, ?, ?)";
        
        try {
            conexao.setAutoCommit(false); 
            
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno)) {
                
                psPessoa.setLong(1, aluno.getCPF());
                psPessoa.setString(2, aluno.getNome());
                psPessoa.setString(3, aluno.getDataNascimento()); 
                psPessoa.setString(4, aluno.getTelefone());
                psPessoa.setString(5, aluno.getRua());
                psPessoa.setString(6, aluno.getBairro());
                psPessoa.setString(7, aluno.getCidade());
                psPessoa.setString(8, aluno.getEstado());
                psPessoa.executeUpdate(); 
                
                psAluno.setLong(1, aluno.getMatricula());
                psAluno.setInt(2, aluno.getPeriodo());
                psAluno.setLong(3, aluno.getCPF()); 
                psAluno.setLong(4, aluno.getTurma()); 
                psAluno.executeUpdate();
                
                conexao.commit(); 
                JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {} 
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao inserir Aluno: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao inserir Aluno: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    public void alteraAluno(Aluno aluno) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, DataNascimento = ?, Telefone = ?, Rua = ?, Bairro = ?, Cidade = ?, Estado = ? WHERE CPF = ?";
        String sqlAluno = "UPDATE Aluno SET Periodo = ?, Codigo_Turma = ? WHERE Matricula = ?";

        try {
            conexao.setAutoCommit(false);

            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno)) {

                psPessoa.setString(1, aluno.getNome());
                psPessoa.setString(2, aluno.getDataNascimento());
                psPessoa.setString(3, aluno.getTelefone());
                psPessoa.setString(4, aluno.getRua());
                psPessoa.setString(5, aluno.getBairro());
                psPessoa.setString(6, aluno.getCidade());
                psPessoa.setString(7, aluno.getEstado());
                psPessoa.setLong(8, aluno.getCPF());
                psPessoa.executeUpdate();

                psAluno.setInt(1, aluno.getPeriodo());
                psAluno.setLong(2, aluno.getTurma());
                psAluno.setLong(3, aluno.getMatricula());
                psAluno.executeUpdate();

                conexao.commit();
                JOptionPane.showMessageDialog(null, "Dados do Aluno alterados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {}
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao alterar Aluno: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao alterar Aluno: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    public ArrayList<Aluno> recuperaTodosAlunos(String campoOrdenacao) {
        Connection conexao = realizaConexaoMySQL();
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        if (conexao == null) return listaAlunos;
        
        String sql = "SELECT * FROM Pessoa p INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa ORDER BY " + campoOrdenacao;
        
        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setCPF(rs.getLong("CPF"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setDataNascimento(rs.getString("DataNascimento"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setRua(rs.getString("Rua"));
                aluno.setBairro(rs.getString("Bairro"));
                aluno.setCidade(rs.getString("Cidade"));
                aluno.setEstado(rs.getString("Estado"));
                aluno.setMatricula(rs.getLong("Matricula"));
                aluno.setPeriodo(rs.getInt("Periodo"));
                aluno.setTurma(rs.getLong("Codigo_Turma"));
                
                listaAlunos.add(aluno);       
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao recuperar Alunos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao recuperar dados: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao); 
        }
        return listaAlunos;
    }

    public Aluno buscaAlunoPorMatricula(long matricula) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return null;

        String sql = "SELECT * FROM Pessoa p INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa WHERE a.Matricula = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setCPF(rs.getLong("CPF"));
                    aluno.setNome(rs.getString("Nome"));
                    aluno.setDataNascimento(rs.getString("DataNascimento"));
                    aluno.setTelefone(rs.getString("Telefone"));
                    aluno.setRua(rs.getString("Rua"));
                    aluno.setBairro(rs.getString("Bairro"));
                    aluno.setCidade(rs.getString("Cidade"));
                    aluno.setEstado(rs.getString("Estado"));
                    aluno.setMatricula(rs.getLong("Matricula"));
                    aluno.setPeriodo(rs.getInt("Periodo"));
                    aluno.setTurma(rs.getLong("Codigo_Turma"));
                    return aluno;
                }
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao buscar Aluno por matrícula: " + e.getMessage());
        } finally {
            desconectaMySQL(conexao);
        }
        return null;
    }

    // ========================================================================
    // EGRESSO - GRAVAR E ALTERAR

    public void insereEgresso(Egresso egresso) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAluno = "INSERT INTO Aluno (Matricula, Periodo, CPF_Pessoa, Codigo_Turma) VALUES (?, ?, ?, ?)";
        String sqlEgresso = "INSERT INTO Egresso (Matricula_Aluno, ProfissaoAtual, FaixaSalarial, CursoAnterior, CursoAtual) VALUES (?, ?, ?, ?, ?)";
        
        try {
            conexao.setAutoCommit(false); 
            
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno);
                 PreparedStatement psEgresso = conexao.prepareStatement(sqlEgresso)) {
                
                psPessoa.setLong(1, egresso.getCPF());
                psPessoa.setString(2, egresso.getNome());
                psPessoa.setString(3, egresso.getDataNascimento());
                psPessoa.setString(4, egresso.getTelefone());
                psPessoa.setString(5, egresso.getRua());
                psPessoa.setString(6, egresso.getBairro());
                psPessoa.setString(7, egresso.getCidade());
                psPessoa.setString(8, egresso.getEstado());
                psPessoa.executeUpdate();
                
                psAluno.setLong(1, egresso.getMatricula());
                psAluno.setInt(2, egresso.getPeriodo());
                psAluno.setLong(3, egresso.getCPF());
                psAluno.setLong(4, egresso.getTurma());
                psAluno.executeUpdate();
                
                psEgresso.setLong(1, egresso.getMatricula()); 
                psEgresso.setString(2, egresso.getProfissaoAtual());
                psEgresso.setString(3, egresso.getFaixaSalarial());
                psEgresso.setString(4, egresso.getCursoAnterior());
                psEgresso.setString(5, egresso.getCursoAtual());
                psEgresso.executeUpdate();
                
                conexao.commit(); 
                JOptionPane.showMessageDialog(null, "Egresso cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {}
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao inserir Egresso: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar egresso: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    public void alteraEgresso(Egresso egresso) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, DataNascimento = ?, Telefone = ?, Rua = ?, Bairro = ?, Cidade = ?, Estado = ? WHERE CPF = ?";
        String sqlAluno = "UPDATE Aluno SET Periodo = ?, Codigo_Turma = ? WHERE Matricula = ?";
        String sqlEgresso = "UPDATE Egresso SET ProfissaoAtual = ?, FaixaSalarial = ?, CursoAnterior = ?, CursoAtual = ? WHERE Matricula_Aluno = ?";

        try {
            conexao.setAutoCommit(false);

            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno);
                 PreparedStatement psEgresso = conexao.prepareStatement(sqlEgresso)) {

                psPessoa.setString(1, egresso.getNome());
                psPessoa.setString(2, egresso.getDataNascimento());
                psPessoa.setString(3, egresso.getTelefone());
                psPessoa.setString(4, egresso.getRua());
                psPessoa.setString(5, egresso.getBairro());
                psPessoa.setString(6, egresso.getCidade());
                psPessoa.setString(7, egresso.getEstado());
                psPessoa.setLong(8, egresso.getCPF());
                psPessoa.executeUpdate();

                psAluno.setInt(1, egresso.getPeriodo());
                psAluno.setLong(2, egresso.getTurma());
                psAluno.setLong(3, egresso.getMatricula());
                psAluno.executeUpdate();

                psEgresso.setString(1, egresso.getProfissaoAtual());
                psEgresso.setString(2, egresso.getFaixaSalarial());
                psEgresso.setString(3, egresso.getCursoAnterior());
                psEgresso.setString(4, egresso.getCursoAtual());
                psEgresso.setLong(5, egresso.getMatricula());
                psEgresso.executeUpdate();

                conexao.commit();
                JOptionPane.showMessageDialog(null, "Dados do Egresso alterados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {}
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao alterar Egresso: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao alterar Egresso: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    // ========================================================================
    // TURMA - GRAVAR

    public void insereTurma(Turma turma) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sql = "INSERT INTO Turma (CodigoTurma, NomeTurma) VALUES (?, ?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, turma.getCodigoTurma());
            ps.setString(2, turma.getNomeTurma());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Turma criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao inserir Turma: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao criar turma: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    // ========================================================================
    // USUÁRIO - AUTENTICAÇÃO

    public Usuario autenticaUsuario(String nomeUsuario, String senhaFornecida) {
    Connection conexao = realizaConexaoMySQL();
    if (conexao == null) return null;

    String sql = "SELECT * FROM Usuario WHERE NomeUsuario = ? AND Senha = ?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, nomeUsuario);
        ps.setString(2, senhaFornecida);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String nomeBanco = rs.getString("NomeUsuario");
                String senhaBanco = rs.getString("Senha");
                int nivelBanco = rs.getInt("NivelAcesso");

                return new Usuario(nomeBanco, senhaBanco, nivelBanco);
            }
        }
    } catch (SQLException e) {
        registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao autenticar Usuário: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    } finally {
        desconectaMySQL(conexao);
    }

    return null;
}
}