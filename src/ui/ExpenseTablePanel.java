package ui;

import model.Expense;
import service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ExpenseTablePanel extends JPanel {
    private ExpenseService expenseService;
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private List<Expense> currentExpenses;

    public ExpenseTablePanel(ExpenseService expenseService, MainFrame mainFrame) {
        this.expenseService = expenseService;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(20, 30, 20, 30));
        
        RoundedPanel card = new RoundedPanel(new Color(45, 45, 45), 15);
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(Color.WHITE);
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchPanel.add(lblSearch);
        
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        
        JButton btnSearch = new JButton("Search");
        searchPanel.add(btnSearch);
        
        JButton btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(60, 60, 60));
        searchPanel.add(btnClear);
        
        card.add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Date", "Title", "Category", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 35));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        card.add(scrollPane, BorderLayout.CENTER);
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setOpaque(false);
        
        JButton btnEdit = new JButton("Edit Selected");
        btnEdit.setBackground(new Color(60, 60, 60));
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new Color(220, 53, 69)); // Danger red
        
        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);
        
        card.add(actionPanel, BorderLayout.SOUTH);
        
        add(card, BorderLayout.CENTER);
        
        // Event Listeners
        btnSearch.addActionListener(e -> performSearch());
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            refresh();
        });
        
        btnEdit.addActionListener(e -> editSelected());
        btnDelete.addActionListener(e -> deleteSelected());
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText();
        currentExpenses = expenseService.searchExpenses(keyword);
        updateTableModel();
    }
    
    public void refresh() {
        txtSearch.setText("");
        currentExpenses = expenseService.getAllExpenses();
        updateTableModel();
    }
    
    private void updateTableModel() {
        tableModel.setRowCount(0);
        for (Expense expense : currentExpenses) {
            Object[] row = {
                expense.getId(),
                expense.getDate(),
                expense.getTitle(),
                expense.getCategory(),
                String.format("₹%.2f", expense.getAmount()),
                expense.getDescription()
            };
            tableModel.addRow(row);
        }
    }
    
    private void editSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int expenseId = (int) tableModel.getValueAt(selectedRow, 0);
            Expense expense = expenseService.getExpenseById(expenseId);
            if (expense != null) {
                mainFrame.showAddExpenseForEdit(expense);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to edit.");
        }
    }
    
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int expenseId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this expense?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                expenseService.deleteExpense(expenseId);
                refresh();
                mainFrame.refreshData(); // Updates dashboard too
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
        }
    }
    
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
