package ui;

import model.Expense;
import service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DashboardPanel extends JPanel {
    private ExpenseService expenseService;
    private JLabel lblTotal;
    private JLabel lblTransactions;
    private JPanel categoryPanel;
    
    private Color cardBg = new Color(45, 45, 45);

    public DashboardPanel(ExpenseService expenseService) {
        this.expenseService = expenseService;
        setLayout(new BorderLayout(20, 20));
        setOpaque(false);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        // Top Summary Cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        summaryPanel.setOpaque(false);
        
        RoundedPanel card1 = new RoundedPanel(cardBg, 15);
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        card1.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel title1 = new JLabel("Total Expenses");
        title1.setForeground(new Color(150, 150, 150));
        title1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal = new JLabel("₹0.0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotal.setForeground(Color.WHITE);
        card1.add(title1);
        card1.add(Box.createVerticalStrut(10));
        card1.add(lblTotal);
        
        RoundedPanel card2 = new RoundedPanel(cardBg, 15);
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
        card2.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel title2 = new JLabel("Transactions");
        title2.setForeground(new Color(150, 150, 150));
        title2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTransactions = new JLabel("0");
        lblTransactions.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTransactions.setForeground(Color.WHITE);
        card2.add(title2);
        card2.add(Box.createVerticalStrut(10));
        card2.add(lblTransactions);
        
        summaryPanel.add(card1);
        summaryPanel.add(card2);
        
        add(summaryPanel, BorderLayout.NORTH);

        // Category Breakdown Card
        RoundedPanel categoryCard = new RoundedPanel(cardBg, 15);
        categoryCard.setLayout(new BorderLayout());
        categoryCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel catTitle = new JLabel("Category Breakdown");
        catTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        catTitle.setForeground(Color.WHITE);
        categoryCard.add(catTitle, BorderLayout.NORTH);
        
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(categoryPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        categoryCard.add(scrollPane, BorderLayout.CENTER);
        
        add(categoryCard, BorderLayout.CENTER);
    }

    public void refresh() {
        List<Expense> expenses = expenseService.getAllExpenses();
        double total = expenseService.calculateTotalExpenses(expenses);
        
        lblTotal.setText(String.format("₹%.2f", total));
        lblTransactions.setText(String.valueOf(expenses.size()));
        
        categoryPanel.removeAll();
        Map<String, Double> summary = expenseService.getCategorySummary(expenses);
        
        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)),
                new EmptyBorder(10, 5, 10, 5)
            ));
            
            JLabel catLbl = new JLabel(entry.getKey());
            catLbl.setForeground(new Color(220, 220, 220));
            catLbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            
            JLabel amtLbl = new JLabel(String.format("₹%.2f", entry.getValue()));
            amtLbl.setForeground(Color.WHITE);
            amtLbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
            
            row.add(catLbl, BorderLayout.WEST);
            row.add(amtLbl, BorderLayout.EAST);
            categoryPanel.add(row);
        }
        
        categoryPanel.revalidate();
        categoryPanel.repaint();
    }
    
    // Inner class for rounded panels
    class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius;

        public RoundedPanel(Color bgColor, int radius) {
            super();
            this.backgroundColor = bgColor;
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }
}
