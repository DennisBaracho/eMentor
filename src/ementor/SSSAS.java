/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ementor;
import java.util.ArrayList;
/**
 *
 * @author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
 */
public class ListaNotas extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ListaNotas.class.getName());

    // Agora temos 3 modos: TODOS, SOMENTE_EGRESSOS, SOMENTE_NAO_EGRESSOS
    public enum ModoExibicao {
        TODOS,
        SOMENTE_EGRESSOS,
        SOMENTE_NAO_EGRESSOS
    }

    private ModoExibicao modoAtual;

    // ATENÇÃO: estes 3 botões NÃO fazem parte do initComponents() gerado pelo
    // NetBeans (nem do arquivo .form). Eles são criados manualmente no método
    // criarBotoesFiltro(), logo abaixo, para não corromper o bloco protegido
    // que o GUI Builder controla.
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;

    public ListaNotas() {
        this(ModoExibicao.TODOS);
    }

    // Mantido por compatibilidade: true = somente egressos, false = todos
    public ListaNotas(boolean somenteEgressos) {
        this(somenteEgressos ? ModoExibicao.SOMENTE_EGRESSOS : ModoExibicao.TODOS);
    }

    public ListaNotas(ModoExibicao modo) {
        initComponents();
        criarBotoesFiltro();

        this.modoAtual = modo;

        this.setResizable(false);
        this.setSize(800, 640);
        this.setLocationRelativeTo(null);

        carregarNotas(modoAtual);
    }

    /**
     * Cria os botões de filtro (Todos / Somente Egressos / Somente Não
     * Egressos) e ajusta a tabela para abrir espaço para eles.
     * Este método é código nosso, comum, sem nenhuma ligação com o
     * GUI Builder do NetBeans — pode ser editado livremente.
     */
    private void criarBotoesFiltro() {
        jButton7 = new javax.swing.JButton();
        jButton7.setBackground(new java.awt.Color(45, 60, 135));
        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Todos");
        jButton7.setBorderPainted(false);
        jButton7.addActionListener(this::jButton7ActionPerformed);
        jButton7.setBounds(20, 40, 120, 23);
        jPanel1.add(jButton7);

        jButton8 = new javax.swing.JButton();
        jButton8.setBackground(new java.awt.Color(45, 60, 135));
        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Somente Egressos");
        jButton8.setBorderPainted(false);
        jButton8.addActionListener(this::jButton8ActionPerformed);
        jButton8.setBounds(150, 40, 150, 23);
        jPanel1.add(jButton8);

        jButton9 = new javax.swing.JButton();
        jButton9.setBackground(new java.awt.Color(45, 60, 135));
        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Somente Não Egressos");
        jButton9.setBorderPainted(false);
        jButton9.addActionListener(this::jButton9ActionPerformed);
        jButton9.setBounds(310, 40, 170, 23);
        jPanel1.add(jButton9);

        // Empurra a tabela pra baixo pra abrir espaço para a linha de botões
        jScrollPane1.setBounds(0, 70, 800, 530);

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void carregarNotas(ModoExibicao modo) {
        ConexoesMySQL banco = new ConexoesMySQL();
        ArrayList<? extends Aluno> listaAlunos;

        switch (modo) {
            case SOMENTE_EGRESSOS:
                listaAlunos = banco.recuperaTodosEgressos();
                jLabel1.setText("Lista de Notas - Somente Egressos");
                break;
            case SOMENTE_NAO_EGRESSOS:
                listaAlunos = banco.recuperaAlunosNaoEgressos("Nome");
                jLabel1.setText("Lista de Notas - Somente Não Egressos");
                break;
            default:
                listaAlunos = banco.recuperaTodosAlunos("Nome");
                jLabel1.setText("Lista de Notas - Todos os Alunos");
                break;
        }

        java.util.Map<Long, java.util.List<Float>> notasPorMatricula = new java.util.HashMap<>();

        java.sql.Connection conexao = banco.realizaConexaoMySQL();
        if (conexao != null) {
            String sql = "SELECT Matricula_Aluno, Valor FROM Nota ORDER BY Matricula_Aluno, NumeroNota";
            try (java.sql.PreparedStatement ps = conexao.prepareStatement(sql);
                 java.sql.ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    long matricula = rs.getLong("Matricula_Aluno");
                    float valor = rs.getFloat("Valor");
                    notasPorMatricula
                        .computeIfAbsent(matricula, k -> new java.util.ArrayList<>())
                        .add(valor);
                }
            } catch (java.sql.SQLException e) {
                logger.log(java.util.logging.Level.SEVERE, "Erro ao carregar notas", e);
            } finally {
                banco.desconectaMySQL(conexao);
            }
        }

        javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        for (Aluno aluno : listaAlunos) {
            java.util.List<Float> notas = notasPorMatricula.getOrDefault(aluno.getMatricula(), java.util.Collections.emptyList());

            float soma = 0;
            for (float nota : notas) soma += nota;
            float media = !notas.isEmpty() ? soma / notas.size() : 0;

            modelo.addRow(new Object[]{
                aluno.getNome(),
                notas.size() > 0 ? notas.get(0) : "",
                notas.size() > 1 ? notas.get(1) : "",
                notas.size() > 2 ? notas.get(2) : "",
                notas.size() > 3 ? notas.get(3) : "",
                notas.size() > 4 ? notas.get(4) : "",
                notas.size() > 5 ? notas.get(5) : "",
                notas.size() > 6 ? notas.get(6) : "",
                notas.size() > 7 ? notas.get(7) : "",
                notas.size() > 8 ? notas.get(8) : "",
                notas.size() > 9 ? notas.get(9) : "",
                !notas.isEmpty() ? String.format("%.2f", media) : ""
            });
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jButton4.setBackground(new java.awt.Color(45, 60, 135));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cadastrar");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(this::jButton4ActionPerformed);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 400));
        setSize(new java.awt.Dimension(600, 400));

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Lista de Notas");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 400, 16);

        jTable1.setBackground(new java.awt.Color(30, 30, 30));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Nota 1", "Nota 2 ", "Nota 3", "Nota 4 ", "Nota 5", "Nota 6", "Nota 7", "Nota 8", "Nota 9", "Nota 10", "Média"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 40, 800, 560);

        jButton5.setBackground(new java.awt.Color(45, 60, 135));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Voltar");
        jButton5.setBorderPainted(false);
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel1.add(jButton5);
        jButton5.setBounds(580, 10, 100, 23);

        jButton6.setBackground(new java.awt.Color(45, 60, 135));
        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Gerar PDF");
        jButton6.setBorderPainted(false);
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel1.add(jButton6);
        jButton6.setBounds(690, 10, 100, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        javax.swing.JFileChooser seletor = new javax.swing.JFileChooser();

        // Nome de arquivo sugerido varia conforme o filtro ativo na tela
        String nomeSugerido;
        switch (modoAtual) {
            case SOMENTE_EGRESSOS:
                nomeSugerido = "relatorio_notas_egressos.pdf";
                break;
            case SOMENTE_NAO_EGRESSOS:
                nomeSugerido = "relatorio_notas_nao_egressos.pdf";
                break;
            default:
                nomeSugerido = "relatorio_notas_todos.pdf";
                break;
        }

        seletor.setDialogTitle("Salvar Relatório de Notas");
        seletor.setSelectedFile(new java.io.File(nomeSugerido));

        int opcao = seletor.showSaveDialog(this);
        if (opcao != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }

        String caminhoArquivo = seletor.getSelectedFile().getAbsolutePath();
        if (!caminhoArquivo.toLowerCase().endsWith(".pdf")) {
            caminhoArquivo += ".pdf";
        }

        ConexoesMySQL banco = new ConexoesMySQL();
        GerarRelatorioPDF gerador = new GerarRelatorioPDF();

        // Gera o PDF com a MESMA lista que está sendo exibida na tabela no momento
        switch (modoAtual) {
            case SOMENTE_EGRESSOS:
                ArrayList<Egresso> listaEgressos = banco.recuperaTodosEgressos();
                gerador.gerarRelatorioNotasEgressos(listaEgressos, caminhoArquivo);
                break;
            case SOMENTE_NAO_EGRESSOS:
                ArrayList<Aluno> listaNaoEgressos = banco.recuperaAlunosNaoEgressos("Nome");
                gerador.gerarRelatorioNotas(listaNaoEgressos, caminhoArquivo);
                break;
            default:
                ArrayList<Aluno> listaTodos = banco.recuperaTodosAlunos("Nome");
                gerador.gerarRelatorioNotas(listaTodos, caminhoArquivo);
                break;
        }
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        MenuOpçõesAluno MinhaJanela = new MenuOpçõesAluno();
        MinhaJanela.setVisible(true);
        this.dispose();
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        modoAtual = ModoExibicao.TODOS;
        carregarNotas(modoAtual);
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        modoAtual = ModoExibicao.SOMENTE_EGRESSOS;
        carregarNotas(modoAtual);
    }

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        modoAtual = ModoExibicao.SOMENTE_NAO_EGRESSOS;
        carregarNotas(modoAtual);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ListaNotas().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   
}
