package test;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class TableHeaderWithSetModel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("setModel Table Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create panel with BoxLayout
            JPanel jPanel1 = new JPanel();
            jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));

            JTable table = new JTable(); // create empty table

            // Set model AFTER table creation
            MyTableModel myTableModel = new MyTableModel();
            table.setModel(myTableModel);

            // Wrap table in scroll pane (this will display the header)
            JScrollPane scrollPane = new JScrollPane(jPanel1);

            // ❌ DO NOT add table directly — it's already inside scrollPane
//            jPanel1.add(scrollPane);

            frame.add(scrollPane);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null); // center on screen
            frame.setVisible(true);
        });
    }

    static class MyTableModel extends AbstractTableModel {
        private final String[] columnNames = { "ID", "Name", "Email" };
        private final Object[][] data = {
                { 1, "Alice", "alice@example.com" },
                { 2, "Bob", "bob@example.com" },
                { 3, "Charlie", "charlie@example.com" }
        };

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        // THIS IS REQUIRED FOR HEADERS TO SHOW
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}
