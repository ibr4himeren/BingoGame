/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tombala;

import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ieren
 */
public class IbrahimGokdemirCard extends javax.swing.JFrame {

    /**
     * Creates new form Card1
     */
    public IbrahimGokdemirCard() {
        initComponents();
    }

    public static int[][] createMatrix() {
        int[][] card = new int[3][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                card[i][j] = -1;
            }}
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                card[i][j] = 1;
            } }
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int k = random.nextInt(9);
                int temp = card[i][j];
                card[i][j] = card[i][k];
                card[i][k] = temp;
            }}
        int temp = 0;
        int bef = 0;
        int past = 0;
        int currentno = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                if ((card[j][i] == 1)) {
                    while (true) {
                        currentno = random.nextInt(9);
                        if (currentno != bef && past != currentno) {
                            card[j][i] = currentno + (temp * 10);
                            break;
                        }}
                    past = bef;
                    bef = currentno;
                } }
            temp++;}
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if ((card[j][i] != -1) && (card[k][i] != -1) && (k > j) && (card[j][i] > card[k][i])) {
                        int temp1 = card[j][i];
                        card[j][i] = card[k][i];
                        card[k][i] = temp1;   }  } }   }
        return card; }

    public static int[][] createManualMatrix1() {
        int[][] manualMatrix1 = {
            {1, -1, 23, -1, 45, -1, 67, -1, 89},
            {-1, 12, -1, 34, 49, 56, -1, 78, -1},
            {7, 16, 25, 38, -1, -1, 69, -1, -1}
        };
        return manualMatrix1;
    }

    public static int[][] createManualMatrix2() {
        int[][] manualMatrix2 = {
            {-1, 11, 24, -1, 45, 52, -1, -1, 81},
            {-1, 13, -1, -1, -1, 56, 66, 76, 85},
            {8, -1, 28, -1, 47, -1, 68, 77, -1}
        };
        return manualMatrix2;
    }

    private void loadMultiListToTable(IbrahimGokdemirMLinkedList<Integer> list, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(3);
        model.setColumnCount(9);

        IbrahimGokdemirNode<Integer> currentRow = list.getHead();
        int rowIndex = 0;
        while (currentRow != null) {
            IbrahimGokdemirNode<Integer> currentColumn = currentRow;
            int columnIndex = 0;
            while (currentColumn != null) {
                if (currentColumn.getData() == -1) {
                    model.setValueAt("", rowIndex, columnIndex);
                } else {
                    model.setValueAt(currentColumn.getData(), rowIndex, columnIndex);
                }
                currentColumn = currentColumn.getNext();
                columnIndex++;
            }
            currentRow = currentRow.getDown();
            rowIndex++;
        }
    }

    private void takeANumber() {
        Random random = new Random();
        int tableIndex = random.nextInt(2);
        JTable selectedTable = tableIndex == 0 ? jTable1 : jTable2;

        boolean tableEmpty = true;
        for (int i = 0; i < selectedTable.getRowCount(); i++) {
            for (int j = 0; j < selectedTable.getColumnCount(); j++) {
                Object cellValue = selectedTable.getValueAt(i, j);

                if (cellValue != null && !cellValue.toString().isEmpty() && !cellValue.toString().equals("X")) {
                    tableEmpty = false;
                    break;
                }
            }
            if (!tableEmpty) {
                break;
            }
        }

        if (tableEmpty) {
            JOptionPane.showMessageDialog(this, "The selected table is empty!", "Empty Table", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = random.nextInt(selectedTable.getRowCount());
        int column = random.nextInt(selectedTable.getColumnCount());

        Object selectedCellValue = selectedTable.getValueAt(row, column);

        if (selectedCellValue != null && !selectedCellValue.toString().isEmpty() && !selectedCellValue.toString().equals("X")) {
            lblnum.setText(selectedCellValue.toString());

            lbltable1bingo.setText("");
            lbltable2bingo.setText("");

            boolean numberInTable1 = false;
            boolean numberInTable2 = false;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                for (int j = 0; j < jTable1.getColumnCount(); j++) {
                    Object cellValue = jTable1.getValueAt(i, j);
                    if (cellValue != null && !cellValue.toString().equals("X") && cellValue.toString().equals(selectedCellValue.toString())) {
                        numberInTable1 = true;
                        break;
                    }
                }
                if (numberInTable1) {
                    break;
                }
            }

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                for (int j = 0; j < jTable2.getColumnCount(); j++) {
                    Object cellValue = jTable2.getValueAt(i, j);
                    if (cellValue != null && !cellValue.toString().equals("X") && cellValue.toString().equals(selectedCellValue.toString())) {
                        numberInTable2 = true;
                        break;
                    }
                }
                if (numberInTable2) {
                    break;
                }
            }

            if (numberInTable1) {
                lbltable1.setText("It's here");
            } else {
                lbltable1.setText("");
            }

            if (numberInTable2) {
                lbltable2.setText("It's here");
            } else {
                lbltable2.setText("");
            }
        } else {
            takeANumber();
        }
    }

    private void checkNumberInTable(JTable table) {
        Object num = lblnum.getText();
        if (!num.equals("")) {
            String number = num.toString();
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Object cellValue = table.getValueAt(i, j);
                    if (cellValue != null && cellValue.toString().equals(number)) {
                        table.setValueAt("X", i, j);
                        return;
                    }
                }
            }
        }
    }

    private boolean firstBingoFlagTable1 = false;
    private boolean secondBingoFlagTable1 = false;
    private boolean firstBingoFlagTable2 = false;
    private boolean secondBingoFlagTable2 = false;

    private void checkBingoInTable(JTable table, JLabel bingoLabel, boolean isFirstTable) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        int totalXCount = 0;
        int fullRowCount = 0;

        fullRowCount = 0;

        for (int i = 0; i < rowCount; i++) {
            int countX = 0;
            for (int j = 0; j < columnCount; j++) {
                Object cellValue = table.getValueAt(i, j);
                if (cellValue != null && cellValue.toString().equals("X")) {
                    countX++;
                    totalXCount++;
                }
            }
            if (countX >= 5) {
                fullRowCount++;
            }
        }

        if (fullRowCount >= 1 && isFirstTable && !firstBingoFlagTable1) {
            bingoLabel.setText("Birinci Çinko");
            firstBingoFlagTable1 = true;
        } else if (fullRowCount >= 2 && isFirstTable && !secondBingoFlagTable1) {
            bingoLabel.setText("Ikinci Çinko");
            secondBingoFlagTable1 = true;
        } else if (fullRowCount >= 1 && !isFirstTable && !firstBingoFlagTable2) {
            bingoLabel.setText("Birinci Çinko");
            firstBingoFlagTable2 = true;
        } else if (fullRowCount >= 2 && !isFirstTable && !secondBingoFlagTable2) {
            bingoLabel.setText("Ikinci Çinko");
            secondBingoFlagTable2 = true;
        } else {
            bingoLabel.setText("");
        }

        if (totalXCount == 15) {
            bingoLabel.setText("Tombala!");
            if (isFirstTable) {
                JOptionPane.showMessageDialog(rootPane, "Player 1 won the game!", "TOMBALA!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Player 2 won the game!", "TOMBALA!", JOptionPane.INFORMATION_MESSAGE);
            }
            resetGame();
        }
    }

    private void resetGame() {
        firstBingoFlagTable1 = false;
        secondBingoFlagTable1 = false;
        firstBingoFlagTable2 = false;
        secondBingoFlagTable2 = false;

        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();

        model1.setRowCount(0);
        model2.setRowCount(0);

        lblnum.setText("");

        lbltable1bingo.setText("");
        lbltable2bingo.setText("");

        lbltable1.setText("");
        lbltable2.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTable1 = new javax.swing.JTable();
        jTable2 = new javax.swing.JTable();
        lblnum = new javax.swing.JLabel();
        btngenerate = new javax.swing.JButton();
        lbltable1 = new javax.swing.JLabel();
        lbltable2 = new javax.swing.JLabel();
        btncheck = new javax.swing.JButton();
        lbltable1bingo = new javax.swing.JLabel();
        btnstart = new javax.swing.JButton();
        lbltable2bingo = new javax.swing.JLabel();
        btnreset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(875, 426));

        jTable1.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setEnabled(false);
        jTable1.setShowGrid(true);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, null));
        jTable2.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        jTable2.setForeground(new java.awt.Color(0, 0, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable2.setToolTipText("");
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable2.setEnabled(false);
        jTable2.setGridColor(new java.awt.Color(102, 102, 102));
        jTable2.setShowGrid(true);

        lblnum.setBackground(new java.awt.Color(255, 255, 255));
        lblnum.setFont(new java.awt.Font("Traditional Arabic", 1, 36)); // NOI18N
        lblnum.setForeground(new java.awt.Color(255, 255, 255));
        lblnum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btngenerate.setFont(new java.awt.Font("Traditional Arabic", 1, 14)); // NOI18N
        btngenerate.setText("Take a number");
        btngenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerateActionPerformed(evt);
            }
        });

        lbltable1.setBackground(new java.awt.Color(255, 255, 255));
        lbltable1.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        lbltable1.setForeground(new java.awt.Color(255, 255, 255));

        lbltable2.setBackground(new java.awt.Color(255, 255, 255));
        lbltable2.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        lbltable2.setForeground(new java.awt.Color(255, 255, 255));

        btncheck.setFont(new java.awt.Font("Traditional Arabic", 1, 14)); // NOI18N
        btncheck.setText("Check");
        btncheck.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btncheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncheckActionPerformed(evt);
            }
        });

        lbltable1bingo.setBackground(new java.awt.Color(255, 255, 255));
        lbltable1bingo.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        lbltable1bingo.setForeground(new java.awt.Color(255, 255, 255));
        lbltable1bingo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnstart.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        btnstart.setText("Shuffle ");
        btnstart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnstartActionPerformed(evt);
            }
        });

        lbltable2bingo.setBackground(new java.awt.Color(255, 255, 255));
        lbltable2bingo.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        lbltable2bingo.setForeground(new java.awt.Color(255, 255, 255));
        lbltable2bingo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnreset.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Traditional Arabic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOMBALA");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Traditional Arabic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Player 1");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Traditional Arabic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Player 2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(284, 284, 284))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btncheck)
                        .addGap(399, 399, 399))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(btnstart, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbltable1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbltable1bingo, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbltable2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbltable2bingo, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133)))))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(371, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblnum, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(387, 387, 387))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btngenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(366, 366, 366))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblnum, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btngenerate)
                .addGap(18, 18, 18)
                .addComponent(btncheck)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTable2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltable2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltable1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltable2bingo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltable1bingo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnstart, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnstartActionPerformed(java.awt.event.ActionEvent evt) {                                         
        IbrahimGokdemirMLinkedList<Integer> list1 = new IbrahimGokdemirMLinkedList<>();
        IbrahimGokdemirMLinkedList<Integer> list2 = new IbrahimGokdemirMLinkedList<>();

        list1.matrix_to_multiList(createMatrix());
        list2.matrix_to_multiList(createMatrix());

//        list1.matrix_to_multiList(createManualMatrix1());
//        list2.matrix_to_multiList(createManualMatrix2());

        loadMultiListToTable(list1, jTable1);
        loadMultiListToTable(list2, jTable2);
//         TODO add your handling code here:
    }                                        

    private void btngenerateActionPerformed(java.awt.event.ActionEvent evt) {                                            
        takeANumber();
        // TODO add your handling code here:
    }                                           
    private boolean isTable1Checked = false;
    private void btncheckActionPerformed(java.awt.event.ActionEvent evt) {                                         
        checkNumberInTable(jTable1);
        checkBingoInTable(jTable1, lbltable1bingo, true);

        checkNumberInTable(jTable2);
        checkBingoInTable(jTable2, lbltable2bingo, false);
    }                                        

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {                                         
        resetGame();
        // TODO add your handling code here:
    }                                        

    /*
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IbrahimGokdemirCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IbrahimGokdemirCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IbrahimGokdemirCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IbrahimGokdemirCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IbrahimGokdemirCard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btncheck;
    private javax.swing.JButton btngenerate;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnstart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblnum;
    private javax.swing.JLabel lbltable1;
    private javax.swing.JLabel lbltable1bingo;
    private javax.swing.JLabel lbltable2;
    private javax.swing.JLabel lbltable2bingo;
    // End of variables declaration                   
}
