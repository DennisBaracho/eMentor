/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ementor;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import jiconfont.swing.IconFontSwing;
import jiconfont.icons.font_awesome.FontAwesome;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
 */
public class MenuAlterarProfessor extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAlterarProfessor.class.getName());

    /**
     * Creates new form Cadastros
     */
    private List<Professor> listaProfessores = new ArrayList<>();
    private int indiceAtual = -1;
    
    private void exibirProfessorNaTela(Professor prof) {
        if (prof == null) return;
        
        lblNome.setText(prof.getNome());
        lblDataNascimento.setText(prof.getDataNascimento());
        lblCPF.setText(String.valueOf(prof.getCPF()));
        lblTelefone.setText(prof.getTelefone());
        lblRua.setText(prof.getRua());
        lblBairro.setText(prof.getBairro());
        lblCidade.setText(prof.getCidade());
        lblEstado.setText(prof.getEstado());
    
        lblDataAdmissao.setText(prof.getDataAdmissao());
        lblSalarioBruto.setText(String.format("%.2f", prof.getSalarioBruto()));
        lblSalarioBruto1.setText(String.format("%.2f", prof.calcularSalarioLiquido()));
    
        if (prof.isChefia()) {
            jCheckBox2.setSelected(true);
        } else {
            jCheckBox1.setSelected(true);
        }

        if (prof.isCoordenacao()) {
            jCheckBox4.setSelected(true);
        } else {
            jCheckBox3.setSelected(true);
        }
        
        liberarCampos();
        lblDataAdmissao1.setEditable(false);
        lblCPF.setEditable(false);
        
        jButton5.setEnabled(true);
        jButton1.setEnabled(true); 
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
    
        lblDataAdmissao.setEditable(false);
        lblSalarioBruto.setEditable(false);
        lblSalarioBruto1.setEditable(false); 

        jCheckBox1.setEnabled(false);
        jCheckBox2.setEnabled(false);
        jCheckBox3.setEnabled(false);
        jCheckBox4.setEnabled(false);
    
        jButton3.setEnabled(false);
    }

    private void liberarCampos() {
        lblNome.setEditable(true);
        lblDataNascimento.setEditable(true);
        lblTelefone.setEditable(true);
        lblRua.setEditable(true);
        lblBairro.setEditable(true);
        lblCidade.setEditable(true);
        lblEstado.setEditable(true);
    
        lblDataAdmissao.setEditable(true);
        lblSalarioBruto.setEditable(true);
    
        jCheckBox1.setEnabled(true);
        jCheckBox2.setEnabled(true);
        jCheckBox3.setEnabled(true);
        jCheckBox4.setEnabled(true);
    
        jButton3.setEnabled(true);
    }

    private void salvarTodasAlteracoes() {
        try {
            Professor profAlterado = new Professor();
            
            profAlterado.setNome(lblNome.getText());
            profAlterado.setDataNascimento(lblDataNascimento.getText());
            profAlterado.setCPF(Long.parseLong(lblCPF.getText()));
            profAlterado.setTelefone(lblTelefone.getText());
            profAlterado.setRua(lblRua.getText());
            profAlterado.setBairro(lblBairro.getText());
            profAlterado.setCidade(lblCidade.getText());
            profAlterado.setEstado(lblEstado.getText());
            
            profAlterado.setDataAdmissao(lblDataAdmissao.getText());
            profAlterado.setSalarioBruto(Double.parseDouble(lblSalarioBruto.getText().replace(",", ".")));
            
            profAlterado.setChefia(jCheckBox2.isSelected());   
            profAlterado.setCoordenacao(jCheckBox4.isSelected()); 

            BarraProgresso barra = new BarraProgresso();
            barra.setVisible(true);

            Timer timer = new Timer(30, null);
            timer.addActionListener(e -> {
                int valorAtual = barra.getBarra().getValue();
                if (valorAtual < 90) {
                    barra.getBarra().setValue(valorAtual + 1);
                }
            });
            timer.start();

            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    try {
                        ConexoesMySQL conexao = new ConexoesMySQL();
                        conexao.alteraProfessor(profAlterado);
                        return true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }

                @Override
                protected void done() {
                    timer.stop();
                    barra.getBarra().setValue(100);

                    Timer fechamento = new Timer(150, e -> {
                        barra.dispose();
                        
                        try {
                            boolean sucesso = get();
                            
                            if (sucesso) {
                                lblSalarioBruto1.setText(String.format("%.2f", profAlterado.calcularSalarioLiquido()));

                                JOptionPane.showMessageDialog(
                                    MenuAlterarProfessor.this, 
                                    "Dados do professor alterados com sucesso!", 
                                    "Sucesso", 
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                                
                                bloquearCampos();
                                lblDataAdmissao1.setEditable(true);
                                listaProfessores.clear();
                                indiceAtual = -1;

                            } else {
                                JOptionPane.showMessageDialog(
                                    MenuAlterarProfessor.this, 
                                    "Ocorreu um erro ao tentar atualizar os dados do professor no banco.", 
                                    "Erro no Banco de Dados", 
                                    JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    fechamento.setRepeats(false);
                    fechamento.start();
                }
            };
            worker.execute();
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao preparar dados para alterar: " + e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }
    public MenuAlterarProfessor() {
        
        IconFontSwing.register(FontAwesome.getIconFont());
        
        initComponents();
        
        this.setResizable(false); 
        
        this.setSize(800, 640); 
        
        this.setLocationRelativeTo(null);
        
        bloquearCampos();
        
        jButton1.setEnabled(false);
        jButton5.setEnabled(false);
        
        jButton3.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 18, new java.awt.Color(255, 255, 255)));
        
        jButton6.setIcon(IconFontSwing.buildIcon(FontAwesome.SEARCH, 16, new java.awt.Color(255, 255, 255)));
        
        jButton2.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton1.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_LEFT, 16, new java.awt.Color(255, 255, 255)));
        
        jButton5.setIcon(IconFontSwing.buildIcon(FontAwesome.CHEVRON_RIGHT, 16, new java.awt.Color(255, 255, 255)));
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
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
        txtDataAdmissao = new javax.swing.JLabel();
        lblDataAdmissao = new javax.swing.JTextField();
        txtSalarioBruto = new javax.swing.JLabel();
        lblSalarioBruto = new javax.swing.JTextField();
        lblSalarioBruto1 = new javax.swing.JTextField();
        txtSalarioBruto1 = new javax.swing.JLabel();
        lblDataAdmissao1 = new javax.swing.JTextField();
        txtDataAdmissao1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtSalarioBruto2 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        txtSalarioBruto3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

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
        lblNome.setBounds(30, 50, 125, 22);

        txtNome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtNome.setForeground(new java.awt.Color(255, 255, 255));
        txtNome.setText("Nome");
        jPanel6.add(txtNome);
        txtNome.setBounds(30, 30, 37, 16);

        lblDataNascimento.addActionListener(this::lblDataNascimentoActionPerformed);
        jPanel6.add(lblDataNascimento);
        lblDataNascimento.setBounds(30, 110, 125, 22);

        txtDataNascimento.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtDataNascimento.setForeground(new java.awt.Color(255, 255, 255));
        txtDataNascimento.setText("Data de Nascimento");
        jPanel6.add(txtDataNascimento);
        txtDataNascimento.setBounds(30, 80, 108, 16);

        txtCPF.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCPF.setForeground(new java.awt.Color(255, 255, 255));
        txtCPF.setText("CPF");
        jPanel6.add(txtCPF);
        txtCPF.setBounds(30, 140, 37, 16);

        lblCPF.addActionListener(this::lblCPFActionPerformed);
        jPanel6.add(lblCPF);
        lblCPF.setBounds(30, 160, 125, 22);

        txtTelefone.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTelefone.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefone.setText("Telefone");
        jPanel6.add(txtTelefone);
        txtTelefone.setBounds(30, 200, 50, 16);

        lblTelefone.addActionListener(this::lblTelefoneActionPerformed);
        jPanel6.add(lblTelefone);
        lblTelefone.setBounds(30, 220, 125, 22);

        txtRua.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtRua.setForeground(new java.awt.Color(255, 255, 255));
        txtRua.setText("Rua");
        jPanel6.add(txtRua);
        txtRua.setBounds(30, 250, 37, 16);

        lblRua.addActionListener(this::lblRuaActionPerformed);
        jPanel6.add(lblRua);
        lblRua.setBounds(30, 270, 125, 22);

        txtBairro.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBairro.setForeground(new java.awt.Color(255, 255, 255));
        txtBairro.setText("Bairro");
        jPanel6.add(txtBairro);
        txtBairro.setBounds(30, 310, 37, 16);

        lblBairro.addActionListener(this::lblBairroActionPerformed);
        jPanel6.add(lblBairro);
        lblBairro.setBounds(30, 330, 125, 22);

        txtCidade.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtCidade.setForeground(new java.awt.Color(255, 255, 255));
        txtCidade.setText("Cidade");
        jPanel6.add(txtCidade);
        txtCidade.setBounds(30, 360, 40, 16);

        lblCidade.addActionListener(this::lblCidadeActionPerformed);
        jPanel6.add(lblCidade);
        lblCidade.setBounds(30, 390, 125, 22);

        lblEstado.addActionListener(this::lblEstadoActionPerformed);
        jPanel6.add(lblEstado);
        lblEstado.setBounds(30, 440, 125, 22);

        txtEstado.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtEstado.setForeground(new java.awt.Color(255, 255, 255));
        txtEstado.setText("Estado");
        jPanel6.add(txtEstado);
        txtEstado.setBounds(30, 420, 37, 16);

        txtDataAdmissao.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtDataAdmissao.setForeground(new java.awt.Color(255, 255, 255));
        txtDataAdmissao.setText("Data de Admissão");
        jPanel6.add(txtDataAdmissao);
        txtDataAdmissao.setBounds(180, 30, 100, 16);

        lblDataAdmissao.addActionListener(this::lblDataAdmissaoActionPerformed);
        jPanel6.add(lblDataAdmissao);
        lblDataAdmissao.setBounds(180, 50, 125, 22);

        txtSalarioBruto.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalarioBruto.setForeground(new java.awt.Color(255, 255, 255));
        txtSalarioBruto.setText("Salario Bruto");
        jPanel6.add(txtSalarioBruto);
        txtSalarioBruto.setBounds(180, 80, 80, 16);

        lblSalarioBruto.addActionListener(this::lblSalarioBrutoActionPerformed);
        jPanel6.add(lblSalarioBruto);
        lblSalarioBruto.setBounds(180, 110, 125, 22);

        lblSalarioBruto1.addActionListener(this::lblSalarioBruto1ActionPerformed);
        jPanel6.add(lblSalarioBruto1);
        lblSalarioBruto1.setBounds(180, 160, 125, 22);

        txtSalarioBruto1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalarioBruto1.setForeground(new java.awt.Color(255, 255, 255));
        txtSalarioBruto1.setText("Chefia");
        jPanel6.add(txtSalarioBruto1);
        txtSalarioBruto1.setBounds(180, 210, 90, 16);

        lblDataAdmissao1.addActionListener(this::lblDataAdmissao1ActionPerformed);
        jPanel6.add(lblDataAdmissao1);
        lblDataAdmissao1.setBounds(640, 60, 120, 22);

        txtDataAdmissao1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtDataAdmissao1.setForeground(new java.awt.Color(255, 255, 255));
        txtDataAdmissao1.setText("CPF");
        jPanel6.add(txtDataAdmissao1);
        txtDataAdmissao1.setBounds(640, 40, 100, 16);

        buttonGroup1.add(jCheckBox1);
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Não");
        jPanel6.add(jCheckBox1);
        jCheckBox1.setBounds(180, 260, 84, 20);

        txtSalarioBruto2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalarioBruto2.setForeground(new java.awt.Color(255, 255, 255));
        txtSalarioBruto2.setText("Salario Líquido");
        jPanel6.add(txtSalarioBruto2);
        txtSalarioBruto2.setBounds(180, 140, 90, 16);

        buttonGroup1.add(jCheckBox2);
        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Sim");
        jCheckBox2.addActionListener(this::jCheckBox2ActionPerformed);
        jPanel6.add(jCheckBox2);
        jCheckBox2.setBounds(180, 230, 84, 20);

        buttonGroup2.add(jCheckBox3);
        jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Não");
        jPanel6.add(jCheckBox3);
        jCheckBox3.setBounds(180, 350, 84, 20);

        buttonGroup2.add(jCheckBox4);
        jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setText("Sim");
        jPanel6.add(jCheckBox4);
        jCheckBox4.setBounds(180, 320, 84, 20);

        txtSalarioBruto3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalarioBruto3.setForeground(new java.awt.Color(255, 255, 255));
        txtSalarioBruto3.setText("Coordenação");
        jPanel6.add(txtSalarioBruto3);
        txtSalarioBruto3.setBounds(180, 300, 90, 16);

        jButton6.setBackground(new java.awt.Color(45, 60, 135));
        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Pesquisar");
        jButton6.setBorderPainted(false);
        jButton6.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel6.add(jButton6);
        jButton6.setBounds(640, 90, 120, 25);

        jTabbedPane2.addTab("Dados Pessoais", jPanel6);

        jPanel1.add(jTabbedPane2);
        jTabbedPane2.setBounds(0, 30, 800, 510);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("eMentor Plus - Professor");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 10, 800, 16);

        jButton2.setBackground(new java.awt.Color(45, 60, 135));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Voltar");
        jButton2.setBorderPainted(false);
        jButton2.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel1.add(jButton2);
        jButton2.setBounds(440, 550, 150, 25);

        jButton3.setBackground(new java.awt.Color(45, 60, 135));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Salvar Alterações");
        jButton3.setBorderPainted(false);
        jButton3.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel1.add(jButton3);
        jButton3.setBounds(610, 550, 150, 25);

        jButton1.setBackground(new java.awt.Color(45, 60, 135));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Anterior");
        jButton1.setBorderPainted(false);
        jButton1.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel1.add(jButton1);
        jButton1.setBounds(20, 550, 120, 25);

        jButton5.setBackground(new java.awt.Color(45, 60, 135));
        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Próximo");
        jButton5.setBorderPainted(false);
        jButton5.setMaximumSize(new java.awt.Dimension(100, 22));
        jButton5.setMinimumSize(new java.awt.Dimension(100, 22));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel1.add(jButton5);
        jButton5.setBounds(160, 550, 120, 25);

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

    private void lblSalarioBrutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblSalarioBrutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalarioBrutoActionPerformed

    private void lblDataAdmissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDataAdmissaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDataAdmissaoActionPerformed

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

    private void lblSalarioBruto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblSalarioBruto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalarioBruto1ActionPerformed

    private void lblDataAdmissao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDataAdmissao1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDataAdmissao1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MenuOpçõesProfessor MinhaJanela = new MenuOpçõesProfessor();
        MinhaJanela.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (listaProfessores.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            listaProfessores = conexao.recuperaTodosProfessores();
        }
        
        if (listaProfessores.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum professor cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual > 0) {
            indiceAtual--;
            exibirProfessorNaTela(listaProfessores.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no primeiro registro da lista!", "Início da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        salvarTodasAlteracoes();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (listaProfessores.isEmpty()) {
            ConexoesMySQL conexao = new ConexoesMySQL();
            listaProfessores = conexao.recuperaTodosProfessores();
        }
        
        if (listaProfessores.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Não há nenhum professor cadastrado no banco de dados.");
            return;
        }
        
        if (indiceAtual < listaProfessores.size() - 1) {
            indiceAtual++;
            exibirProfessorNaTela(listaProfessores.get(indiceAtual));
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Você já está no último registro da lista!", "Fim da Lista", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String cpfStr = lblDataAdmissao1.getText().trim();
    
        if (cpfStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite um CPF para buscar.");
            return;
        }
    
        try {
            long cpf = Long.parseLong(cpfStr);
            ConexoesMySQL conexao = new ConexoesMySQL();
            Professor prof = conexao.buscaProfessorPorCPF(cpf);
        
            if (prof != null) {
                exibirProfessorNaTela(prof); // O método auxiliar já preenche tudo e destrava os botões!
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Nenhum professor encontrado com o CPF: " + cpf, "Não encontrado", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "O CPF deve conter apenas números!", "Erro de formatação", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MenuAlterarProfessor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField lblBairro;
    private javax.swing.JTextField lblCPF;
    private javax.swing.JTextField lblCidade;
    private javax.swing.JTextField lblDataAdmissao;
    private javax.swing.JTextField lblDataAdmissao1;
    private javax.swing.JTextField lblDataNascimento;
    private javax.swing.JTextField lblEstado;
    private javax.swing.JTextField lblNome;
    private javax.swing.JTextField lblRua;
    private javax.swing.JTextField lblSalarioBruto;
    private javax.swing.JTextField lblSalarioBruto1;
    private javax.swing.JTextField lblTelefone;
    private javax.swing.JLabel txtBairro;
    private javax.swing.JLabel txtCPF;
    private javax.swing.JLabel txtCidade;
    private javax.swing.JLabel txtDataAdmissao;
    private javax.swing.JLabel txtDataAdmissao1;
    private javax.swing.JLabel txtDataNascimento;
    private javax.swing.JLabel txtEstado;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtRua;
    private javax.swing.JLabel txtSalarioBruto;
    private javax.swing.JLabel txtSalarioBruto1;
    private javax.swing.JLabel txtSalarioBruto2;
    private javax.swing.JLabel txtSalarioBruto3;
    private javax.swing.JLabel txtTelefone;
    // End of variables declaration//GEN-END:variables
}
