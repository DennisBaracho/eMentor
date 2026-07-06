package ementor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConexoesMySQL {
    
    /* Seção de atributos/variaveis iniciais */
    private final String caminho = "localhost"; 
    private final String porta = "3306"; 
    private final String nome = "ementor"; 
    private final String usuario = "root"; 
    private final String senha = "admin"; 
    private final String fusoHorario = "?useTimezone=true&serverTimezone=UTC";
    private final String URL = "jdbc:mysql://" + caminho + ":" + porta + "/" + nome + fusoHorario; 
    
    public Connection realizaConexaoMySQL() {
        try {
            return DriverManager.getConnection(URL, usuario, senha); 
        } catch (SQLException e) {
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
            JOptionPane.showMessageDialog(null, "Erro ao desconectar: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }   
    }
    // ========================================================================
    // Pessoa

    public void inserePessoa(Pessoa pessoa) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sql = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            // Pegando os dados de dentro do objeto 'pessoa'
            ps.setLong(1, pessoa.CPF);
            ps.setString(2, pessoa.Nome);
            ps.setString(3, pessoa.DataNascimento); 
            ps.setString(4, pessoa.Telefone);
            ps.setString(5, pessoa.getRua());
            ps.setString(6, pessoa.getBairro());
            ps.setString(7, pessoa.getCidade());
            ps.setString(8, pessoa.getEstado());
            
            ps.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Pessoa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Pessoa: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }
    // ========================================================================
    // ALUNO (Envolve Pessoa)

    
    public void insereAluno(Aluno aluno) {
        
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAluno = "INSERT INTO Aluno (Matricula, Periodo, CPF_Pessoa, Turma) VALUES (?, ?, ?, ?)";
        
        try {
            conexao.setAutoCommit(false);
            
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno)) {
                
                // Pegando os dados direto do OBJETO aluno
                psPessoa.setLong(1, aluno.CPF);
                psPessoa.setString(2, aluno.Nome);
                psPessoa.setString(3, aluno.DataNascimento); 
                psPessoa.setString(4, aluno.Telefone);
                psPessoa.setString(5, aluno.getRua());
                psPessoa.setString(6, aluno.getBairro());
                psPessoa.setString(7, aluno.getCidade());
                psPessoa.setString(8, aluno.getEstado());
                psPessoa.executeUpdate(); 
                
                // Pegando os dados do OBJETO aluno
                psAluno.setLong(1, aluno.getMatricula());
                psAluno.setInt(2, aluno.getPeriodo());
                psAluno.setLong(3, aluno.CPF); 
                psAluno.setLong(4, aluno.getTurma());
                psAluno.executeUpdate();
                
                conexao.commit(); 
                JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {} 
            JOptionPane.showMessageDialog(null, "Erro ao inserir Aluno: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    public void atualizaPeriodoAluno(long matricula, int novoPeriodo) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        // O update deve ser feito apenas na tabela Aluno
        String sql = "UPDATE Aluno SET Periodo = ? WHERE Matricula = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, novoPeriodo);
            ps.setLong(2, matricula);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Período atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    public ArrayList<Aluno> recuperaTodosAlunos(String tipoOrdenacao) {
        Connection conexao = realizaConexaoMySQL();
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        if (conexao == null) return listaAlunos;
        
        String sql = "SELECT * FROM Pessoa p INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa ORDER BY " + tipoOrdenacao;
        
        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.CPF = rs.getLong("CPF");
                aluno.Nome = rs.getString("Nome");
                aluno.DataNascimento = rs.getString("DataNascimento");
                aluno.Telefone = rs.getString("Telefone");
                aluno.setMatricula(rs.getLong("Matricula"));
                aluno.setPeriodo(rs.getInt("Periodo"));
                // Você pode adicionar os sets de Endereço e Turma aqui, caso existam na classe Aluno
                
                listaAlunos.add(aluno);       
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar dados: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao); 
        }
        return listaAlunos;
    }

    public Aluno buscaAluno(long matricula) {
        Connection conexao = realizaConexaoMySQL();
        Aluno aluno = null;
        if (conexao == null) return null;
        
        String sql = "SELECT * FROM Pessoa p INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa WHERE a.Matricula = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, matricula);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno();
                    aluno.CPF = rs.getLong("CPF");
                    aluno.Nome = rs.getString("Nome");
                    aluno.DataNascimento = rs.getString("DataNascimento");
                    aluno.Telefone = rs.getString("Telefone");
                    aluno.setMatricula(rs.getLong("Matricula"));
                    aluno.setPeriodo(rs.getInt("Periodo"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar aluno: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao); 
        }
        return aluno;
    }

    // ========================================================================
    // PROFESSOR


    public void insereProfessor(Professor professor) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlProfessor = "INSERT INTO Professor (DataAdmissao, Chefia, Coordenacao, SalarioBruto, CPF_Pessoa) VALUES (?, ?, ?, ?, ?)";
        
        try {
            conexao.setAutoCommit(false); // Inicia transação
            
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psProf = conexao.prepareStatement(sqlProfessor)) {
                
                // Setando dados da Pessoa (que vieram dentro do objeto professor)
                psPessoa.setLong(1, professor.CPF);
                psPessoa.setString(2, professor.Nome);
                psPessoa.setString(3, professor.DataNascimento);
                psPessoa.setString(4, professor.Telefone);
                psPessoa.setString(5, professor.getRua());
                psPessoa.setString(6, professor.getBairro());
                psPessoa.setString(7, professor.getCidade());
                psPessoa.setString(8, professor.getEstado());
                psPessoa.executeUpdate(); 
                
                // Setando dados específicos do Professor
                psProf.setString(1, professor.getDataAdmissao());
                psProf.setBoolean(2, professor.isChefia());
                psProf.setBoolean(3, professor.isCoordenacao());
                psProf.setDouble(4, professor.getSalarioBruto());
                psProf.setLong(5, professor.CPF); // A chave estrangeira ligando à Pessoa
                psProf.executeUpdate();
                
                conexao.commit(); // Salva tudo
                JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "Erro ao inserir Professor: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    // ========================================================================
    // NOTAS

    public void insereNota(Nota nota) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sql = "INSERT INTO Notas (DataLancamento, Valor, Matricula_Aluno) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            // Pegando os dados de dentro do objeto 'nota'
            ps.setString(1, nota.getDataLancamento()); 
            ps.setDouble(2, nota.getValor());
            ps.setLong(3, nota.getMatriculaAluno());
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Nota lançada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao lançar nota: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }

    // ========================================================================
    // EGRESSO

    public void insereEgresso(Egresso egresso) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sql = "INSERT INTO Egresso (ProfissaoAtual, FaixaSalarial, CursoAnterior, CursoAtual) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            // Pegando os dados de dentro do objeto 'egresso'
            ps.setString(1, egresso.getProfissaoAtual());
            ps.setString(2, egresso.getFaixaSalarial());
            ps.setString(3, egresso.getCursoAnterior());
            ps.setString(4, egresso.getCursoAtual());
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Egresso cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar egresso: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }
}

