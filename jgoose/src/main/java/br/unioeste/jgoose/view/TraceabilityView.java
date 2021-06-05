/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.view;

import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNHorizontal;
import br.unioeste.jgoose.controller.BPMNController;
import br.unioeste.jgoose.controller.Controller;
import br.unioeste.jgoose.controller.HorizontalBPMNTraceController;
import br.unioeste.jgoose.controller.HorizontalIStarTraceController;
import br.unioeste.jgoose.controller.HorizontalUseCaseTraceController;
import br.unioeste.jgoose.controller.VerticalTraceController;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNHorizontal;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceBPMNVertical;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceIStarHorizontal;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceIStarVertical;
import br.unioeste.jgoose.TraceabilityHorizontal.TraceUCHorizontal;
import br.unioeste.jgoose.model.FiltroDOC;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedStakeholders;
import br.unioeste.jgoose.model.TokensTraceability;
import br.unioeste.jgoose.model.TracedAtorSistema;
import br.unioeste.jgoose.model.TracedInformacaoExterna;
import br.unioeste.jgoose.model.TracedInformacaoOrganizacional;
import br.unioeste.jgoose.model.TracedObjetivoSistema;
import br.unioeste.jgoose.model.TracedRequisitos;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.StyleConstants.ALIGN_CENTER;

/**
 *
 * @author Victor Augusto Pozzan
 */
public class TraceabilityView extends javax.swing.JFrame {

    private static String FILE = "c:/temp/FirstPdf.pdf";
    private Integer type;
    private TokensTraceability lista;
    private TitledBorder titlePrint;
    private ArrayList<String[]> elementTracedReport;
    private static Matriz matriz;

    private final DefaultTableModel traceabilityInfo = new DefaultTableModel();

    /**
     * Creates new form TraceabilityView
     */
    public TraceabilityView(int type) {
        this.type = type;
        this.elementTracedReport = new ArrayList<>();
        initComponents();
        Image Icone;
        Icone = Toolkit.getDefaultToolkit().getImage("./src/main/resources/icons/jgoose.gif");
        setIconImage(Icone);
        this.setDefaultCloseOperation(TraceabilityView.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        configTraceabilityView();
    }

    private void configTraceabilityView() {
        switch (type) {
            case 1: //Horizontal BPMN
                lista = TraceBPMNHorizontal.getLista();
                labelTypeTraceability.setText("Traceability Horizontal");
                break;
            case 2: //Horizontal UC
                lista = TraceUCHorizontal.getLista();
                labelTypeTraceability.setText("Traceability Horizontal");
                break;
            case 3://Horizonal i*
                labelTypeTraceability.setText("Traceability Horizontal");
                lista = TraceIStarHorizontal.getLista();
                break;
            case 4://Vertical rastreabilidade vertical BPMN to UC
                labelTypeTraceability.setText("Vertical Traceability");
                lista = TraceBPMNVertical.getLista();
                break;
            case 5://Vertical rastreabilidade vertical i* to UC
                labelTypeTraceability.setText("Vertical Traceability");
                lista = TraceIStarVertical.getLista();
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnTraceability = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        choiceMatrixTrace = new javax.swing.JComboBox<>();
        tabsMenu2 = new javax.swing.JTabbedPane();
        atoresMapeados2 = new javax.swing.JScrollPane();
        tableTraceability = new javax.swing.JTable() {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jPanel3 = new javax.swing.JPanel();
        buttonSaveUseCases = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        labelTypeTraceability = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JGOOSE TRACEABILITY");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Choose Matriz Traceability"));

        btnTraceability.setBackground(new java.awt.Color(0, 204, 255));
        btnTraceability.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/camera.png"))); // NOI18N
        btnTraceability.setText("Rastrear");
        btnTraceability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraceabilityActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        choiceMatrixTrace.setBackground(new java.awt.Color(86, 160, 160));
        choiceMatrixTrace.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        choiceMatrixTrace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Informação Externa x Informação Externa ", "Informação Externa x Informação Organizacional", "Informação Organizacional x Informação Organizacional", "Informação Organizacional x Requisitos", "Objetivo do Sistema x Objetivo do Sistema", "Objetivo do Sistema x Requisitos", "Stakeholder x Stakeholder", "Stakeholder x Requisitos", "Requisitos x Requisitos", "Requisitos x Informação Externa", "Objetivo do Sistema x Informação Organizacional", "Objetivo do Sistema x Ator Sistema", "Ator Sistema x Stakeholder" }));
        choiceMatrixTrace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choiceMatrixTraceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(choiceMatrixTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(choiceMatrixTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnTraceability))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(btnTraceability)
                .addContainerGap())
        );

        tabsMenu2.setBackground(new java.awt.Color(204, 204, 204));
        tabsMenu2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tabsMenu2.setToolTipText("");

        tableTraceability.setAutoCreateRowSorter(true);
        tableTraceability.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Abbreviation", "Name", "Class", "Segment", "Artifact Type", "Model"
            }
        ));
        tableTraceability.setRequestFocusEnabled(false);
        tableTraceability.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTraceabilityMouseClicked(evt);
            }
        });
        atoresMapeados2.setViewportView(tableTraceability);

        tabsMenu2.addTab("Traced Elements", atoresMapeados2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "More Functions"));

        buttonSaveUseCases.setBackground(new java.awt.Color(255, 204, 204));
        buttonSaveUseCases.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        buttonSaveUseCases.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pdf.png"))); // NOI18N
        buttonSaveUseCases.setText("Save PDF ");
        buttonSaveUseCases.setToolTipText("");
        buttonSaveUseCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveUseCasesActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton2.setText("Edit");

        jButton3.setBackground(new java.awt.Color(204, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/x-button.png"))); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jTextField1.setText("Name");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Model", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(45, 42));

        jTextField2.setText("Model");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(45, 42));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stakeholder", "AtorSistema", "Informação Externa", "Informação Organizacional", "Objetivo do Sistema", "Requisito Funcional", "Requisito Não Funcional" }));
        jComboBox1.setToolTipText("");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Segment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jTextField5.setText("Segment");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField5)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonSaveUseCases)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(buttonSaveUseCases))
                .addContainerGap())
        );

        jButton2.getAccessibleContext().setAccessibleName("buttonEdit");
        jButton3.getAccessibleContext().setAccessibleName("buttonDelete");
        jButton1.getAccessibleContext().setAccessibleName("buttonAdd");

        labelTypeTraceability.setBackground(new java.awt.Color(204, 204, 204));
        labelTypeTraceability.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        labelTypeTraceability.setForeground(new java.awt.Color(204, 204, 204));

        jMenu1.setText("Sobre");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabsMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTypeTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTypeTraceability, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabsMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        );

        tabsMenu2.getAccessibleContext().setAccessibleName("Traced Elements");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choiceMatrixTraceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choiceMatrixTraceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_choiceMatrixTraceActionPerformed

    private void btnTraceabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraceabilityActionPerformed
        int indice = choiceMatrixTrace.getSelectedIndex();
        switch (type) {
            case 1: //Horizontal BPMN
                //try {
                    HorizontalBPMNTraceController.selectMatriz(indice);
                //} catch (Exception error) {
                    
                    //JOptionPane.showMessageDialog(this, "MATRIZ ERROR" + error.getMessage());
               // }
                break;
            case 2: //Horizontal UC
                try {
                    HorizontalUseCaseTraceController.selectMatriz(indice);
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(this, "MATRIZ ERROR" + error.getMessage());
                }
                break;
            case 3://Horizonal i*
                try {
                    HorizontalIStarTraceController.selectMatriz(indice);
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(this, "MATRIZ ERROR" + error.getMessage());
                }
                break;
            case 4://Vertical rastreabilidade vertical BPMN to UC
            case 5://Vertical rastreabilidade vertical i* to UC     
                try {
                    VerticalTraceController.selectMatriz(indice);
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(this, "MATRIZ ERROR" + error.getMessage());
                }
                break;

        }
    }//GEN-LAST:event_btnTraceabilityActionPerformed

    private void tableTraceabilityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTraceabilityMouseClicked
        int i = tableTraceability.getSelectedRow();

        jTextField1.setText(tableTraceability.getValueAt(i, 1).toString());
        jTextField2.setText(tableTraceability.getValueAt(i, 2).toString());
        jTextField5.setText(tableTraceability.getValueAt(i, 4).toString());

        String classEle = tableTraceability.getValueAt(i, 3).toString();

        switch (classEle) {
            case "Stakeholder":
                jComboBox1.setSelectedIndex(0);
                break;
            case "Ator Sistema":
                jComboBox1.setSelectedIndex(1);
                break;
            case "Informação Externa":
                jComboBox1.setSelectedIndex(2);
                break;
            case "Informação Organizacional":
                jComboBox1.setSelectedIndex(3);
                break;
            case "Objetivo Do Sistema":
                jComboBox1.setSelectedIndex(4);
                break;
            case "Requisitos":
                String s = tableTraceability.getValueAt(i, 1).toString();
                if(s.substring(0, 2) == "[RF"){
                    jComboBox1.setSelectedIndex(5);                    
                }else{
                    jComboBox1.setSelectedIndex(6);                 
                }
                break;

        }

    }//GEN-LAST:event_tableTraceabilityMouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final Object[] row = new Object[5];
        // jButton1.addActionListener(new ActionListener(){
            // @Override
            //  public void actionPerformed(ActionEvent e) {
                row[1] = jTextField1.getText();
                row[2] = jTextField2.getText();
                row[4] = jTextField5.getText();
                int i = jComboBox1.getSelectedIndex();
                String eleClass = " ";
                Integer cod;
                switch (i) {
                    case 0:
                    eleClass = "Stakeholder";
                    TracedStakeholders tracedStakeholders = new TracedStakeholders();
                    tracedStakeholders.setLabel(jTextField1.getText());
                    tracedStakeholders.setModel(jTextField2.getText());
                    cod = tracedStakeholders.getId() + 1000;
                    tracedStakeholders.setCode(Integer.toString(cod));
                    row[0] = tracedStakeholders.getAbreviacao();
                    lista.setStakeholders(tracedStakeholders);
                    break;
                    case 1:
                    eleClass = "Ator Sistema";
                    TracedAtorSistema tracedAtorSis = new TracedAtorSistema();
                    tracedAtorSis.setLabel(jTextField1.getText());
                    tracedAtorSis.setModel(jTextField2.getText());
                    cod = tracedAtorSis.getId() + 1000;
                    tracedAtorSis.setCode(Integer.toString(cod));
                    row[0] = tracedAtorSis.getAbreviacao();
                    lista.setAtorSistema(tracedAtorSis);
                    break;
                    case 2:
                    eleClass = "Informação Externa";
                    TracedInformacaoExterna tracedInfExt = new TracedInformacaoExterna();
                    tracedInfExt.setLabel(jTextField1.getText());
                    tracedInfExt.setModel(jTextField2.getText());
                    cod = tracedInfExt.getId() + 1000;
                    tracedInfExt.setCode(Integer.toString(cod));
                    row[0] = tracedInfExt.getAbreviacao();
                    lista.setInformacaoExterna(tracedInfExt);
                    break;
                    case 3:
                    eleClass = "Informação Organizacional";
                    TracedInformacaoOrganizacional tracedInfOrg = new TracedInformacaoOrganizacional();
                    tracedInfOrg.setLabel(jTextField1.getText());
                    tracedInfOrg.setModel(jTextField2.getText());
                    cod = tracedInfOrg.getId() + 1000;
                    tracedInfOrg.setCode(Integer.toString(cod));
                    row[0] = tracedInfOrg.getAbreviacao();
                    lista.setInformacaoOrg(tracedInfOrg);
                    break;
                    case 4:
                    eleClass = "Objetivo do Sistema";
                    TracedObjetivoSistema tracedObjSis = new TracedObjetivoSistema();
                    tracedObjSis.setLabel(jTextField1.getText());
                    tracedObjSis.setModel(jTextField2.getText());
                    cod = tracedObjSis.getId() + 1000;
                    tracedObjSis.setCode(Integer.toString(cod));
                    row[0] = tracedObjSis.getAbreviacao();
                    lista.setObjetivoSistema(tracedObjSis);
                    break;
                    case 5:
                    eleClass = "Requisito";
                    TracedRequisitos tracedReq = new TracedRequisitos();
                    tracedReq.setLabel(jTextField1.getText());
                    tracedReq.setModel(jTextField2.getText());
                    tracedReq.setAbreviacao("[RF");
                    cod = tracedReq.getId() + 1000;
                    tracedReq.setCode(Integer.toString(cod));
                    row[0] = tracedReq.getAbreviacao();
                    lista.setRequisitos(tracedReq);
                    break;
                    case 6:
                    eleClass = "Requisito";
                    TracedRequisitos tracedReqNF = new TracedRequisitos();
                    tracedReqNF.setLabel(jTextField1.getText());
                    tracedReqNF.setModel(jTextField2.getText());
                    tracedReqNF.setAbreviacao("[RNF");
                    cod = tracedReqNF.getId() + 1000;
                    tracedReqNF.setCode(Integer.toString(cod));
                    row[0] = tracedReqNF.getAbreviacao();
                    lista.setRequisitos(tracedReqNF);
                    break;
                    default:
                    eleClass = "Classe não add";
                    break;
                }

                row[3] = eleClass;

                traceabilityInfo.addRow(row);
                //     }
            //   });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int indice = tableTraceability.getSelectedRow();
        String abb = tableTraceability.getValueAt(indice, 0).toString();

        try {
            traceabilityInfo.removeRow(indice);
        } catch (Error e) {

        }

        int i = jComboBox1.getSelectedIndex();
        String eleClass = " ";
        Integer cod;
        switch (i) {
            case 0:
            for(int j=0; j < lista.getStakeholders().size(); j++){
                if(lista.getStakeholders().get(j).getAbreviacao() == abb){
                    lista.getStakeholders().remove(j);
                    break;
                }
            }
            break;
            case 1:
            for(int j=0; j < lista.getAtorSistema().size(); j++){
                if(lista.getAtorSistema().get(j).getAbreviacao() == abb){
                    lista.getAtorSistema().remove(j);
                    break;
                }
            }
            break;
            case 2:
            for(int j=0; j < lista.getInformcaoExterna().size(); j++){
                if(lista.getInformcaoExterna().get(j).getAbreviacao() == abb){
                    lista.getInformcaoExterna().remove(j);
                    break;
                }
            }
            break;
            case 3:
            for(int j=0; j < lista.getInformacaoOrg().size(); j++){
                if(lista.getInformacaoOrg().get(j).getAbreviacao() == abb){
                    lista.getInformacaoOrg().remove(j);
                    break;
                }
            }
            break;
            case 4:
            for(int j=0; j < lista.getObjetivoSistema().size(); j++){
                if(lista.getObjetivoSistema().get(j).getAbreviacao() == abb){
                    lista.getObjetivoSistema().remove(j);
                    break;
                }
            }
            break;
            case 5:
            case 6:
            for(int j=0; j < lista.getRequisitos().size(); j++){
                if(lista.getRequisitos().get(j).getAbreviacao() == abb ||
                    lista.getRequisitos().get(j).getAbreviacao() == abb){
                    System.out.println("aaaaaa: removed");
                    lista.getRequisitos().remove(j);
                    break;
                }
            }
            break;
            default:
            eleClass = "Classe não add";
            break;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buttonSaveUseCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveUseCasesActionPerformed
        Document document = new Document(PageSize.A4.rotate());

        String path = Controller.loadProperties();
        JFileChooser fileChooser = new JFileChooser(path);
        int resultado = fileChooser.showSaveDialog(null);
        Controller.saveProperties(fileChooser.getSelectedFile().getParent());

        try {
            File file = fileChooser.getSelectedFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getPath() + ".pdf"));
            document.open();
            document.add(new Paragraph("A Traceability PDF document."));

            PdfPTable table = new PdfPTable(5); // 5 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table
            float[] columnWidths = {1f, 2f, 1f, 2f, 1f};
            table.setWidths(columnWidths);
            String[] cabeçalho = {"Abreviation", "Name", "Model", "Class", "Segment"};

            for (int i = 0; i < 5; i++) {//Cabeçalho da tabela
                PdfPCell cell1 = new PdfPCell(new Paragraph(cabeçalho[i]));
                cell1.setBorderColor(BaseColor.BLUE);
                cell1.setPaddingLeft(10);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                cell1.setVerticalAlignment(ALIGN_MIDDLE);
                table.addCell(cell1);
            }

            for(int i = 0; i < lista.getAtorSistema().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getAtorSistema().get(i).getAbreviacao();
                vetor[1] = lista.getAtorSistema().get(i).getLabel();
                vetor[2] = lista.getAtorSistema().get(i).getModel();
                vetor[3] = lista.getAtorSistema().get(i).getClasse();
                vetor[4] = lista.getAtorSistema().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            for(int i = 0; i < lista.getStakeholders().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getStakeholders().get(i).getAbreviacao();
                vetor[1] = lista.getStakeholders().get(i).getLabel();
                vetor[2] = lista.getStakeholders().get(i).getModel();
                vetor[3] = lista.getStakeholders().get(i).getClasse();
                vetor[4] = lista.getStakeholders().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            for(int i = 0; i < lista.getInformcaoExterna().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getInformcaoExterna().get(i).getAbreviacao();
                vetor[1] = lista.getInformcaoExterna().get(i).getLabel();
                vetor[2] = lista.getInformcaoExterna().get(i).getModel();
                vetor[3] = lista.getInformcaoExterna().get(i).getClasse();
                vetor[4] = lista.getInformcaoExterna().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            for(int i = 0; i < lista.getInformacaoOrg().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getInformacaoOrg().get(i).getAbreviacao();
                vetor[1] = lista.getInformacaoOrg().get(i).getLabel();
                vetor[2] = lista.getInformacaoOrg().get(i).getModel();
                vetor[3] = lista.getInformacaoOrg().get(i).getClasse();
                vetor[4] = lista.getInformacaoOrg().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            for(int i = 0; i < lista.getObjetivoSistema().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getObjetivoSistema().get(i).getAbreviacao();
                vetor[1] = lista.getObjetivoSistema().get(i).getLabel();
                vetor[2] = lista.getObjetivoSistema().get(i).getModel();
                vetor[3] = lista.getObjetivoSistema().get(i).getClasse();
                vetor[4] = lista.getObjetivoSistema().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            for(int i = 0; i < lista.getRequisitos().size(); i++){
                String[] vetor = new String[5];
                vetor[0] = lista.getRequisitos().get(i).getAbreviacao();
                vetor[1] = lista.getRequisitos().get(i).getLabel();
                vetor[2] = lista.getRequisitos().get(i).getModel();
                vetor[3] = lista.getRequisitos().get(i).getClasse();
                vetor[4] = lista.getRequisitos().get(i).getFase();

                for (int j = 0; j < 5; j++) {
                    PdfPCell cell1 = new PdfPCell(new Paragraph(vetor[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }
            /*for (int i = 0; i < elementTracedReport.size(); i++) {//Conteúdo
                for (int j = 0; j < 5; j++) {
                    System.out.println("elementTracedReort:" + elementTracedReport.get(i)[j]);
                    PdfPCell cell1 = new PdfPCell(new Paragraph(elementTracedReport.get(i)[j]));
                    cell1.setBorderColor(BaseColor.BLUE);
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }*/
            document.add(table);

            //incluir matrizes
            for (int i = 0; i < 12; i++) {
                switch (type) {
                    case 1:
                    try {
                        matriz = HorizontalBPMNTraceController.propertiesMatriz(i);
                        document.add(new Paragraph(matriz.getTitle()));
                        PdfPTable tableMatriz = new PdfPTable(matriz.getSizeCol() + 1);
                        tableMatriz.setWidthPercentage(100); //Width 50%
                        tableMatriz.setSpacingBefore(1f); //Space before table
                        tableMatriz.setSpacingAfter(1f); //Space after table

                        PdfPCell cel1 = new PdfPCell(new Paragraph("<-"));
                        cel1.setBorderColor(BaseColor.BLUE);
                        cel1.setPaddingLeft(10);
                        cel1.setHorizontalAlignment(ALIGN_CENTER);
                        cel1.setVerticalAlignment(ALIGN_MIDDLE);
                        tableMatriz.addCell(cel1);

                        for (int l = 0; l < matriz.getSizeCol(); l++) {
                            cel1 = new PdfPCell(new Paragraph(matriz.getElementColumn().get(l).getAbreviacao()));
                            cel1.setBorderColor(BaseColor.BLUE);
                            cel1.setPaddingLeft(10);
                            cel1.setHorizontalAlignment(ALIGN_CENTER);
                            cel1.setVerticalAlignment(ALIGN_MIDDLE);
                            tableMatriz.addCell(cel1);
                        }
                        boolean marcado = false;
                        for (int l = 0; l < matriz.getSizeRow(); l++) {
                            cel1 = new PdfPCell(new Paragraph(matriz.getElementRow().get(l).getAbreviacao()));
                            cel1.setBorderColor(BaseColor.BLUE);
                            cel1.setPaddingLeft(10);
                            cel1.setHorizontalAlignment(ALIGN_CENTER);
                            cel1.setVerticalAlignment(ALIGN_MIDDLE);
                            tableMatriz.addCell(cel1);
                            for (int k = 0; k < matriz.getElementColumn().size(); k++) {
                                for (int j = 0; j < matriz.getElementRow().get(l).getListConcflicts().size(); j++) {
                                    if (matriz.getElementRow().get(l).getListConcflicts().get(j)[0] == matriz.getElementColumn().get(k).getCode()) {
                                        if (matriz.isMatrizQuadrada() && l != k) {
                                            PdfPCell cell1 = new PdfPCell(new Paragraph(matriz.getElementRow().get(l).getListConcflicts().get(j)[1]));
                                            cell1.setBorderColor(BaseColor.BLUE);
                                            cell1.setPaddingLeft(10);
                                            cell1.setHorizontalAlignment(ALIGN_CENTER);
                                            cell1.setVerticalAlignment(ALIGN_MIDDLE);
                                            tableMatriz.addCell(cell1);
                                            marcado = true;
                                            j = matriz.getElementRow().get(l).getListConcflicts().size();
                                            break;
                                            //table.setValueAt(elementCol.get(l).getListConcflicts().get(j)[1], k, l);
                                        } else if (!matriz.isMatrizQuadrada()) {
                                            PdfPCell cell1 = new PdfPCell(new Paragraph(matriz.getElementRow().get(l).getListConcflicts().get(j)[1]));
                                            cell1.setBorderColor(BaseColor.BLUE);
                                            cell1.setPaddingLeft(10);
                                            cell1.setHorizontalAlignment(ALIGN_CENTER);
                                            cell1.setVerticalAlignment(ALIGN_MIDDLE);
                                            tableMatriz.addCell(cell1);
                                            marcado = true;
                                            break;
                                            // table.setValueAt(elementCol.get(l).getListConcflicts().get(j)[1], k, l);
                                        }
                                    }
                                }
                                if (!marcado) {
                                    PdfPCell cell1 = new PdfPCell(new Paragraph(" "));
                                    cell1.setBorderColor(BaseColor.BLUE);
                                    cell1.setPaddingLeft(10);
                                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                                    tableMatriz.addCell(cell1);
                                }
                                marcado = false;
                            }

                        }

                        document.add(tableMatriz);
                    } catch (Error e) {
                        System.out.println("Error: " + e);
                    }
                    break;
                }
            }

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //this.saveDocTraceability(tableTraceability);
    }//GEN-LAST:event_buttonSaveUseCasesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane atoresMapeados2;
    private javax.swing.JButton btnTraceability;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton buttonSaveUseCases;
    private javax.swing.JComboBox<String> choiceMatrixTrace;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel labelTypeTraceability;
    private javax.swing.JTable tableTraceability;
    private javax.swing.JTabbedPane tabsMenu2;
    // End of variables declaration//GEN-END:variables

    /**
     * Atualiza as Tabelas relacionadas à rastreabilidade
     *
     */
    public void updateTableBPMNHorizontalTraceability() {
        /*
         * Atualiza Informações Arquivo
         */
        DefaultTableModel traceabilityInfo = new DefaultTableModel();
        traceabilityInfo.addColumn("Abreviation");
        traceabilityInfo.addColumn("Name");
        traceabilityInfo.addColumn("Model");
        traceabilityInfo.addColumn("Class");
        traceabilityInfo.addColumn("Segment");
        // adiciona os dados dos casos de uso no modelo da tabela
        for (int i = 0; i < HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().size(); i++) {
            String vetStakeholder[] = new String[5];
            vetStakeholder[0] = HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().get(i).getAbreviacao();
            vetStakeholder[1] = HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().get(i).getLabel();
            vetStakeholder[2] = HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().get(i).getModel();
            vetStakeholder[3] = HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().get(i).getClasse();
            vetStakeholder[4] = HorizontalBPMNTraceController.getTokensTraceability().getStakeholders().get(i).getFase();
            elementTracedReport.add(vetStakeholder);
            traceabilityInfo.addRow(vetStakeholder);
        }
        for (int i = 0; i < HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().size(); i++) {
            String vetInfExterna[] = new String[5];
            vetInfExterna[0] = HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().get(i).getAbreviacao();
            vetInfExterna[1] = HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().get(i).getLabel();
            vetInfExterna[2] = HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().get(i).getModel();
            vetInfExterna[3] = HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().get(i).getClasse();
            vetInfExterna[4] = HorizontalBPMNTraceController.getTokensTraceability().getInformcaoExterna().get(i).getFase();
            elementTracedReport.add(vetInfExterna);
            traceabilityInfo.addRow(vetInfExterna);
        }
        for (int i = 0; i < HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().size(); i++) {
            String vetInfOrganizacional[] = new String[5];
            vetInfOrganizacional[0] = HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().get(i).getAbreviacao();
            vetInfOrganizacional[1] = HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().get(i).getLabel();
            vetInfOrganizacional[2] = HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().get(i).getModel();
            vetInfOrganizacional[3] = HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().get(i).getClasse();
            vetInfOrganizacional[4] = HorizontalBPMNTraceController.getTokensTraceability().getInformacaoOrg().get(i).getFase();
            elementTracedReport.add(vetInfOrganizacional);
            traceabilityInfo.addRow(vetInfOrganizacional);
        }
        for (int i = 0; i < HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().size(); i++) {
            String vetObjetivoSistema[] = new String[5];
            vetObjetivoSistema[0] = HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().get(i).getAbreviacao();
            vetObjetivoSistema[1] = HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().get(i).getLabel();
            vetObjetivoSistema[2] = HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().get(i).getModel();
            vetObjetivoSistema[3] = HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().get(i).getClasse();
            vetObjetivoSistema[4] = HorizontalBPMNTraceController.getTokensTraceability().getObjetivoSistema().get(i).getFase();
            elementTracedReport.add(vetObjetivoSistema);
            traceabilityInfo.addRow(vetObjetivoSistema);
        }
        for (int i = 0; i < HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().size(); i++) {
            String vetRequisitos[] = new String[5];
            vetRequisitos[0] = HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().get(i).getAbreviacao();
            vetRequisitos[1] = HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().get(i).getLabel();
            vetRequisitos[2] = HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().get(i).getModel();
            vetRequisitos[3] = HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().get(i).getClasse();
            vetRequisitos[4] = HorizontalBPMNTraceController.getTokensTraceability().getRequisitos().get(i).getFase();
            elementTracedReport.add(vetRequisitos);
            traceabilityInfo.addRow(vetRequisitos);
        }

        tableTraceability.setModel(traceabilityInfo);
        tableTraceability.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableTraceability.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableTraceability.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTraceability.getColumnModel().getColumn(3).setPreferredWidth(300);
        tableTraceability.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void updateTableUCHorizontalTraceability() {
        DefaultTableModel traceabilityInfo = new DefaultTableModel();
        traceabilityInfo.addColumn("Abreviation");
        traceabilityInfo.addColumn("Name");
        traceabilityInfo.addColumn("Model");//2
        traceabilityInfo.addColumn("Class");
        traceabilityInfo.addColumn("Segment");

        String vetStakeholder[] = new String[5];
        for (int i = 0; i < HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().size(); i++) {
            vetStakeholder[0] = HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().get(i).getAbreviacao();
            vetStakeholder[1] = HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().get(i).getLabel();
            vetStakeholder[2] = HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().get(i).getModel();
            vetStakeholder[3] = HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().get(i).getClasse();
            vetStakeholder[4] = HorizontalUseCaseTraceController.getTokensTraceability().getStakeholders().get(i).getFase();
            traceabilityInfo.addRow(vetStakeholder);
        }
        String vetInfExterna[] = new String[5];
        for (int i = 0; i < HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().size(); i++) {
            vetInfExterna[0] = HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().get(i).getAbreviacao();
            vetInfExterna[1] = HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().get(i).getLabel();
            vetInfExterna[2] = HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().get(i).getModel();
            vetInfExterna[3] = HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().get(i).getClasse();
            vetInfExterna[4] = HorizontalUseCaseTraceController.getTokensTraceability().getInformcaoExterna().get(i).getFase();
            traceabilityInfo.addRow(vetInfExterna);
        }
        String vetInfOrganizacional[] = new String[5];
        for (int i = 0; i < HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().size(); i++) {
            vetInfOrganizacional[0] = HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().get(i).getAbreviacao();
            vetInfOrganizacional[1] = HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().get(i).getLabel();
            vetInfOrganizacional[2] = HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().get(i).getModel();
            vetInfOrganizacional[3] = HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().get(i).getClasse();
            vetInfOrganizacional[4] = HorizontalUseCaseTraceController.getTokensTraceability().getInformacaoOrg().get(i).getFase();
            traceabilityInfo.addRow(vetInfOrganizacional);
        }
        String vetObjetivoSistema[] = new String[5];
        for (int i = 0; i < HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().size(); i++) {
            vetObjetivoSistema[0] = HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().get(i).getAbreviacao();
            vetObjetivoSistema[1] = HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().get(i).getLabel();
            vetObjetivoSistema[2] = HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().get(i).getModel();
            vetObjetivoSistema[3] = HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().get(i).getClasse();
            vetObjetivoSistema[4] = HorizontalUseCaseTraceController.getTokensTraceability().getObjetivoSistema().get(i).getFase();
            traceabilityInfo.addRow(vetObjetivoSistema);
        }
        String vetRequisitos[] = new String[5];
        for (int i = 0; i < HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().size(); i++) {
            vetRequisitos[0] = HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().get(i).getAbreviacao();
            vetRequisitos[1] = HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().get(i).getLabel();
            vetRequisitos[2] = HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().get(i).getModel();
            vetRequisitos[3] = HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().get(i).getClasse();
            vetRequisitos[4] = HorizontalUseCaseTraceController.getTokensTraceability().getRequisitos().get(i).getFase();
            traceabilityInfo.addRow(vetRequisitos);
        }

        tableTraceability.setModel(traceabilityInfo);
        tableTraceability.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableTraceability.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableTraceability.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTraceability.getColumnModel().getColumn(3).setPreferredWidth(300);
        tableTraceability.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void updateTableIStarHorizontalTraceability() {
        DefaultTableModel traceabilityInfo = new DefaultTableModel();
        traceabilityInfo.addColumn("Abreviation");//0
        traceabilityInfo.addColumn("Name");//1
        traceabilityInfo.addColumn("Model");//2
        traceabilityInfo.addColumn("Class");//3
        traceabilityInfo.addColumn("Segment");//4
        String vetActorsystem[] = new String[5];
        // adiciona os dados dos casos de uso no modelo da tabela
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().size(); i++) {
            vetActorsystem[0] = HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().get(i).getAbreviacao();
            vetActorsystem[1] = HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().get(i).getLabel();
            vetActorsystem[2] = HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().get(i).getModel();
            vetActorsystem[3] = HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().get(i).getClasse();
            vetActorsystem[4] = HorizontalIStarTraceController.getTokensTraceability().getAtorSistema().get(i).getFase();
            traceabilityInfo.addRow(vetActorsystem);
        }
        String vetStakeholder[] = new String[5];
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getStakeholders().size(); i++) {
            vetStakeholder[0] = HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(i).getAbreviacao();
            vetStakeholder[1] = HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(i).getLabel();
            vetStakeholder[2] = HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(i).getModel();
            vetStakeholder[3] = HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(i).getClasse();
            vetStakeholder[4] = HorizontalIStarTraceController.getTokensTraceability().getStakeholders().get(i).getFase();
            traceabilityInfo.addRow(vetStakeholder);
        }
        String vetInfExterna[] = new String[5];
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().size(); i++) {
            vetInfExterna[0] = HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().get(i).getAbreviacao();
            vetInfExterna[1] = HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().get(i).getLabel();
            vetInfExterna[2] = HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().get(i).getModel();
            vetInfExterna[3] = HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().get(i).getClasse();
            vetInfExterna[4] = HorizontalIStarTraceController.getTokensTraceability().getInformcaoExterna().get(i).getFase();
            traceabilityInfo.addRow(vetInfExterna);
        }
        String vetInfOrganizacional[] = new String[5];
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().size(); i++) {
            vetInfOrganizacional[0] = HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().get(i).getAbreviacao();
            vetInfOrganizacional[1] = HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().get(i).getLabel();
            vetInfOrganizacional[2] = HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().get(i).getModel();
            vetInfOrganizacional[3] = HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().get(i).getClasse();
            vetInfOrganizacional[4] = HorizontalIStarTraceController.getTokensTraceability().getInformacaoOrg().get(i).getFase();
            traceabilityInfo.addRow(vetInfOrganizacional);
        }
        String vetObjetivoSistema[] = new String[5];
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().size(); i++) {
            vetObjetivoSistema[0] = HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().get(i).getAbreviacao();
            vetObjetivoSistema[1] = HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().get(i).getLabel();
            vetObjetivoSistema[2] = HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().get(i).getModel();
            vetObjetivoSistema[3] = HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().get(i).getClasse();
            vetObjetivoSistema[4] = HorizontalIStarTraceController.getTokensTraceability().getObjetivoSistema().get(i).getFase();
            traceabilityInfo.addRow(vetObjetivoSistema);
        }
        String vetRequisitos[] = new String[5];
        for (int i = 0; i < HorizontalIStarTraceController.getTokensTraceability().getRequisitos().size(); i++) {
            vetRequisitos[0] = HorizontalIStarTraceController.getTokensTraceability().getRequisitos().get(i).getAbreviacao();
            vetRequisitos[1] = HorizontalIStarTraceController.getTokensTraceability().getRequisitos().get(i).getLabel();
            vetRequisitos[2] = HorizontalIStarTraceController.getTokensTraceability().getRequisitos().get(i).getModel();
            vetRequisitos[3] = HorizontalIStarTraceController.getTokensTraceability().getRequisitos().get(i).getClasse();
            vetRequisitos[4] = HorizontalIStarTraceController.getTokensTraceability().getRequisitos().get(i).getFase();
            traceabilityInfo.addRow(vetRequisitos);
        }

        tableTraceability.setModel(traceabilityInfo);
        tableTraceability.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableTraceability.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableTraceability.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTraceability.getColumnModel().getColumn(3).setPreferredWidth(300);
        tableTraceability.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void updateTableVerticalBPMNtoUCTraceability() {
        DefaultTableModel traceabilityInfo = new DefaultTableModel();
        traceabilityInfo.addColumn("Abreviation");//0
        traceabilityInfo.addColumn("Name");//1
        traceabilityInfo.addColumn("Model");//2
        traceabilityInfo.addColumn("Class");//3
        traceabilityInfo.addColumn("Segment");//4
        String vetAtorSistema[] = new String[5];
        // adiciona os dados dos casos de uso no modelo da tabela
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getAtorSistema().size(); i++) {
            vetAtorSistema[0] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getAbreviacao();
            vetAtorSistema[1] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getLabel();
            vetAtorSistema[2] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getModel();
            vetAtorSistema[3] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getClasse();
            vetAtorSistema[4] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getFase();
            traceabilityInfo.addRow(vetAtorSistema);
        }
        String vetStakeholder[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getStakeholders().size(); i++) {
            vetStakeholder[0] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getAbreviacao();
            vetStakeholder[1] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getLabel();
            vetStakeholder[2] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getModel();
            vetStakeholder[3] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getClasse();
            vetStakeholder[4] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getFase();
            traceabilityInfo.addRow(vetStakeholder);
        }
        String vetInfExterna[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getInformcaoExterna().size(); i++) {
            vetInfExterna[0] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getAbreviacao();
            vetInfExterna[1] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getLabel();
            vetInfExterna[2] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getModel();
            vetInfExterna[3] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getClasse();
            vetInfExterna[4] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getFase();
            traceabilityInfo.addRow(vetInfExterna);
        }
        String vetInfOrganizacional[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getInformacaoOrg().size(); i++) {
            vetInfOrganizacional[0] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getAbreviacao();
            vetInfOrganizacional[1] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getLabel();
            vetInfOrganizacional[2] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getModel();
            vetInfOrganizacional[3] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getClasse();
            vetInfOrganizacional[4] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getFase();
            traceabilityInfo.addRow(vetInfOrganizacional);
        }
        String vetObjetivoSistema[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getObjetivoSistema().size(); i++) {
            vetObjetivoSistema[0] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getAbreviacao();
            vetObjetivoSistema[1] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getLabel();
            vetObjetivoSistema[2] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getModel();
            vetObjetivoSistema[3] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getClasse();
            vetObjetivoSistema[4] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getFase();
            traceabilityInfo.addRow(vetObjetivoSistema);
        }
        String vetRequisitos[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getRequisitos().size(); i++) {
            vetRequisitos[0] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getAbreviacao();
            vetRequisitos[1] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getLabel();
            vetRequisitos[2] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getModel();
            vetRequisitos[3] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getClasse();
            vetRequisitos[4] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getFase();
            traceabilityInfo.addRow(vetRequisitos);
        }

        tableTraceability.setModel(traceabilityInfo);
        tableTraceability.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableTraceability.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableTraceability.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTraceability.getColumnModel().getColumn(3).setPreferredWidth(300);
        tableTraceability.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void updateTableVerticalIStartoUCTraceability() {
        DefaultTableModel traceabilityInfo = new DefaultTableModel();
        traceabilityInfo.addColumn("Abreviation");//0
        traceabilityInfo.addColumn("Name");//1
        traceabilityInfo.addColumn("Model");//2
        traceabilityInfo.addColumn("Class");//3
        traceabilityInfo.addColumn("Segment");//4
        String vetActorSystem[] = new String[5];
        // adiciona os dados dos casos de uso no modelo da tabela
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getAtorSistema().size(); i++) {
            vetActorSystem[0] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getAbreviacao();
            vetActorSystem[1] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getLabel();
            vetActorSystem[2] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getModel();
            vetActorSystem[3] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getClasse();
            vetActorSystem[4] = VerticalTraceController.getTokensVertical().getAtorSistema().get(i).getFase();
            traceabilityInfo.addRow(vetActorSystem);
        }
        String vetStakeholder[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getStakeholders().size(); i++) {
            vetStakeholder[0] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getAbreviacao();
            vetStakeholder[1] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getLabel();
            vetStakeholder[2] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getModel();
            vetStakeholder[3] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getClasse();
            vetStakeholder[4] = VerticalTraceController.getTokensVertical().getStakeholders().get(i).getFase();
            traceabilityInfo.addRow(vetStakeholder);
        }
        String vetInfExterna[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getInformcaoExterna().size(); i++) {
            vetInfExterna[0] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getAbreviacao();
            vetInfExterna[1] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getLabel();
            vetInfExterna[2] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getModel();
            vetInfExterna[3] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getClasse();
            vetInfExterna[4] = VerticalTraceController.getTokensVertical().getInformcaoExterna().get(i).getFase();
            traceabilityInfo.addRow(vetInfExterna);
        }
        String vetInfOrganizacional[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getInformacaoOrg().size(); i++) {
            vetInfOrganizacional[0] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getAbreviacao();
            vetInfOrganizacional[1] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getLabel();
            vetInfOrganizacional[2] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getModel();
            vetInfOrganizacional[3] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getClasse();
            vetInfOrganizacional[4] = VerticalTraceController.getTokensVertical().getInformacaoOrg().get(i).getFase();
            traceabilityInfo.addRow(vetInfOrganizacional);
        }
        String vetObjetivoSistema[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getObjetivoSistema().size(); i++) {
            vetObjetivoSistema[0] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getAbreviacao();
            vetObjetivoSistema[1] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getLabel();
            vetObjetivoSistema[2] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getModel();
            vetObjetivoSistema[3] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getClasse();
            vetObjetivoSistema[4] = VerticalTraceController.getTokensVertical().getObjetivoSistema().get(i).getFase();
            traceabilityInfo.addRow(vetObjetivoSistema);
        }
        String vetRequisitos[] = new String[5];
        for (int i = 0; i < VerticalTraceController.getTokensVertical().getRequisitos().size(); i++) {
            vetRequisitos[0] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getAbreviacao();
            vetRequisitos[1] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getLabel();
            vetRequisitos[2] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getModel();
            vetRequisitos[3] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getClasse();
            vetRequisitos[4] = VerticalTraceController.getTokensVertical().getRequisitos().get(i).getFase();
            traceabilityInfo.addRow(vetRequisitos);
        }

        tableTraceability.setModel(traceabilityInfo);
        tableTraceability.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableTraceability.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableTraceability.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableTraceability.getColumnModel().getColumn(3).setPreferredWidth(300);
        tableTraceability.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

}
