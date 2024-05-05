/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.ibrahimgokdemirproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ieren
 */
public class Card extends javax.swing.JFrame {

    private final int ROWS = 3;
    private final int COLS = 9;

    public Card() {
         
        initComponents();
    }

    private int[][] organizeNumbers(int[][] card) {
        int[][] organizedCard = new int[ROWS][COLS];

        for (int col = 0; col < COLS; col++) {
            int start = col * 10 + 1;
            int end = start + 9;

            List<Integer> columnNumbers = new ArrayList<>();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (card[i][j] >= start && card[i][j] <= end) {
                        columnNumbers.add(card[i][j]);
                    }
                }
            }

            Collections.sort(columnNumbers);

            for (int i = 0; i < Math.min(ROWS, columnNumbers.size()); i++) {
                organizedCard[i][col] = columnNumbers.get(i);
            }
        }

        return organizedCard;
    }

   
    private MLinkedList<Integer> generateCard(List<Integer> usedNumbers) {
        int[][] card = new int[ROWS][COLS];
        Random random = new Random();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                card[i][j] = -1;
            }
        }

        int start = 1;
        int end = 10;
        for (int col = 0; col < COLS; col++) {
            List<Integer> columnNumbers = new ArrayList<>();
            while (columnNumbers.size() < ROWS) {
                int num = random.nextInt(end - start + 1) + start;
                if (!usedNumbers.contains(num)) {
                    columnNumbers.add(num);
                    usedNumbers.add(num);
                }
            }
            Collections.sort(columnNumbers);

            for (int row = 0; row < ROWS; row++) {
                card[row][col] = columnNumbers.get(row);
            }

            start += 10;
            end += 10;
        }

        MLinkedList<Integer> multiLinkedList = convertMatrixToMultiList(card);

        return multiLinkedList;
    }

    private MLinkedList<Integer> convertMatrixToMultiList(int[][] matrix) {
        MLinkedList<Integer> multiLinkedList = new MLinkedList<>();
        multiLinkedList.matrix_to_multiList(matrix);
        return multiLinkedList;
    }

    private void generateCards() {
        // Temizleme işlemi
        clearTable(jTable1);
        clearTable(jTable2);

        List<Integer> usedNumbers = new ArrayList<>();
        MLinkedList<Integer> multiList1 = generateCard(usedNumbers);
        MLinkedList<Integer> multiList2 = generateCard(usedNumbers);

        displayMultiList(multiList1, jTable1);
        displayMultiList(multiList2, jTable2);
    }

    private void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    private void displayMultiList(MLinkedList<Integer> multiList, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(3); // Set the number of rows to 3

        // Create a list to hold all nodes
        List<Node<Integer>> nodes = new ArrayList<>();

        // Traverse the multiList and add nodes to the list
        Node<Integer> current = multiList.getHead();
        while (current != null) {
            Node<Integer> temp = current;
            while (temp != null) {
                nodes.add(temp);
                temp = temp.next;
            }
            current = current.down;
        }

        Random random = new Random();

        // Shuffle the nodes randomly
        // Display the shuffled nodes on the table
        int rowIndex = 0;
        int numbersDisplayed = 0;
        while (!nodes.isEmpty() && numbersDisplayed < 15) {
            int row = rowIndex % 3; // Determine the row index
            List<Integer>[] columns = new List[9];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = new ArrayList<>();
            }

            // Iterate through the nodes and distribute them to the appropriate columns
            for (int i = 0; i < 5; i++) {
                if (nodes.isEmpty()) {
                    break; // Break if no more nodes
                }
                Node<Integer> node = nodes.remove(0);
                int column = (node.data - 1) / 10;
                columns[column].add(node.data);
                numbersDisplayed++;
            }

            // Sort the numbers in each column
            for (List<Integer> column : columns) {
                column.sort(Comparator.naturalOrder());
            }

            // Display the sorted numbers in the table
            for (int i = 0; i < columns.length; i++) {
                int colIndex = i;
                int colOffset = 0;
                for (Integer number : columns[i]) {
                    // Find the next empty row
                    while (model.getValueAt(row, colIndex) != null) {
                        row = (row + 1) % 3;
                        colOffset++;
                        colIndex = (i + colOffset) % 9;
                    }
                    // Set the value in the table
                    model.setValueAt(number == -1 ? "" : number, row, colIndex);
                }
            }

            rowIndex++; // Move to the next row
        }
    }

    private void populateTable(JTable table, int[][] card) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (int i = 0; i < ROWS; i++) {
            Object[] rowData = new Object[COLS];
            for (int j = 0; j < COLS; j++) {
                rowData[j] = card[i][j];
            }
            model.addRow(rowData);
        }
    }

    private void checkNumberInCards(int number) {
        boolean numberFoundInCard1 = false;
        boolean numberFoundInCard2 = false;

        DefaultTableModel model1 = (DefaultTableModel) jTable2.getModel();
        for (int row = 0; row < model1.getRowCount(); row++) {
            for (int col = 0; col < model1.getColumnCount(); col++) {
                if (model1.getValueAt(row, col) != null && model1.getValueAt(row, col).equals(number)) {
                    lblcard1.setText("It's here!");
                    lblcard2.setText(""); // Clear lblcard2
                    numberFoundInCard1 = true;
                    model1.setValueAt("-", row, col);
                    break;
                }
            }
            if (numberFoundInCard1) {
                break;
            }
        }

        DefaultTableModel model2 = (DefaultTableModel) jTable1.getModel();
        for (int row = 0; row < model2.getRowCount(); row++) {
            for (int col = 0; col < model2.getColumnCount(); col++) {
                if (model2.getValueAt(row, col) != null && model2.getValueAt(row, col).equals(number)) {
                    lblcard2.setText("It's here!");
                    lblcard1.setText(""); // Clear lblcard1
                    numberFoundInCard2 = true;
                    model2.setValueAt("-", row, col);
                    break;
                }
            }
            if (numberFoundInCard2) {
                break;
            }
        }
    }

    private int getNumberFromTable(JTable table) {
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (selectedRow != -1 && selectedColumn != -1) {
            Object value = table.getValueAt(selectedRow, selectedColumn);
            if (value != null) {
                try {
                    return Integer.parseInt(value.toString());
                } catch (NumberFormatException e) {
                    // Handle parsing error, if necessary
                }
            }
        }

        return -1; // Return -1 if no number found or cell is empty
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTable2 = new javax.swing.JTable();
        jTable1 = new javax.swing.JTable();
        btnstart = new javax.swing.JButton();
        lblnum = new javax.swing.JLabel();
        tngenerate = new javax.swing.JButton();
        btncheck = new javax.swing.JButton();
        lblcard1 = new javax.swing.JLabel();
        lblcard2 = new javax.swing.JLabel();
        LBLtİTLE = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TOMBALA");
        setPreferredSize(new java.awt.Dimension(1200, 1000));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jTable2.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
                , "Title 7", "Title 8", "Title 9"}
        ));

        jTable1.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
                , "Title 7", "Title 8", "Title 9"}
        ));

        btnstart.setText("Start ");
        btnstart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnstartActionPerformed(evt);
            }
        });

        tngenerate.setText("Generate");
        tngenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tngenerateActionPerformed(evt);
            }
        });

        btncheck.setText("Check");
        btncheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncheckActionPerformed(evt);
            }
        });

        LBLtİTLE.setBackground(new java.awt.Color(153, 153, 153));
        LBLtİTLE.setFont(new java.awt.Font("Century Schoolbook", 1, 36)); // NOI18N
        LBLtİTLE.setForeground(new java.awt.Color(153, 153, 153));
        LBLtİTLE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLtİTLE.setText("TOMBALA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnstart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(492, 492, 492))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addComponent(jTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(642, 642, 642)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btncheck, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tngenerate))
                    .addGap(480, 480, 480))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(392, 392, 392)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(lblnum, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(616, 616, 616))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(LBLtİTLE, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(410, 410, 410))))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblcard2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 383, Short.MAX_VALUE)
                    .addComponent(lblcard1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(37, 37, 37)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addComponent(btnstart)
                .addContainerGap(383, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(LBLtİTLE, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(lblnum, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(28, 28, 28)
                    .addComponent(tngenerate)
                    .addGap(18, 18, 18)
                    .addComponent(btncheck)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblcard1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblcard2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void btnstartActionPerformed(java.awt.event.ActionEvent evt) {                                         
        generateCards();
        // TODO add your handling code here:
    }                                        

    private void tngenerateActionPerformed(java.awt.event.ActionEvent evt) {                                           
        int tableIndex = new Random().nextInt(2); // Randomly select the table index (0 for jTable1, 1 for jTable2)
        JTable table = tableIndex == 0 ? jTable1 : jTable2;
        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();

        if (rowCount > 0 && colCount > 0) {
            List<Integer> nonEmptyCellIndices = new ArrayList<>();

            // Find indices of non-empty cells containing numbers
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    Object value = table.getValueAt(i, j);
                    if (value instanceof Number) {
                        nonEmptyCellIndices.add(i * colCount + j);
                    }
                }
            }

            // Check if there are non-empty cells with numbers
            if (!nonEmptyCellIndices.isEmpty()) {
                // Randomly select a non-empty cell
                int randomIndex = nonEmptyCellIndices.get(new Random().nextInt(nonEmptyCellIndices.size()));
                int randomRow = randomIndex / colCount;
                int randomCol = randomIndex % colCount;

                Object value = table.getValueAt(randomRow, randomCol);
                int numberToCheck = ((Number) value).intValue();
                lblnum.setText(String.valueOf(numberToCheck)); // Update lblnum with the chosen number

                // Clear both labels initially
                lblcard1.setText("");
                lblcard2.setText("");

                // Update the corresponding label based on the table where the number is found
                if (table == jTable1) {
                    lblcard1.setText("It's here!"); // Update lblcard1 if the number is in jTable1
                } else {
                    lblcard2.setText("It's here!"); // Update lblcard2 if the number is in jTable2
                }

                checkNumberInCards(numberToCheck);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There are no numbers in the selected table. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "The table is empty. Please generate cards first.");
        }
    }                                          

    private void btncheckActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int numberToCheck = Integer.parseInt(lblnum.getText());

        checkNumberInCards(numberToCheck);

// TODO add your handling code here:
    }                                        

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Card.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Card.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Card.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Card.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Card().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel LBLtİTLE;
    private javax.swing.JButton btncheck;
    private javax.swing.JButton btnstart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblcard1;
    private javax.swing.JLabel lblcard2;
    private javax.swing.JLabel lblnum;
    private javax.swing.JButton tngenerate;
    // End of variables declaration                   
}
