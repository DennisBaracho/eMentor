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
 * @author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
 */
public class MenuAlterarEgresso extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAlterarEgresso.class.getName());

    /**
     * Creates new form Cadastros
     */
    private List<Egresso> listaEgressos = new ArrayList<>();
    private int indiceAtual = -1;
    
    private void exibirEgressoNaTela(Egresso egresso) {
        if (egresso == null) return;
        
        lblNome.setText(egresso.getNome());
        lblDataNascimento.setText(egresso.getDataNascimento());
        lblCPF.setText(String.valueOf(egresso.getCPF()));
        lblTelefone.setText(egresso.getTelefone());
        lblRua.setText(egresso.getRua());
        lblBairro.setText(egresso.getBairro());
        lblCidade.setText(egresso.getCidade());
        lblEstado.setText(egresso.getEstado());
        lblProfissaoAtual.setText(egresso.getProfissaoAtual());
        jComboBox1.setSelectedItem(egresso.getFaixaSalarial());

        lblMatricula.setText(String.valueOf(egresso.getMatricula()));
        lblPeriodo.setText(String.valueOf(egresso.getPeriodo()));
        lblTurma.setText(String.valueOf(egresso.getTurma()));
        lblCursoAnterior.setText(egresso.getCursoAnterior());
        lblCursoAtual.setText(egresso.getCursoAtual());

        float[] notas = egresso.getNotas();
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
        lblProfissaoAtual1.setEditable(false); 
        lblCPF.setEditable(false);
        
        jButton1.setEnabled(true);
        jButton6.setEnabled(true);
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
        lblProfissaoAtual.setEditable(false);
        jComboBox1.setEnabled(false);
    
        lblMatricula.setEditable(false);
        lblPeriodo.setEditable(false);
        lblTurma.setEditable(false);
        lblCursoAnterior.setEditable(false);
        lblCursoAtual.setEditable(false);
    
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
    
        jButton5.setEnabled(false);
    }

    private void liberarCampos() {
        lblNome.setEditable(true);
        lblDataNascimento.setEditable(true);
        lblTelefone.setEditable(true);
        lblRua.setEditable(true);
        lblBairro.setEditable(true);
        lblCidade.setEditable(true);
        lblEstado.setEditable(true);
        lblProfissaoAtual.setEditable(true);
        jComboBox1.setEnabled(true);
    
        lblPeriodo.setEditable(true);
        lblTurma.setEditable(true);
        lblCursoAnterior.setEditable(true);
        lblCursoAtual.setEditable(true);
    
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
    
        jButton5.setEnabled(true);
    }

    private void salvarTodasAlteracoes() {
        try {
            if (jComboBox1.getSelectedIndex() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecione uma Faixa Salarial válida!", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
            }
            
            Egresso egressoAlterado = new Egresso();
        
            egressoAlterado.setNome(lblNome.getText());
            egressoAlterado.setDataNascimento(lblDataNascimento.getText());
            egressoAlterado.setCPF(Long.parseLong(lblCPF.getText()));
            egressoAlterado.setTelefone(lblTelefone.getText());
            egressoAlterado.setRua(lblRua.getText());
            egressoAlterado.setBairro(lblBairro.getText());
            egressoAlterado.setCidade(lblCidade.getText());
            egressoAlterado.setEstado(lblEstado.getText());
            egressoAlterado.setProfissaoAtual(lblProfissaoAtual.getText());
            egressoAlterado.setFaixaSalarial(jComboBox1.getSelectedItem().toString());
            
            egressoAlterado.setMatricula(Long.parseLong(lblMatricula.getText()));
            egressoAlterado.setPeriodo(Integer.parseInt(lblPeriodo.getText()));
            egressoAlterado.setTurma(Long.parseLong(lblTurma.getText()));
            egressoAlterado.setCursoAnterior(lblCursoAnterior.getText());
            egressoAlterado.setCursoAtual(lblCursoAtual.getText());
            
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
            egressoAlterado.setNotas(notas);
            
            ConexoesMySQL conexao = new ConexoesMySQL();
            conexao.alteraEgresso(egressoAlterado);
        
            bloquearCampos();
            lblProfissaoAtual1.setEditable(true);
            listaEgressos.clear();
            indiceAtual = -1;
        
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao preparar dados para alterar: " + e.getMessage(), "Erro de Validação", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public MenuAlterarEgresso() {
    IconFontSwing.register(FontAwesome.getIconFont());
        
        initComponents();
        
        this.setResizable(false); 
        
        this.setSize(800, 640); 
        
        this.setLocationRelativeTo(null);
        
        bloquearCampos();
        
        jButton6.setEnabled(false);
        jButton1.setEnabled(false); 
       
        jButton5.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 18, new java.awt.Color(255, 255, 255)));
        
        jButton2.setIcon(IconFontSwing.buildIcon(FontAwesome.SEARCH, 16, new java.awt.Color(255, 255, 255)));
        
        jButton3.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton6.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton1.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_RIGHT, 16, new java.awt.Color(255, 255, 255)));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
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
        lblProfissaoAtual = new javax.swing.JTextField();
        txtProfissaoAtual = new javax.swing.JLabel();
        txtFaixaSalarial = new javax.swing.JLabel();
        lblProfissaoAtual1 = new javax.swing.JTextField();
        txtProfissaoAtual1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lblMatricula = new javax.swing.JTextField();
        txtMatricula = new javax.swing.JLabel();
        txtPeriodo = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JTextField();
        txtTurma = new javax.swing.JLabel();
        lblTurma = new javax.swing.JTextField();
        lblCursoAnterior = new javax.swing.JTextField();
        txtCursoAnterior = new javax.swing.JLabel();
        lblCursoAtual = new javax.swing.JTextField();
        txtCursoAtual = new javax.swing.JLabel();
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
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
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
        lblNome.setBounds(30, 60, 125, 22);

        txtNome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtNome.setForeground(new java.awt.Color(255, 255, 255));
        txtNome.setText("Nome");
        jPanel6.add(txtNome);
        txtNome.setBounds(30, 40, 37, 16);

        lblDataNascimento.addActionListener(this::lblDataNascimentoActionPerformed);
        jPanel6.add(lblDataNascimento);
        lblDataNascimento.setBounds(30, 110, 125, 22);

        txtDataNascimento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtDataNascimento.setForeground(new java.awt.Color(255, 255, 255));
        txtDataNascimento.setText("Data de Nascimento");
        jPanel6.add(txtDataNascimento);
        txtDataNascimento.setBounds(30, 90, 108, 16);

        txtCPF.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCPF.setForeground(new java.awt.Color(255, 255, 255));
        txtCPF.setText("CPF");
        jPanel6.add(txtCPF);
        txtCPF.setBounds(30, 150, 37, 16);

        lblCPF.addActionListener(this::lblCPFActionPerformed);
        jPanel6.add(lblCPF);
        lblCPF.setBounds(30, 170, 125, 22);

        txtTelefone.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTelefone.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefone.setText("Telefone");
        jPanel6.add(txtTelefone);
        txtTelefone.setBounds(30, 200, 46, 16);

        lblTelefone.addActionListener(this::lblTelefoneActionPerformed);
        jPanel6.add(lblTelefone);
        lblTelefone.setBounds(30, 220, 125, 22);

        txtRua.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtRua.setForeground(new java.awt.Color(255, 255, 255));
        txtRua.setText("Rua");
        jPanel6.add(txtRua);
        txtRua.setBounds(30, 260, 37, 16);

        lblRua.addActionListener(this::lblRuaActionPerformed);
        jPanel6.add(lblRua);
        lblRua.setBounds(30, 280, 125, 22);

        txtBairro.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBairro.setForeground(new java.awt.Color(255, 255, 255));
        txtBairro.setText("Bairro");
        jPanel6.add(txtBairro);
        txtBairro.setBounds(30, 310, 37, 16);

        lblBairro.addActionListener(this::lblBairroActionPerformed);
        jPanel6.add(lblBairro);
        lblBairro.setBounds(30, 340, 125, 22);

        txtCidade.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCidade.setForeground(new java.awt.Color(255, 255, 255));
        txtCidade.setText("Cidade");
        jPanel6.add(txtCidade);
        txtCidade.setBounds(30, 370, 36, 16);

        lblCidade.addActionListener(this::lblCidadeActionPerformed);
        jPanel6.add(lblCidade);
        lblCidade.setBounds(30, 390, 125, 22);

        lblEstado.addActionListener(this::lblEstadoActionPerformed);
        jPanel6.add(lblEstado);
        lblEstado.setBounds(30, 450, 125, 22);

        txtEstado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtEstado.setForeground(new java.awt.Color(255, 255, 255));
        txtEstado.setText("Estado");
        jPanel6.add(txtEstado);
        txtEstado.setBounds(30, 430, 37, 16);

        lblProfissaoAtual.addActionListener(this::lblProfissaoAtualActionPerformed);
        jPanel6.add(lblProfissaoAtual);
        lblProfissaoAtual.setBounds(180, 60, 125, 22);

        txtProfissaoAtual.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProfissaoAtual.setForeground(new java.awt.Color(255, 255, 255));
        txtProfissaoAtual.setText("Profissão Atual");
        jPanel6.add(txtProfissaoAtual);
        txtProfissaoAtual.setBounds(180, 40, 90, 16);

        txtFaixaSalarial.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtFaixaSalarial.setForeground(new java.awt.Color(255, 255, 255));
        txtFaixaSalarial.setText("Faixa Salarial");
        jPanel6.add(txtFaixaSalarial);
        txtFaixaSalarial.setBounds(180, 90, 70, 16);

        lblProfissaoAtual1.addActionListener(this::lblProfissaoAtual1ActionPerformed);
        jPanel6.add(lblProfissaoAtual1);
        lblProfissaoAtual1.setBounds(630, 60, 120, 22);

        txtProfissaoAtual1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProfissaoAtual1.setForeground(new java.awt.Color(255, 255, 255));
        txtProfissaoAtual1.setText("CPF");
        jPanel6.add(txtProfissaoAtual1);
        txtProfissaoAtual1.setBounds(630, 40, 90, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione Uma Opção", "1 a 3 salários mínimos", "3 a 6 salários mínimos", "6 a 9 salários mínimos", "Mais que 9 salários mínimos" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        jPanel6.add(jComboBox1);
        jComboBox1.setBounds(180, 110, 120, 22);

        jButton2.setBackground(new java.awt.Color(45, 60, 135));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Pesquisar");
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel6.add(jButton2);
        jButton2.setBounds(630, 90, 120, 25);

        jTabbedPane2.addTab("Dados Pessoais", jPanel6);

        jPanel7.setBackground(new java.awt.Color(30, 30, 30));
        jPanel7.setLayout(null);

        lblMatricula.addActionListener(this::lblMatriculaActionPerformed);
        jPanel7.add(lblMatricula);
        lblMatricula.setBounds(30, 50, 125, 22);

        txtMatricula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(255, 255, 255));
        txtMatricula.setText("Matricula");
        jPanel7.add(txtMatricula);
        txtMatricula.setBounds(30, 30, 50, 16);

        txtPeriodo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPeriodo.setForeground(new java.awt.Color(255, 255, 255));
        txtPeriodo.setText("Período");
        jPanel7.add(txtPeriodo);
        txtPeriodo.setBounds(30, 80, 41, 16);

        lblPeriodo.addActionListener(this::lblPeriodoActionPerformed);
        jPanel7.add(lblPeriodo);
        lblPeriodo.setBounds(30, 100, 125, 22);

        txtTurma.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTurma.setForeground(new java.awt.Color(255, 255, 255));
        txtTurma.setText("Turma");
        jPanel7.add(txtTurma);
        txtTurma.setBounds(30, 140, 37, 16);

        lblTurma.addActionListener(this::lblTurmaActionPerformed);
        jPanel7.add(lblTurma);
        lblTurma.setBounds(30, 160, 125, 22);

        lblCursoAnterior.addActionListener(this::lblCursoAnteriorActionPerformed);
        jPanel7.add(lblCursoAnterior);
        lblCursoAnterior.setBounds(180, 50, 125, 22);

        txtCursoAnterior.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCursoAnterior.setForeground(new java.awt.Color(255, 255, 255));
        txtCursoAnterior.setText("Curso Anterior");
        jPanel7.add(txtCursoAnterior);
        txtCursoAnterior.setBounds(180, 30, 80, 16);

        lblCursoAtual.addActionListener(this::lblCursoAtualActionPerformed);
        jPanel7.add(lblCursoAtual);
        lblCursoAtual.setBounds(180, 100, 125, 22);

        txtCursoAtual.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCursoAtual.setForeground(new java.awt.Color(255, 255, 255));
        txtCursoAtual.setText("Curso Atual");
        jPanel7.add(txtCursoAtual);
        txtCursoAtual.setBounds(180, 80, 70, 16);

        jTabbedPane2.addTab("Dados Acadêmicos", jPanel7);

        jPanel8.setBackground(new java.awt.Color(30, 30, 30));

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
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(0, 547, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
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
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Histórico de Notas", jPanel8);

        jPanel1.add(jTabbedPane2);
        jTabbedPane2.setBounds(0, 40, 800, 510);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("eMentor Plus - Egresso");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 5, 800, 25);

        jButton3.setBackground(new java.awt.Color(45, 60, 135));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Voltar");
        jButton3.setBorderPainted(false);
        jButton3.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel1.add(jButton3);
        jButton3.setBounds(430, 560, 150, 25);

        jButton5.setBackground(new java.awt.Color(45, 60, 135));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Salvar Alterações");
        jButton5.setBorderPainted(false);
        jButton5.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton5.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel1.add(jButton5);
        jButton5.setBounds(610, 560, 150, 25);

        jButton1.setBackground(new java.awt.Color(45, 60, 135));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Próximo");
        jButton1.setBorderPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel1.add(jButton1);
        jButton1.setBounds(170, 560, 120, 25);

        jButton6.setBackground(new java.awt.Color(45, 60, 135));
        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Anterior");
        jButton6.setBorderPainted(false);
        jButton6.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel1.add(jButton6);
        jButton6.setBounds(30, 560, 120, 25);

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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void lblProfissaoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblProfissaoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblProfissaoAtualActionPerformed

    private void lblEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEstadoActionPerformed

    private void lblCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCidadeActionPerformed

    private void lblBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblBairroActionPerformed

    private void lblRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblRuaActionPerformed

    private void lblTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTelefoneActionPerformed

    private void lblCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCPFActionPerformed

    private void lblDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDataNascimentoActionPerformed

    private void lblNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNomeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (listaEgressos.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            listaEgressos = conexao.recuperaTodosEgressos();
        }
        
        if (listaEgressos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum egresso cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual < listaEgressos.size() - 1) {
            indiceAtual++;
            exibirEgressoNaTela(listaEgressos.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no último registro da lista!", "Fim da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lblCursoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCursoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCursoAtualActionPerformed

    private void lblCursoAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCursoAnteriorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCursoAnteriorActionPerformed

    private void lblTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTurmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTurmaActionPerformed

    private void lblPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPeriodoActionPerformed

    private void lblMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMatriculaActionPerformed

    private void lblProfissaoAtual1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblProfissaoAtual1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblProfissaoAtual1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        MenuOpçõesEgresso MinhaJanela = new MenuOpçõesEgresso();
        MinhaJanela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        salvarTodasAlteracoes();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String cpfStr = lblProfissaoAtual1.getText().trim();
    
        if (cpfStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite um CPF para buscar.");
            return;
        }

        try {
            long cpf = Long.parseLong(cpfStr);
            ConexoesMySQL conexao = new ConexoesMySQL();
            Egresso egresso = conexao.buscaEgressoPorCPF(cpf);

            if (egresso != null) {
                exibirEgressoNaTela(egresso); // Desenha os dados na tela e destrava as setas!
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Nenhum egresso encontrado com o CPF: " + cpf, "Não encontrado", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "O CPF deve conter apenas números (sem pontos ou traços)!", "Erro de formatação", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (listaEgressos.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            listaEgressos = conexao.recuperaTodosEgressos();
        }
        
        if (listaEgressos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum egresso cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual > 0) {
            indiceAtual--;
            exibirEgressoNaTela(listaEgressos.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no primeiro registro da lista!", "Início da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MenuAlterarEgresso().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JTextField lblCursoAnterior;
    private javax.swing.JTextField lblCursoAtual;
    private javax.swing.JTextField lblDataNascimento;
    private javax.swing.JTextField lblEstado;
    private javax.swing.JTextField lblMatricula;
    private javax.swing.JTextField lblNome;
    private javax.swing.JTextField lblPeriodo;
    private javax.swing.JTextField lblProfissaoAtual;
    private javax.swing.JTextField lblProfissaoAtual1;
    private javax.swing.JTextField lblRua;
    private javax.swing.JTextField lblTelefone;
    private javax.swing.JTextField lblTurma;
    private javax.swing.JLabel txtBairro;
    private javax.swing.JLabel txtCPF;
    private javax.swing.JLabel txtCidade;
    private javax.swing.JLabel txtCursoAnterior;
    private javax.swing.JLabel txtCursoAtual;
    private javax.swing.JLabel txtDataNascimento;
    private javax.swing.JLabel txtEstado;
    private javax.swing.JLabel txtFaixaSalarial;
    private javax.swing.JLabel txtMatricula;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtPeriodo;
    private javax.swing.JLabel txtProfissaoAtual;
    private javax.swing.JLabel txtProfissaoAtual1;
    private javax.swing.JLabel txtRua;
    private javax.swing.JLabel txtTelefone;
    private javax.swing.JLabel txtTurma;
    // End of variables declaration//GEN-END:variables
}
