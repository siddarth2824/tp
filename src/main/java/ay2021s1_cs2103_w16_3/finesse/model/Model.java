package ay2021s1_cs2103_w16_3.finesse.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true. */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = transaction -> true;

    /** {@code Predicate} that evaluates to true if the transaction is an {@code Expense} */
    Predicate<Transaction> PREDICATE_SHOW_ALL_EXPENSES = transaction -> transaction instanceof Expense;

    /** {@code Predicate} that evaluates to true if the transaction is an {@code Income} */
    Predicate<Transaction> PREDICATE_SHOW_ALL_INCOMES = transaction -> transaction instanceof Income;

    Predicate<FrequentExpense> PREDICATE_SHOW_ALL_FREQUENT_EXPENSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Sets the user prefs' finance tracker file path.
     */
    void setFinanceTrackerFilePath(Path financeTrackerFilePath);

    /**
     * Replaces finance tracker data with the data in {@code financeTracker}.
     */
    void setFinanceTracker(ReadOnlyFinanceTracker financeTracker);

    /** Returns the FinanceTracker */
    ReadOnlyFinanceTracker getFinanceTracker();

    /**
     * Deletes the given transaction.
     * The transaction must exist in the finance tracker.
     */
    void deleteTransaction(Transaction target);

    /**
     * Deletes the given frequent expense.
     * The transaction must exist in the finance tracker.
     */
    void deleteFrequentExpense(FrequentExpense target);

    /**
     * Adds the given expense.
     */
    void addExpense(Expense expense);

    /**
     * Adds the given income.
     */
    void addIncome(Income income);

    /**
     * Adds the given frequent expense.
     */
    void addFrequentExpense(FrequentExpense frequentExpense);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the finance tracker.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /**
     * Replaces the given frequent expense {@code target} with {@code editedFrequentExpense}.
     * {@code target} must exist in the finance tracker.
     */
    void setFrequentExpense(FrequentExpense target, FrequentExpense editedFrequentExpense);

    /** Returns an unmodifiable view of the filtered transaction list. */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Returns an unmodifiable view of the filtered expense list. */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered income list. */
    ObservableList<Income> getFilteredIncomeList();

    /** Returns an unmodifiable view of the filtered frequent expense list. */
    ObservableList<FrequentExpense> getFilteredFrequentExpenseList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered income list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIncomeList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered frequent expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFrequentExpenseList(Predicate<FrequentExpense> predicate);

}
