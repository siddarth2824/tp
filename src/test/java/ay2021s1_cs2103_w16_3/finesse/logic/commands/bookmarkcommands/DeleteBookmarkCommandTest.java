package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteBookmarkCommand}.
 */
public class DeleteBookmarkCommandTest {

    @Test
    public void execute_throwsCommandException() {
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        assertCommandFailure(new DeleteBookmarkCommand(INDEX_FIRST), model, MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Test
    public void equals() {
        DeleteBookmarkCommand deleteBookmarkFirstCommand = new DeleteBookmarkCommand(INDEX_FIRST);
        DeleteBookmarkCommand deleteBookmarkSecondCommand = new DeleteBookmarkCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteBookmarkFirstCommand.equals(deleteBookmarkFirstCommand));

        // same values -> returns true
        DeleteBookmarkCommand deleteBookmarkFirstCommandCopy = new DeleteBookmarkCommand(INDEX_FIRST);
        assertTrue(deleteBookmarkFirstCommand.equals(deleteBookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(null));

        // different transactions -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(deleteBookmarkSecondCommand));
    }

}
