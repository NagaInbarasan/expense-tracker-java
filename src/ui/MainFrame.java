package ui;

import service.ExpenseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private ExpenseService expenseService;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JLabel lblHeaderTitle;

    private DashboardPanel dashboardPanel;
    private AddExpensePanel addExpensePanel;
    private ExpenseTablePanel expenseTablePanel;
    
    private Color sidebarBg = new Color(20, 20, 20);
    private Color sidebarHover = new Color(40, 40, 40);

    public MainFrame() {
        setTitle("Expense Tracker");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        expenseService = new ExpenseService();

        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarBg);
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        
        JLabel appTitle = new JLabel("EXPENSE TRACKER");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appTitle.setForeground(Color.WHITE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        appTitle.setBorder(new EmptyBorder(20, 10, 30, 10));
        sidebar.add(appTitle);

        JButton btnDashboard = createSidebarButton("Dashboard", "Dashboard");
        JButton btnAddExpense = createSidebarButton("Add Expense", "AddExpense");
        JButton btnExpenses = createSidebarButton("Expenses", "Expenses");

        sidebar.add(btnDashboard);
        sidebar.add(btnAddExpense);
        sidebar.add(btnExpenses);
        sidebar.add(Box.createVerticalGlue());
        
        add(sidebar, BorderLayout.WEST);

        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        lblHeaderTitle = new JLabel("Dashboard");
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderTitle.setForeground(Color.WHITE);
        headerPanel.add(lblHeaderTitle);
        
        mainContent.add(headerPanel, BorderLayout.NORTH);

        // Cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(30, 30, 30));

        dashboardPanel = new DashboardPanel(expenseService);
        addExpensePanel = new AddExpensePanel(expenseService, this);
        expenseTablePanel = new ExpenseTablePanel(expenseService, this);

        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(addExpensePanel, "AddExpense");
        cardPanel.add(expenseTablePanel, "Expenses");

        mainContent.add(cardPanel, BorderLayout.CENTER);
        
        add(mainContent, BorderLayout.CENTER);
        
        refreshData();
    }
    
    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(new Color(200, 200, 200));
        btn.setBackground(sidebarBg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(sidebarHover);
                btn.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(sidebarBg);
                btn.setForeground(new Color(200, 200, 200));
            }
        });
        
        btn.addActionListener(e -> {
            lblHeaderTitle.setText(text);
            if (cardName.equals("Dashboard")) showDashboard();
            else if (cardName.equals("AddExpense")) showAddExpense();
            else if (cardName.equals("Expenses")) showExpenses();
        });
        
        return btn;
    }

    public void showDashboard() {
        lblHeaderTitle.setText("Dashboard");
        dashboardPanel.refresh();
        cardLayout.show(cardPanel, "Dashboard");
    }

    public void showAddExpense() {
        lblHeaderTitle.setText("Add Expense");
        addExpensePanel.clearFields();
        cardLayout.show(cardPanel, "AddExpense");
    }
    
    public void showAddExpenseForEdit(model.Expense expense) {
        lblHeaderTitle.setText("Edit Expense");
        addExpensePanel.populateForEdit(expense);
        cardLayout.show(cardPanel, "AddExpense");
    }

    public void showExpenses() {
        lblHeaderTitle.setText("Expenses");
        expenseTablePanel.refresh();
        cardLayout.show(cardPanel, "Expenses");
    }

    public void refreshData() {
        dashboardPanel.refresh();
        expenseTablePanel.refresh();
    }
}
