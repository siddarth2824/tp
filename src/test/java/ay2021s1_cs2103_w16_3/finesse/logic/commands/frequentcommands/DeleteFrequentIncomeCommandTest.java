package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showFrequentIncomeAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.DeleteFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;

public class DeleteFrequentIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        FrequentIncome frequentIncomeToDelete = model.getFilteredFrequentIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteFrequentIncomeCommand deleteFrequentIncomeCommand =
                new DeleteFrequentIncomeCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteFrequentIncomeCommand.MESSAGE_DELETE_FREQUENT_INCOME_SUCCESS,
                frequentIncomeToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteFrequentIncome(frequentIncomeToDelete);

        assertCommandSuccess(deleteFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentIncomeList().size() + 1);
        DeleteFrequentIncomeCommand deleteFrequentIncomeCommand = new DeleteFrequentIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFrequentIncomeAtIndex(model, INDEX_FIRST);

        FrequentIncome frequentIncomeToDelete = model.getFilteredFrequentIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteFrequentIncomeCommand deleteFrequentIncomeCommand =
                new DeleteFrequentIncomeCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteFrequentIncomeCommand.MESSAGE_DELETE_FREQUENT_INCOME_SUCCESS,
                frequentIncomeToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteFrequentIncome(frequentIncomeToDelete);
        showNoFrequentIncomes(expectedModel);

        assertCommandSuccess(deleteFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFrequentIncomeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of frequent income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentIncomeList().size());

        DeleteFrequentIncomeCommand deleteFrequentIncomeCommand = new DeleteFrequentIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteFrequentIncomeCommand deleteFirstCommand =
                new DeleteFrequentIncomeCommand(INDEX_FIRST);
        DeleteFrequentIncomeCommand deleteSecondCommand =
                new DeleteFrequentIncomeCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteFrequentIncomeCommand deleteFirstCommandCopy =
                new DeleteFrequentIncomeCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different income -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no frequent incomes.
     */
    private void showNoFrequentIncomes(Model model) {
        model.updateFilteredFrequentIncomeList(p -> false);

        assertTrue(model.getFilteredFrequentIncomeList().isEmpty());
    }
}
