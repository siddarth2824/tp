package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.AddFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyUserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;
import javafx.collections.ObservableList;

public class AddFrequentIncomeCommandTest {

    @Test
    public void constructor_nullFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFrequentIncomeCommand(null));
    }

    @Test
    public void execute_frequentIncomeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFrequentIncomeAdded modelStub = new ModelStubAcceptingFrequentIncomeAdded();
        FrequentIncome validFrequentIncome = new FrequentTransactionBuilder().buildFrequentIncome();

        CommandResult commandResult = new AddFrequentIncomeCommand(validFrequentIncome).execute(modelStub);

        assertEquals(String.format(AddFrequentIncomeCommand.MESSAGE_SUCCESS, validFrequentIncome),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFrequentIncome), modelStub.frequentIncomesAdded);
    }

    @Test
    public void equals() {
        FrequentIncome bubbleTea = new FrequentTransactionBuilder().withTitle("Bubble Tea").buildFrequentIncome();
        FrequentIncome tuitionFees = new FrequentTransactionBuilder().withTitle("Tuition Fees").buildFrequentIncome();
        AddFrequentIncomeCommand addBubbleTeaCommand = new AddFrequentIncomeCommand(bubbleTea);
        AddFrequentIncomeCommand addTuitionFeesCommand = new AddFrequentIncomeCommand(tuitionFees);

        // same object -> returns true
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommand));

        // same values -> returns true
        AddFrequentIncomeCommand addBubbleTeaCommandCopy = new AddFrequentIncomeCommand(bubbleTea);
        assertTrue(addBubbleTeaCommand.equals(addBubbleTeaCommandCopy));

        // different types -> returns false
        assertFalse(addBubbleTeaCommand.equals(1));

        // null -> returns false
        assertFalse(addBubbleTeaCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addBubbleTeaCommand.equals(addTuitionFeesCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFinanceTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFrequentExpense(FrequentExpense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFrequentIncome(FrequentIncome target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFrequentExpense(FrequentExpense frequentExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFrequentIncome(FrequentIncome frequentIncome) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFrequentExpense(FrequentExpense target, FrequentExpense editedFrequentExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFrequentIncome(FrequentIncome target, FrequentIncome editedFrequentIncome) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MonthlyBudget getMonthlyBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenseLimit(Amount limit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSavingsGoal(Amount goal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Income> getFilteredIncomeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FrequentExpense> getFilteredFrequentExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FrequentIncome> getFilteredFrequentIncomeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIncomeList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFrequentExpenseList(Predicate<FrequentExpense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFrequentIncomeList(Predicate<FrequentIncome> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the frequent expense being added.
     */
    private class ModelStubAcceptingFrequentIncomeAdded extends ModelStub {
        final ArrayList<FrequentIncome> frequentIncomesAdded = new ArrayList<>();

        @Override
        public void addFrequentIncome(FrequentIncome frequentIncome) {
            requireNonNull(frequentIncome);
            frequentIncomesAdded.add(frequentIncome);
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

}
