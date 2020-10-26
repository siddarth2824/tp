package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showBookmarkIncomeAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;

public class DeleteBookmarkIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        BookmarkIncome bookmarkIncomeToDelete = model.getFilteredBookmarkIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteBookmarkIncomeCommand deleteBookmarkIncomeCommand =
                new DeleteBookmarkIncomeCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookmarkIncomeCommand.MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS,
                bookmarkIncomeToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBookmarkIncome(bookmarkIncomeToDelete);

        assertCommandSuccess(deleteBookmarkIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkIncomeList().size() + 1);
        DeleteBookmarkIncomeCommand deleteBookmarkIncomeCommand = new DeleteBookmarkIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkIncomeAtIndex(model, INDEX_FIRST);

        BookmarkIncome bookmarkIncomeToDelete = model.getFilteredBookmarkIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteBookmarkIncomeCommand deleteBookmarkIncomeCommand =
                new DeleteBookmarkIncomeCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookmarkIncomeCommand.MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS,
                bookmarkIncomeToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBookmarkIncome(bookmarkIncomeToDelete);
        showNoBookmarkIncomes(expectedModel);

        assertCommandSuccess(deleteBookmarkIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkIncomeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of bookmark income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getBookmarkIncomeList().size());

        DeleteBookmarkIncomeCommand deleteBookmarkIncomeCommand = new DeleteBookmarkIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteBookmarkIncomeCommand deleteFirstCommand =
                new DeleteBookmarkIncomeCommand(INDEX_FIRST);
        DeleteBookmarkIncomeCommand deleteSecondCommand =
                new DeleteBookmarkIncomeCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookmarkIncomeCommand deleteFirstCommandCopy =
                new DeleteBookmarkIncomeCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different income -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no bookmark incomes.
     */
    private void showNoBookmarkIncomes(Model model) {
        model.updateFilteredBookmarkIncomeList(p -> false);

        assertTrue(model.getFilteredBookmarkIncomeList().isEmpty());
    }
}
