package ui;

import model.Expense;
import service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpensePanel extends JPanel {
    private ExpenseService expenseService;
    private MainFrame mainFrame;
    
    private JTextField txtTitle;
    private JTextField txtAmount;
    private JComboBox<String> cbCategory;
    private JTextField txtDate;
    private JTextArea taDescription;
    
    private Integer editExpenseId = null;
    private JLabel formTitle;

    public AddExpensePanel(ExpenseService expenseService, MainFrame mainFrame) {
        this.expenseService = expenseService;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        RoundedPanel card = new RoundedPanel(new Color(45, 45, 45), 15);
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        formTitle = new JLabel("Add New Expense");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(Color.WHITE);
        card.add(formTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 20, 20));
        formPanel.setOpaque(false);
        
        formPanel.add(createLabel("Title:"));
        txtTitle = new JTextField();
        formPanel.add(txtTitle);
        
        formPanel.add(createLabel("Amount (₹):"));
        txtAmount = new JTextField();
        formPanel.add(txtAmount);
        
        formPanel.add(createLabel("Category:"));
        String[] categories = {"Food", "Transport", "Education", "Shopping", "Bills", "Entertainment", "Health", "Travel", "Other"};
        cbCategory = new JComboBox<>(categories);
        formPanel.add(cbCategory);
        
        formPanel.add(createLabel("Date (YYYY-MM-DD):"));
        txtDate = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        formPanel.add(txtDate);
        
        formPanel.add(createLabel("Description:"));
        taDescription = new JTextArea(3, 20);
        taDescription.setLineWrap(true);
        taDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(taDescription);
        formPanel.add(scrollDesc);
        
        card.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);
        JButton btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(60, 60, 60));
        
        JButton btnSave = new JButton("Save Expense");
        
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        add(card, BorderLayout.CENTER);
        
        // Push everything up so it doesn't stretch vertically
        add(Box.createVerticalStrut(200), BorderLayout.SOUTH);
        
        btnSave.addActionListener(e -> saveExpense());
        btnClear.addActionListener(e -> clearFields());
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(200, 200, 200));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void saveExpense() {
        try {
            String title = txtTitle.getText();
            double amount = Double.parseDouble(txtAmount.getText().trim());
            String category = (String) cbCategory.getSelectedItem();
            String date = txtDate.getText().trim();
            String description = taDescription.getText();
            
            Expense expense = new Expense(title, amount, category, date, description);
            
            if (editExpenseId != null) {
                expense.setId(editExpenseId);
                expenseService.updateExpense(expense);
                JOptionPane.showMessageDialog(this, "Expense updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                expenseService.addExpense(expense);
                JOptionPane.showMessageDialog(this, "Expense added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            clearFields();
            mainFrame.showExpenses();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        txtTitle.setText("");
        txtAmount.setText("");
        cbCategory.setSelectedIndex(0);
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        taDescription.setText("");
        editExpenseId = null;
        formTitle.setText("Add New Expense");
    }
    
    public void populateForEdit(Expense expense) {
        editExpenseId = expense.getId();
        txtTitle.setText(expense.getTitle());
        txtAmount.setText(String.valueOf(expense.getAmount()));
        cbCategory.setSelectedItem(expense.getCategory());
        txtDate.setText(expense.getDate());
        taDescription.setText(expense.getDescription());
        formTitle.setText("Edit Expense");
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
