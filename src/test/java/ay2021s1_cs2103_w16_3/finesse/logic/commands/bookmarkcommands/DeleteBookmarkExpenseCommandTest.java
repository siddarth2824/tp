package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.showBookmarkExpenseAtIndex;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;

public class DeleteBookmarkExpenseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        BookmarkExpense bookmarkExpenseToDelete = model.getFilteredBookmarkExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteBookmarkExpenseCommand deleteBookmarkExpenseCommand =
                new DeleteBookmarkExpenseCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookmarkExpenseCommand.MESSAGE_DELETE_BOOKMARK_EXPENSE_SUCCESS,
                bookmarkExpenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBookmarkExpense(bookmarkExpenseToDelete);

        assertCommandSuccess(deleteBookmarkExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkExpenseList().size() + 1);
        DeleteBookmarkExpenseCommand deleteBookmarkExpenseCommand = new DeleteBookmarkExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookmarkExpenseCommand, model, MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkExpenseAtIndex(model, INDEX_FIRST);

        BookmarkExpense bookmarkExpenseToDelete = model.getFilteredBookmarkExpenseList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteBookmarkExpenseCommand deleteBookmarkExpenseCommand =
                new DeleteBookmarkExpenseCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookmarkExpenseCommand.MESSAGE_DELETE_BOOKMARK_EXPENSE_SUCCESS,
                bookmarkExpenseToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBookmarkExpense(bookmarkExpenseToDelete);
        showNoBookmarkExpenses(expectedModel);

        assertCommandSuccess(deleteBookmarkExpenseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkExpenseAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of bookmark expense list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getBookmarkExpenseList().size());

        DeleteBookmarkExpenseCommand deleteBookmarkExpenseCommand = new DeleteBookmarkExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookmarkExpenseCommand, model, MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteBookmarkExpenseCommand deleteFirstCommand =
                new DeleteBookmarkExpenseCommand(INDEX_FIRST);
        DeleteBookmarkExpenseCommand deleteSecondCommand =
                new DeleteBookmarkExpenseCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookmarkExpenseCommand deleteFirstCommandCopy =
                new DeleteBookmarkExpenseCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no bookmark expenses.
     */
    private void showNoBookmarkExpenses(Model model) {
        model.updateFilteredBookmarkExpenseList(p -> false);

        assertTrue(model.getFilteredBookmarkExpenseList().isEmpty());
    }
}
