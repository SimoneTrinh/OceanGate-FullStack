package CryptoUserInfo.panels;

import CryptoUserInfo.components.IconResources;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionHistoryPanel extends JPanel {

    public TransactionHistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== Wrapper panel chứa toàn bộ nội dung =====
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245)); // màu nền nhẹ

        // ===== Tiêu đề =====
        JLabel title = new JLabel("History");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== Nhóm giao dịch theo ngày =====
        contentPanel.add(createDateGroup("Today", List.of(
                new Transaction("Bitcoin", "04:35 09.03", "Pending", 854),
                new Transaction("Ethereum", "04:21 09.03", "Completed", 100)
        )));

        contentPanel.add(createDateGroup("Yesterday", List.of(
                new Transaction("Ethereum", "12:12 08.03", "Completed", -100),
                new Transaction("Bitcoin", "11:59 08.03", "Completed", -400.38)
        )));

        contentPanel.add(createDateGroup("05.03.2024", List.of(
                new Transaction("XRP", "17:22 05.03", "Completed", 400.01),
                new Transaction("Bitcoin", "13:15 05.03", "Completed", 400.38)
        )));

        contentPanel.add(createDateGroup("02.03.2024", List.of(
                new Transaction("XRP", "04:20 02.03", "Completed", -225.52)
        )));

        // ===== Cho phép cuộn nếu dài =====
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createDateGroup(String dateLabel, List<Transaction> transactions) {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setOpaque(false);
        groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ✅ Cố định chiều cao cho groupPanel (dựa theo số dòng, ví dụ 1 dòng = 60px)
        int rowHeight = 60;
        int padding = 30; // space for date label, margin, etc.
        int height = transactions.size() * rowHeight + padding;

        groupPanel.setPreferredSize(new Dimension(720, height));
        groupPanel.setMaximumSize(new Dimension(720, height));
        groupPanel.setMinimumSize(new Dimension(720, height));

        // === Date label ===
        JPanel dateWrapper = new JPanel(new BorderLayout());
        dateWrapper.setOpaque(false);
        dateWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        dateWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateTitle = new JLabel(dateLabel);
        dateTitle.setFont(new Font("Arial", Font.BOLD, 14));
        dateTitle.setForeground(Color.GRAY);
        dateWrapper.add(dateTitle, BorderLayout.WEST);

        groupPanel.add(dateWrapper);

        for (Transaction tx : transactions) {
            JPanel txRow = createTransactionRow(tx);
            txRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            groupPanel.add(txRow);
            groupPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return groupPanel;
    }

    private JPanel createTransactionRow(Transaction tx) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS)); // Tránh BorderLayout để kiểm soát chặt alignment
        row.setBackground(Color.WHITE);
        row.setPreferredSize(new Dimension(700, 50));
        row.setMaximumSize(new Dimension(700, 50));
        row.setMinimumSize(new Dimension(700, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // === Left ===
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.setOpaque(false);
        left.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel icon;
        try {
            icon = new JLabel(IconResources.valueOf(tx.coin.toUpperCase().replace(" ", "_")).getIcon());
        } catch (Exception e) {
            icon = new JLabel("🪙");
        }
        icon.setPreferredSize(new Dimension(24, 24));

        JPanel textGroup = new JPanel();
        textGroup.setLayout(new BoxLayout(textGroup, BoxLayout.Y_AXIS));
        textGroup.setOpaque(false);

        JLabel name = new JLabel(tx.coin);
        name.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel date = new JLabel(tx.date);
        date.setFont(new Font("Arial", Font.PLAIN, 12));
        date.setForeground(Color.GRAY);

        textGroup.add(name);
        textGroup.add(date);

        left.add(icon);
        left.add(Box.createRigidArea(new Dimension(10, 0)));
        left.add(textGroup);

        // === Center ===
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.setOpaque(false);
        center.setAlignmentY(Component.CENTER_ALIGNMENT);
        center.setPreferredSize(new Dimension(120, 50)); // Cố định chiều ngang
        center.setMaximumSize(new Dimension(120, 50));

        JLabel status = new JLabel(tx.status);
        status.setFont(new Font("Arial", Font.BOLD, 12));
        status.setForeground(switch (tx.status) {
            case "Pending" -> new Color(255, 165, 0);
            case "Completed" -> new Color(0, 153, 51);
            default -> Color.RED;
        });
        center.add(status);

        // === Right ===
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(120, 50)); // Cố định chiều ngang
        right.setMaximumSize(new Dimension(120, 50));
        right.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel amount = new JLabel((tx.amount < 0 ? "-" : "+") + Math.abs(tx.amount) + " $");
        amount.setFont(new Font("Arial", Font.BOLD, 14));
        amount.setForeground(tx.amount < 0 ? Color.RED : new Color(0, 128, 0));
        right.add(amount);

        // Add các phần vào row
        row.add(left);
        row.add(Box.createHorizontalGlue()); // Đẩy các thành phần về bên trái
        row.add(center);
        row.add(Box.createRigidArea(new Dimension(10, 0)));
        row.add(right);

        return row;
    }

    // ==== Data model cho 1 giao dịch ====
    static class Transaction {
        String coin;
        String date;
        String status;
        double amount;

        public Transaction(String coin, String date, String status, double amount) {
            this.coin = coin;
            this.date = date;
            this.status = status;
            this.amount = amount;
        }
    }
}
