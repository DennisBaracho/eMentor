/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ementor;
import jiconfont.swing.IconFontSwing;
import jiconfont.icons.font_awesome.FontAwesome;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Anderson
 */
public class MenuAlterarAluno extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAlterarAluno.class.getName());
    
    private List<Aluno> listaAlunos = new ArrayList<>();
    private int indiceAtual = -1;
    
    private void exibirAlunoNaTela(Aluno aluno) {
        if (aluno == null) return;
        
        lblNome.setText(aluno.getNome());
        lblDataNascimento.setText(aluno.getDataNascimento());
        lblCPF.setText(String.valueOf(aluno.getCPF()));
        lblTelefone.setText(aluno.getTelefone());
        lblRua.setText(aluno.getRua());
        lblBairro.setText(aluno.getBairro());
        lblCidade.setText(aluno.getCidade());
        lblEstado.setText(aluno.getEstado());
    
        lblMatricula.setText(String.valueOf(aluno.getMatricula()));
        lblPeriodo.setText(String.valueOf(aluno.getPeriodo()));
        lblTurma.setText(String.valueOf(aluno.getTurma()));
    
        float[] notas = aluno.getNotas();
        if (notas != null && notas.length >= 10) {
            jTextField4.setText(String.valueOf(notas[0]));
            jTextField14.setText(String.valueOf(notas[1]));
            jTextField6.setText(String.valueOf(notas[2]));
            jTextField13.setText(String.valueOf(notas[3]));
            jTextField7.setText(String.valueOf(notas[4]));
            jTextField12.setText(String.valueOf(notas[5]));
            jTextField8.setText(String.valueOf(notas[6]));
            jTextField11.setText(String.valueOf(notas[7]));
            jTextField9.setText(String.valueOf(notas[8]));
            jTextField10.setText(String.valueOf(notas[9]));
        }
        
        liberarCampos();
        lblNome1.setEditable(false);
    }
    
        private void salvarTodasAlteracoes() {
        try {
            Aluno alunoAlterado = new Aluno();
        
            alunoAlterado.setNome(lblNome.getText());
            alunoAlterado.setDataNascimento(lblDataNascimento.getText());
            alunoAlterado.setCPF(Long.parseLong(lblCPF.getText()));
            alunoAlterado.setTelefone(lblTelefone.getText());
            alunoAlterado.setRua(lblRua.getText());
            alunoAlterado.setBairro(lblBairro.getText());
            alunoAlterado.setCidade(lblCidade.getText());
            alunoAlterado.setEstado(lblEstado.getText());
        
            alunoAlterado.setMatricula(Long.parseLong(lblMatricula.getText()));
            alunoAlterado.setPeriodo(Integer.parseInt(lblPeriodo.getText()));
            alunoAlterado.setTurma(Long.parseLong(lblTurma.getText()));
        
            float[] notas = new float[10];
            notas[0] = Float.parseFloat(jTextField4.getText());
            notas[1] = Float.parseFloat(jTextField14.getText());
            notas[2] = Float.parseFloat(jTextField6.getText());
            notas[3] = Float.parseFloat(jTextField13.getText());
            notas[4] = Float.parseFloat(jTextField7.getText());
            notas[5] = Float.parseFloat(jTextField12.getText());
            notas[6] = Float.parseFloat(jTextField8.getText());
            notas[7] = Float.parseFloat(jTextField11.getText());
            notas[8] = Float.parseFloat(jTextField9.getText());
            notas[9] = Float.parseFloat(jTextField10.getText());
            alunoAlterado.setNotas(notas);
        
            ConexoesMySQL conexao = new ConexoesMySQL();
            conexao.alteraAluno(alunoAlterado);
            bloquearCampos();
            lblNome1.setEditable(true);
            listaAlunos.clear();
            indiceAtual = -1;
        
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao preparar dados para alterar: " + e.getMessage(), "Erro de Validação", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    private void bloquearCampos() {
        lblNome.setEditable(false);
        lblDataNascimento.setEditable(false);
        lblCPF.setEditable(false);
        lblTelefone.setEditable(false);
        lblRua.setEditable(false);
        lblBairro.setEditable(false);
        lblCidade.setEditable(false);
        lblEstado.setEditable(false);
        lblPeriodo.setEditable(false);
        lblTurma.setEditable(false);
        lblMatricula.setEditable(false);
    
        jTextField4.setEditable(false);
        jTextField6.setEditable(false);
        jTextField7.setEditable(false);
        jTextField8.setEditable(false);
        jTextField9.setEditable(false);
        jTextField14.setEditable(false);
        jTextField13.setEditable(false);
        jTextField12.setEditable(false);
        jTextField11.setEditable(false);
        jTextField10.setEditable(false);
        
        //botão de salvar bloqueado alterar para o novo
        jButton7.setEnabled(false);
    }

    private void liberarCampos() {
        lblNome.setEditable(true);
        lblDataNascimento.setEditable(true);
        lblTelefone.setEditable(true);
        lblRua.setEditable(true);
        lblBairro.setEditable(true);
        lblCidade.setEditable(true);
        lblEstado.setEditable(true);
    
        lblPeriodo.setEditable(true);
        lblTurma.setEditable(true);
    
        jTextField4.setEditable(true);
        jTextField6.setEditable(true);
        jTextField7.setEditable(true);
        jTextField8.setEditable(true);
        jTextField9.setEditable(true);
        jTextField14.setEditable(true);
        jTextField13.setEditable(true);
        jTextField12.setEditable(true);
        jTextField11.setEditable(true);
        jTextField10.setEditable(true);
    

        jButton7.setEnabled(true);
}
    public MenuAlterarAluno() {
        IconFontSwing.register(FontAwesome.getIconFont());
        
        initComponents();
        
        this.setResizable(false); 
        
        this.setSize(800, 640); 
        
        this.setLocationRelativeTo(null);
        
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        
        bloquearCampos();

        jButton7.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 18, new java.awt.Color(255, 255, 255)));
        
        jButton1.setIcon(IconFontSwing.buildIcon(FontAwesome.SEARCH, 16, new java.awt.Color(255, 255, 255)));
        
        jButton6.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton3.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton2.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_RIGHT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        lblNome = new javax.swing.JTextField();
        txtNome = new javax.swing.JLabel();
        lblDataNascimento = new javax.swing.JTextField();
        txtDataNascimento = new javax.swing.JLabel();
        txtCPF = new javax.swing.JLabel();
        lblCPF = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JTextField();
        txtRua = new javax.swing.JLabel();
        lblRua = new javax.swing.JTextField();
        txtBairro = new javax.swing.JLabel();
        lblBairro = new javax.swing.JTextField();
        txtCidade = new javax.swing.JLabel();
        lblCidade = new javax.swing.JTextField();
        lblEstado = new javax.swing.JTextField();
        txtEstado = new javax.swing.JLabel();
        lblNome1 = new javax.swing.JTextField();
        txtNome1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lblMatricula = new javax.swing.JTextField();
        txtMatricula = new javax.swing.JLabel();
        txtPeriodo = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JTextField();
        txtTurma = new javax.swing.JLabel();
        lblTurma = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jButton4.setBackground(new java.awt.Color(45, 60, 135));
        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cadastrar");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(this::jButton4ActionPerformed);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 400));
        setSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(222, 222, 222));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(794, 606, 0, 606);

        jPanel6.setBackground(new java.awt.Color(30, 30, 30));
        jPanel6.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel6.setLayout(null);

        lblNome.addActionListener(this::lblNomeActionPerformed);
        jPanel6.add(lblNome);
        lblNome.setBounds(40, 50, 125, 22);

        txtNome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtNome.setForeground(new java.awt.Color(255, 255, 255));
        txtNome.setText("Nome");
        jPanel6.add(txtNome);
        txtNome.setBounds(40, 30, 37, 16);

        lblDataNascimento.addActionListener(this::lblDataNascimentoActionPerformed);
        jPanel6.add(lblDataNascimento);
        lblDataNascimento.setBounds(40, 110, 125, 22);

        txtDataNascimento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtDataNascimento.setForeground(new java.awt.Color(255, 255, 255));
        txtDataNascimento.setText("Data de Nascimento");
        jPanel6.add(txtDataNascimento);
        txtDataNascimento.setBounds(40, 90, 108, 16);

        txtCPF.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCPF.setForeground(new java.awt.Color(255, 255, 255));
        txtCPF.setText("CPF");
        jPanel6.add(txtCPF);
        txtCPF.setBounds(40, 140, 37, 16);

        lblCPF.addActionListener(this::lblCPFActionPerformed);
        jPanel6.add(lblCPF);
        lblCPF.setBounds(40, 160, 125, 22);

        txtTelefone.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTelefone.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefone.setText("Telefone");
        jPanel6.add(txtTelefone);
        txtTelefone.setBounds(40, 200, 46, 16);

        lblTelefone.addActionListener(this::lblTelefoneActionPerformed);
        jPanel6.add(lblTelefone);
        lblTelefone.setBounds(40, 220, 125, 22);

        txtRua.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtRua.setForeground(new java.awt.Color(255, 255, 255));
        txtRua.setText("Rua");
        jPanel6.add(txtRua);
        txtRua.setBounds(40, 250, 37, 16);

        lblRua.addActionListener(this::lblRuaActionPerformed);
        jPanel6.add(lblRua);
        lblRua.setBounds(40, 280, 125, 22);

        txtBairro.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBairro.setForeground(new java.awt.Color(255, 255, 255));
        txtBairro.setText("Bairro");
        jPanel6.add(txtBairro);
        txtBairro.setBounds(40, 310, 37, 16);

        lblBairro.addActionListener(this::lblBairroActionPerformed);
        jPanel6.add(lblBairro);
        lblBairro.setBounds(40, 330, 125, 22);

        txtCidade.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCidade.setForeground(new java.awt.Color(255, 255, 255));
        txtCidade.setText("Cidade");
        jPanel6.add(txtCidade);
        txtCidade.setBounds(40, 370, 36, 16);

        lblCidade.addActionListener(this::lblCidadeActionPerformed);
        jPanel6.add(lblCidade);
        lblCidade.setBounds(40, 390, 125, 22);

        lblEstado.addActionListener(this::lblEstadoActionPerformed);
        jPanel6.add(lblEstado);
        lblEstado.setBounds(40, 440, 125, 22);

        txtEstado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtEstado.setForeground(new java.awt.Color(255, 255, 255));
        txtEstado.setText("Estado");
        jPanel6.add(txtEstado);
        txtEstado.setBounds(40, 420, 37, 16);

        lblNome1.addActionListener(this::lblNome1ActionPerformed);
        jPanel6.add(lblNome1);
        lblNome1.setBounds(630, 50, 130, 22);

        txtNome1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtNome1.setForeground(new java.awt.Color(255, 255, 255));
        txtNome1.setText("Matricula");
        jPanel6.add(txtNome1);
        txtNome1.setBounds(630, 30, 70, 16);

        jButton1.setBackground(new java.awt.Color(45, 60, 135));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Pesquisar");
        jButton1.setBorderPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel6.add(jButton1);
        jButton1.setBounds(630, 80, 130, 25);

        jTabbedPane2.addTab("Dados Pessoais", jPanel6);

        jPanel7.setBackground(new java.awt.Color(30, 30, 30));
        jPanel7.setLayout(null);

        lblMatricula.addActionListener(this::lblMatriculaActionPerformed);
        jPanel7.add(lblMatricula);
        lblMatricula.setBounds(40, 50, 125, 22);

        txtMatricula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(255, 255, 255));
        txtMatricula.setText("Matricula");
        jPanel7.add(txtMatricula);
        txtMatricula.setBounds(40, 30, 60, 16);

        txtPeriodo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        txtPeriodo.setText("Período");
        jPanel7.add(txtPeriodo);
        txtPeriodo.setBounds(40, 90, 50, 16);

        lblPeriodo.addActionListener(this::lblPeriodoActionPerformed);
        jPanel7.add(lblPeriodo);
        lblPeriodo.setBounds(40, 110, 125, 22);

        txtTurma.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTurma.setForeground(new java.awt.Color(255, 255, 255));
        txtTurma.setText("Turma");
        jPanel7.add(txtTurma);
        txtTurma.setBounds(40, 140, 37, 16);

        lblTurma.addActionListener(this::lblTurmaActionPerformed);
        jPanel7.add(lblTurma);
        lblTurma.setBounds(40, 160, 125, 22);

        jTabbedPane2.addTab("Dados Acadêmicos", jPanel7);

        jPanel8.setBackground(new java.awt.Color(30, 30, 30));
        jPanel8.setPreferredSize(new java.awt.Dimension(800, 500));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nota 1");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nota 3");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nota 5");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nota 7");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nota 9");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nota 10");

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nota 8");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nota 6");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nota 4");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Nota 2");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9)
                    .addComponent(jTextField8)
                    .addComponent(jTextField7)
                    .addComponent(jTextField6)
                    .addComponent(jTextField4)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 531, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Notas", jPanel8);

        jPanel1.add(jTabbedPane2);
        jTabbedPane2.setBounds(0, 40, 800, 500);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("eMentor Plus - Aluno");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 10, 800, 16);

        jButton6.setBackground(new java.awt.Color(45, 60, 135));
        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Voltar");
        jButton6.setBorderPainted(false);
        jButton6.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton6.setPreferredSize(new java.awt.Dimension(100, 22));
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel1.add(jButton6);
        jButton6.setBounds(440, 550, 150, 25);

        jButton7.setBackground(new java.awt.Color(45, 60, 135));
        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Salvar Alterações");
        jButton7.setBorderPainted(false);
        jButton7.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton7.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton7.setPreferredSize(new java.awt.Dimension(100, 22));
        jButton7.addActionListener(this::jButton7ActionPerformed);
        jPanel1.add(jButton7);
        jButton7.setBounds(610, 550, 150, 25);

        jButton3.setBackground(new java.awt.Color(45, 60, 135));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Anterior");
        jButton3.setBorderPainted(false);
        jButton3.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel1.add(jButton3);
        jButton3.setBounds(40, 550, 130, 25);

        jButton2.setBackground(new java.awt.Color(45, 60, 135));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Próximo");
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel1.add(jButton2);
        jButton2.setBounds(190, 550, 130, 25);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void lblNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNomeActionPerformed

    private void lblDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDataNascimentoActionPerformed

    private void lblCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCPFActionPerformed

    private void lblTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTelefoneActionPerformed

    private void lblRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblRuaActionPerformed

    private void lblBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblBairroActionPerformed

    private void lblCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCidadeActionPerformed

    private void lblEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEstadoActionPerformed

    private void lblMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMatriculaActionPerformed

    private void lblPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPeriodoActionPerformed

    private void lblTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTurmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTurmaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        MenuOpçõesAluno MinhaJanela = new MenuOpçõesAluno();
        MinhaJanela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void lblNome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNome1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNome1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String matriculaStr = lblNome1.getText().trim();
    
        if (matriculaStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite uma matrícula para buscar.");
            return;
        }
    
        try {
            long matricula = Long.parseLong(matriculaStr);
            ConexoesMySQL conexao = new ConexoesMySQL();
            
            Aluno aluno = conexao.buscaAlunoPorMatricula(matricula);
            
            if (aluno != null) {
                lblNome.setText(aluno.getNome());
                lblDataNascimento.setText(aluno.getDataNascimento());
                lblCPF.setText(String.valueOf(aluno.getCPF()));
                lblTelefone.setText(aluno.getTelefone());
                lblRua.setText(aluno.getRua());
                lblBairro.setText(aluno.getBairro());
                lblCidade.setText(aluno.getCidade());
                lblEstado.setText(aluno.getEstado());
            
                lblMatricula.setText(String.valueOf(aluno.getMatricula()));
                lblPeriodo.setText(String.valueOf(aluno.getPeriodo()));
                lblTurma.setText(String.valueOf(aluno.getTurma()));
            
                float[] notas = aluno.getNotas();
                if (notas != null && notas.length >= 10) {
                    jTextField4.setText(String.valueOf(notas[0]));
                    jTextField14.setText(String.valueOf(notas[1]));
                    jTextField6.setText(String.valueOf(notas[2]));
                    jTextField13.setText(String.valueOf(notas[3]));
                    jTextField7.setText(String.valueOf(notas[4]));
                    jTextField12.setText(String.valueOf(notas[5]));
                    jTextField8.setText(String.valueOf(notas[6]));
                    jTextField11.setText(String.valueOf(notas[7]));
                    jTextField9.setText(String.valueOf(notas[8]));
                    jTextField10.setText(String.valueOf(notas[9]));
                }
                
                liberarCampos();
                lblNome1.setEditable(false);
                jButton2.setEnabled(true);
                jButton3.setEnabled(true);
                
                } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com a matrícula: " + matricula, "Não encontrado", javax.swing.JOptionPane.WARNING_MESSAGE);
                }    
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "A matrícula deve conter apenas números!", "Erro de formatação", javax.swing.JOptionPane.ERROR_MESSAGE);
        }       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        salvarTodasAlteracoes();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (listaAlunos.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            // Pega todos os alunos ordenados por Nome
            listaAlunos = conexao.recuperaTodosAlunos("p.Nome");
        }
        
        if (listaAlunos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum aluno cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual < listaAlunos.size() - 1) {
            indiceAtual++;
            exibirAlunoNaTela(listaAlunos.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no último registro da lista!", "Fim da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (listaAlunos.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            listaAlunos = conexao.recuperaTodosAlunos("p.Nome");
        }
        
        if (listaAlunos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum aluno cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual > 0) {
            indiceAtual--;
            exibirAlunoNaTela(listaAlunos.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no primeiro registro da lista!", "Início da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        java.awt.EventQueue.invokeLater(() -> new MenuAlterarAluno().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField lblBairro;
    private javax.swing.JTextField lblCPF;
    private javax.swing.JTextField lblCidade;
    private javax.swing.JTextField lblDataNascimento;
    private javax.swing.JTextField lblEstado;
    private javax.swing.JTextField lblMatricula;
    private javax.swing.JTextField lblNome;
    private javax.swing.JTextField lblNome1;
    private javax.swing.JTextField lblPeriodo;
    private javax.swing.JTextField lblRua;
    private javax.swing.JTextField lblTelefone;
    private javax.swing.JTextField lblTurma;
    private javax.swing.JLabel txtBairro;
    private javax.swing.JLabel txtCPF;
    private javax.swing.JLabel txtCidade;
    private javax.swing.JLabel txtDataNascimento;
    private javax.swing.JLabel txtEstado;
    private javax.swing.JLabel txtMatricula;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtNome1;
    private javax.swing.JLabel txtPeriodo;
    private javax.swing.JLabel txtRua;
    private javax.swing.JLabel txtTelefone;
    private javax.swing.JLabel txtTurma;
    // End of variables declaration//GEN-END:variables
}
