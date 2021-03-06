package ay2021s1_cs2103_w16_3.finesse.model;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a finance tracker
 */
public interface ReadOnlyFinanceTracker {

    /**
     * Returns an unmodifiable view of the transactions list.
     */
    ObservableList<Transaction> getTransactionList();

    /**
     * Returns an unmodifiable view of the transactions list.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Returns an unmodifiable view of the transactions list.
     */
    ObservableList<Income> getIncomeList();

    /**
     * Returns an unmodifiable view of the frequent expense list.
     */
    ObservableList<FrequentExpense> getFrequentExpenseList();

    /**
     * Returns an unmodifiable view of the frequent income list.
     */
    ObservableList<FrequentIncome> getFrequentIncomeList();

    /**
     * Returns the monthly budget.
     */
    MonthlyBudget getMonthlyBudget();

}
