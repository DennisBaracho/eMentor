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
    private final String nome = "ementorPlus"; 
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
        String sqlNota = "INSERT INTO Nota (Matricula_Aluno, NumeroNota, Valor) VALUES (?, ?, ?)";

        try {
            conexao.setAutoCommit(false);

            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                PreparedStatement psAluno = conexao.prepareStatement(sqlAluno);
                PreparedStatement psNota = conexao.prepareStatement(sqlNota)) {

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

                float[] notas = aluno.getNotas();
                for (int i = 0; i < notas.length; i++) {
                    psNota.setLong(1, aluno.getMatricula());
                    psNota.setInt(2, i + 1);        // Nota 1 a Nota 10
                    psNota.setFloat(3, notas[i]);
                    psNota.addBatch();
                }
                psNota.executeBatch();

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
        String sqlNota = "UPDATE Nota SET Valor = ? WHERE Matricula_Aluno = ? AND NumeroNota = ?";
        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno);
                 PreparedStatement psNota = conexao.prepareStatement(sqlNota)) {

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

                float[] notas = aluno.getNotas();
                if (notas != null) {
                    for (int i = 0; i < notas.length; i++) {
                        psNota.setFloat(1, notas[i]);
                        psNota.setLong(2, aluno.getMatricula());
                        psNota.setInt(3, i + 1);
                        psNota.addBatch();
                    }
                    psNota.executeBatch();
                }

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

    public void atualizaNotas(long matricula, float[] notas) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;
        String sqlNota = "UPDATE Nota SET Valor = ? WHERE Matricula_Aluno = ? AND NumeroNota = ?";

        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement psNota = conexao.prepareStatement(sqlNota)) {
                for (int i = 0; i < notas.length; i++) {
                    psNota.setFloat(1, notas[i]);
                    psNota.setLong(2, matricula);
                    psNota.setInt(3, i + 1);
                    psNota.addBatch();
                }
                psNota.executeBatch();
                conexao.commit();
                JOptionPane.showMessageDialog(null, "Notas lançadas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {}
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao lançar notas: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao lançar notas: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
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
        String sqlNota = "UPDATE Nota SET Valor = ? WHERE Matricula_Aluno = ? AND NumeroNota = ?";
        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 PreparedStatement psAluno = conexao.prepareStatement(sqlAluno);
                 PreparedStatement psEgresso = conexao.prepareStatement(sqlEgresso);
                 PreparedStatement psNota = conexao.prepareStatement(sqlNota)) {

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

                float[] notas = egresso.getNotas();
                if (notas != null) {
                    for (int i = 0; i < notas.length; i++) {
                        psNota.setFloat(1, notas[i]);
                        psNota.setLong(2, egresso.getMatricula());
                        psNota.setInt(3, i + 1); // Nota 1 a Nota 10
                        psNota.addBatch();
                    }
                    psNota.executeBatch();
                }

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

    public Egresso buscaEgressoPorCPF(long cpf) {
        java.sql.Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return null;

        String sql = "SELECT * FROM Pessoa p "
                   + "INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa "
                   + "INNER JOIN Egresso e ON a.Matricula = e.Matricula_Aluno "
                   + "WHERE p.CPF = ?";

        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, cpf);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Egresso egresso = new Egresso();
                    egresso.setCPF(rs.getLong("CPF"));
                    egresso.setNome(rs.getString("Nome"));
                    egresso.setDataNascimento(rs.getString("DataNascimento"));
                    egresso.setTelefone(rs.getString("Telefone"));
                    egresso.setRua(rs.getString("Rua"));
                    egresso.setBairro(rs.getString("Bairro"));
                    egresso.setCidade(rs.getString("Cidade"));
                    egresso.setEstado(rs.getString("Estado"));
                    egresso.setMatricula(rs.getLong("Matricula"));
                    egresso.setPeriodo(rs.getInt("Periodo"));
                    egresso.setTurma(rs.getLong("Codigo_Turma"));
                    egresso.setProfissaoAtual(rs.getString("ProfissaoAtual"));
                    egresso.setFaixaSalarial(rs.getString("FaixaSalarial"));
                    egresso.setCursoAnterior(rs.getString("CursoAnterior"));
                    egresso.setCursoAtual(rs.getString("CursoAtual"));
                    return egresso;
                }
            }
        } catch (java.sql.SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao buscar Egresso por CPF: " + e.getMessage());
        } finally {
            desconectaMySQL(conexao);
        }
        return null;
    }
    
    public ArrayList<Egresso> recuperaTodosEgressos() {
        Connection conexao = realizaConexaoMySQL();
        ArrayList<Egresso> listaEgressos = new ArrayList<>();
        if (conexao == null) return listaEgressos;

        String sql = "SELECT * FROM Pessoa p "
                   + "INNER JOIN Aluno a ON p.CPF = a.CPF_Pessoa "
                   + "INNER JOIN Egresso e ON a.Matricula = e.Matricula_Aluno "
                   + "ORDER BY p.Nome";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Egresso egresso = new Egresso();

                egresso.setCPF(rs.getLong("CPF"));
                egresso.setNome(rs.getString("Nome"));
                egresso.setDataNascimento(rs.getString("DataNascimento"));
                egresso.setTelefone(rs.getString("Telefone"));
                egresso.setRua(rs.getString("Rua"));
                egresso.setBairro(rs.getString("Bairro"));
                egresso.setCidade(rs.getString("Cidade"));
                egresso.setEstado(rs.getString("Estado"));

                egresso.setMatricula(rs.getLong("Matricula"));
                egresso.setPeriodo(rs.getInt("Periodo"));
                egresso.setTurma(rs.getLong("Codigo_Turma"));

                egresso.setProfissaoAtual(rs.getString("ProfissaoAtual"));
                egresso.setFaixaSalarial(rs.getString("FaixaSalarial"));
                egresso.setCursoAnterior(rs.getString("CursoAnterior"));
                egresso.setCursoAtual(rs.getString("CursoAtual"));

                listaEgressos.add(egresso);
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao recuperar Egressos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao recuperar egressos: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
        return listaEgressos;
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
    // TURMA - RECUPERAR POR CÓDIGO E ALTERAR

    public Turma buscaTurmaPorCodigo(long codigoTurma) {
        java.sql.Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return null;

        String sql = "SELECT * FROM Turma WHERE CodigoTurma = ?";

        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, codigoTurma);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turma turma = new Turma();
                    turma.setCodigoTurma(rs.getLong("CodigoTurma"));
                    turma.setNomeTurma(rs.getString("NomeTurma"));
                    return turma;
                }
            }
        } catch (java.sql.SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao buscar Turma por código: " + e.getMessage());
        } finally {
            desconectaMySQL(conexao);
        }
        return null;
    }

    public ArrayList<Turma> recuperaTodasTurmas() {
        Connection conexao = realizaConexaoMySQL();
        ArrayList<Turma> listaTurmas = new ArrayList<>();
        if (conexao == null) return listaTurmas;

        String sql = "SELECT * FROM Turma ORDER BY NomeTurma";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turma turma = new Turma();
                turma.setCodigoTurma(rs.getLong("CodigoTurma"));
                turma.setNomeTurma(rs.getString("NomeTurma"));
                listaTurmas.add(turma);
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao recuperar Turmas: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao recuperar turmas: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
        return listaTurmas;
    }

    public void alteraTurma(Turma turma) {
        java.sql.Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        // Atualiza apenas o nome da turma onde o código for igual ao informado
        String sql = "UPDATE Turma SET NomeTurma = ? WHERE CodigoTurma = ?";

        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, turma.getNomeTurma());
            ps.setLong(2, turma.getCodigoTurma());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Turma alterada com sucesso!", "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Nenhuma turma foi alterada.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (java.sql.SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao alterar Turma: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao alterar Turma: " + e.getMessage(), "ERRO", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }
    
    // ========================================================================
    // PROFESSOR - GRAVER, RECUPERAR POR CPF e ALTERAR
    public void insereProfessor(Professor professor) {
        Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "INSERT INTO Pessoa (CPF, Nome, DataNascimento, Telefone, Rua, Bairro, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlProfessor = "INSERT INTO Professor (CPF_Pessoa, DataAdmissao, SalarioBruto, isChefia, isCoordenacao) VALUES (?, ?, ?, ?, ?)";
    
        try {
            conexao.setAutoCommit(false); 
        
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                PreparedStatement psProfessor = conexao.prepareStatement(sqlProfessor)) {
            
            // 1. Insere os dados básicos na tabela Pessoa
                psPessoa.setLong(1, professor.getCPF());
                psPessoa.setString(2, professor.getNome());
                psPessoa.setString(3, professor.getDataNascimento()); 
                psPessoa.setString(4, professor.getTelefone());
                psPessoa.setString(5, professor.getRua());
                psPessoa.setString(6, professor.getBairro());
                psPessoa.setString(7, professor.getCidade());
                psPessoa.setString(8, professor.getEstado());
                psPessoa.executeUpdate(); 
            
            // 2. Insere os dados específicos na tabela Professor vinculando pelo CPF
                psProfessor.setLong(1, professor.getCPF());
                psProfessor.setString(2, professor.getDataAdmissao());
                psProfessor.setDouble(3, professor.getSalarioBruto());
                psProfessor.setBoolean(4, professor.isChefia());
                psProfessor.setBoolean(5, professor.isCoordenacao());
                psProfessor.executeUpdate();
            
                conexao.commit(); 
                JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            try { conexao.rollback(); } catch (SQLException ex) {} 
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao inserir Professor: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Professor: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
    }
    public Professor buscaProfessorPorCPF(long cpf) {
        java.sql.Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return null;

        // Faz o JOIN entre Pessoa e Professor usando o CPF
        String sql = "SELECT * FROM Pessoa p "
                   + "INNER JOIN Professor prof ON p.CPF = prof.CPF_Pessoa "
                   + "WHERE p.CPF = ?";

        try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, cpf);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Professor prof = new Professor();
                    // Dados de Pessoa
                    prof.setCPF(rs.getLong("CPF"));
                    prof.setNome(rs.getString("Nome"));
                    prof.setDataNascimento(rs.getString("DataNascimento"));
                    prof.setTelefone(rs.getString("Telefone"));
                    prof.setRua(rs.getString("Rua"));
                    prof.setBairro(rs.getString("Bairro"));
                    prof.setCidade(rs.getString("Cidade"));
                    prof.setEstado(rs.getString("Estado"));

                    // Dados de Professor (verifique se os nomes das colunas no seu MySQL sÃ£o esses mesmos)
                    prof.setDataAdmissao(rs.getString("DataAdmissao"));
                    prof.setSalarioBruto(rs.getDouble("SalarioBruto"));
                    prof.setChefia(rs.getBoolean("isChefia"));
                    prof.setCoordenacao(rs.getBoolean("isCoordenacao"));

                    return prof;
                }
            }
        } catch (java.sql.SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao buscar Professor por CPF: " + e.getMessage());
        } finally {
            desconectaMySQL(conexao);
        }
        return null;
    }
    
    public ArrayList<Professor> recuperaTodosProfessores() {
        Connection conexao = realizaConexaoMySQL();
        ArrayList<Professor> listaProfessores = new ArrayList<>();
        if (conexao == null) return listaProfessores;

        String sql = "SELECT * FROM Pessoa p "
                   + "INNER JOIN Professor prof ON p.CPF = prof.CPF_Pessoa "
                   + "ORDER BY p.Nome";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Professor professor = new Professor();
                professor.setCPF(rs.getLong("CPF"));
                professor.setNome(rs.getString("Nome"));
                professor.setDataNascimento(rs.getString("DataNascimento"));
                professor.setTelefone(rs.getString("Telefone"));
                professor.setRua(rs.getString("Rua"));
                professor.setBairro(rs.getString("Bairro"));
                professor.setCidade(rs.getString("Cidade"));
                professor.setEstado(rs.getString("Estado"));
                professor.setDataAdmissao(rs.getString("DataAdmissao"));
                professor.setSalarioBruto(rs.getDouble("SalarioBruto"));
                professor.setChefia(rs.getBoolean("isChefia"));
                professor.setCoordenacao(rs.getBoolean("isCoordenacao"));
                listaProfessores.add(professor);
            }
        } catch (SQLException e) {
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao recuperar Professores: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao recuperar professores: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } finally {
            desconectaMySQL(conexao);
        }
        return listaProfessores;
    }

    public void alteraProfessor(Professor professor) {
        java.sql.Connection conexao = realizaConexaoMySQL();
        if (conexao == null) return;

        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, DataNascimento = ?, Telefone = ?, Rua = ?, Bairro = ?, Cidade = ?, Estado = ? WHERE CPF = ?";
        String sqlProfessor = "UPDATE Professor SET DataAdmissao = ?, SalarioBruto = ?, isChefia = ?, isCoordenacao = ? WHERE CPF_Pessoa = ?";

        try {
            conexao.setAutoCommit(false);

            try (java.sql.PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa);
                 java.sql.PreparedStatement psProfessor = conexao.prepareStatement(sqlProfessor)) {

                // Atualiza tabela Pessoa
                psPessoa.setString(1, professor.getNome());
                psPessoa.setString(2, professor.getDataNascimento());
                psPessoa.setString(3, professor.getTelefone());
                psPessoa.setString(4, professor.getRua());
                psPessoa.setString(5, professor.getBairro());
                psPessoa.setString(6, professor.getCidade());
                psPessoa.setString(7, professor.getEstado());
                psPessoa.setLong(8, professor.getCPF());
                psPessoa.executeUpdate();

                // Atualiza tabela Professor
                psProfessor.setString(1, professor.getDataAdmissao());
                psProfessor.setDouble(2, professor.getSalarioBruto());
                psProfessor.setBoolean(3, professor.isChefia());
                psProfessor.setBoolean(4, professor.isCoordenacao());
                psProfessor.setLong(5, professor.getCPF());
                psProfessor.executeUpdate();

                conexao.commit();
                javax.swing.JOptionPane.showMessageDialog(null, "Dados do Professor alterados com sucesso!", "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (java.sql.SQLException e) {
            try { conexao.rollback(); } catch (java.sql.SQLException ex) {}
            registrarErroLog(String.valueOf(e.getErrorCode()), "Erro ao alterar Professor: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao alterar Professor: " + e.getMessage(), "ERRO", javax.swing.JOptionPane.ERROR_MESSAGE);
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