package ay2021s1_cs2103_w16_3.finesse.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpenseList;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncomeList;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TransactionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the finance tracker level
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private final TransactionList transactions;
    private final FrequentExpenseList frequentExpenses;
    private final FrequentIncomeList frequentIncomes;
    private final MonthlyBudget monthlyBudget;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
        frequentExpenses = new FrequentExpenseList();
        frequentIncomes = new FrequentIncomeList();
        monthlyBudget = new MonthlyBudget();
    }

    public FinanceTracker() {}

    /**
     * Creates an FinanceTracker using the Transactions in the {@code toBeCopied}
     */
    public FinanceTracker(ReadOnlyFinanceTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Replaces the contents of the frequent expense list with {@code frequentExpenses}.
     */
    public void setFrequentExpenses(List<FrequentExpense> frequentExpenses) {
        this.frequentExpenses.setFrequentExpenses(frequentExpenses);
    }

    /**
     * Replaces the contents of the frequent incomes list with {@code frequentIncomes}.
     */
    public void setFrequentIncomes(List<FrequentIncome> frequentIncomes) {
        this.frequentIncomes.setFrequentIncomes(frequentIncomes);
    }

    /**
     * Resets the existing data of this {@code FinanceTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceTracker newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
        setFrequentExpenses(newData.getFrequentExpenseList());
        setFrequentIncomes(newData.getFrequentIncomeList());
        setMonthlyBudget(newData.getMonthlyBudget());
    }

    //// transaction-level operations

    /**
     * Adds a transaction to the finance tracker.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Adds a frequent expense to the finance tracker.
     */
    public void addFrequentExpense(FrequentExpense frequentExpense) {
        frequentExpenses.add(frequentExpense);
    }

    /**
     * Adds a frequent income to the finance tracker.
     */
    public void addFrequentIncome(FrequentIncome frequentIncome) {
        frequentIncomes.add(frequentIncome);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the finance tracker.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Replaces the given frequent expense {@code target} in the list with {@code editedFrequentExpense}.
     * {@code target} must exist in the frequent expense list.
     */
    public void setFrequentExpense(FrequentExpense target, FrequentExpense editedFrequentExpense) {
        requireNonNull(editedFrequentExpense);

        frequentExpenses.setFrequentExpense(target, editedFrequentExpense);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the finance tracker.
     */
    public void removeFrequentExpense(FrequentExpense key) {
        frequentExpenses.remove(key);
    }

    //// budget-level operations

    public void setExpenseLimit(Amount limit) {
        monthlyBudget.setMonthlyExpenseLimit(limit);
    }

    public void setMonthlyBudget(MonthlyBudget budget) {
        monthlyBudget.setMonthlyExpenseLimit(budget.getMonthlyExpenseLimit().getAmount());
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Expense) {
                expenses.add((Expense) t);
            }
        });
        return FXCollections.unmodifiableObservableList(expenses);
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        ObservableList<Income> incomes = FXCollections.observableArrayList();
        transactions.forEach(t -> {
            if (t instanceof Income) {
                incomes.add((Income) t);
            }
        });
        return FXCollections.unmodifiableObservableList(incomes);
    }

    @Override
    public ObservableList<FrequentExpense> getFrequentExpenseList() {
        return frequentExpenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<FrequentIncome> getFrequentIncomeList() {
        return frequentIncomes.asUnmodifiableObservableList();
    }

    public MonthlyBudget getMonthlyBudget() {
        return monthlyBudget;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && transactions.equals(((FinanceTracker) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }

}
