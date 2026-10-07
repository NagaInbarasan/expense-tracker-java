package service;

import dao.ExpenseDAO;
import model.Expense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
    private ExpenseDAO expenseDAO;

    public ExpenseService() {
        this.expenseDAO = new ExpenseDAO();
    }

    public boolean addExpense(Expense expense) throws IllegalArgumentException {
        validateExpense(expense);
        expenseDAO.addExpense(expense);
        return true;
    }

    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }
    
    public Expense getExpenseById(int id) {
        return expenseDAO.getExpenseById(id);
    }

    public boolean updateExpense(Expense expense) throws IllegalArgumentException {
        validateExpense(expense);
        expenseDAO.updateExpense(expense);
        return true;
    }

    public void deleteExpense(int id) {
        expenseDAO.deleteExpense(id);
    }

    public List<Expense> searchExpenses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllExpenses();
        }
        return expenseDAO.searchExpenses(keyword.trim());
    }

    public double calculateTotalExpenses(List<Expense> expenses) {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public Map<String, Double> getCategorySummary(List<Expense> expenses) {
        Map<String, Double> summary = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();
            summary.put(category, summary.getOrDefault(category, 0.0) + amount);
        }
        return summary;
    }

    private void validateExpense(Expense expense) throws IllegalArgumentException {
        if (expense.getTitle() == null || expense.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        if (expense.getCategory() == null || expense.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category must be selected.");
        }
        if (expense.getDate() == null || !expense.getDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
    }
}
