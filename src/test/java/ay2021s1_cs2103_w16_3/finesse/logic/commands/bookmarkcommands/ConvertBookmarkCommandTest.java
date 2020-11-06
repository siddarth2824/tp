package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandFailure;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ConvertBookmarkCommand}.
 */
public class ConvertBookmarkCommandTest {

    @Test
    public void execute_throwsCommandException() {
        Date dateOfConvertedTransaction = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        assertCommandFailure(new ConvertBookmarkCommand(INDEX_FIRST, dateOfConvertedTransaction), model,
                MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Test
    public void equals() {
        Date dateOfConvertedTransaction = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkCommand deleteBookmarkFirstCommand =
                new ConvertBookmarkCommand(INDEX_FIRST, dateOfConvertedTransaction);
        ConvertBookmarkCommand deleteBookmarkSecondCommand =
                new ConvertBookmarkCommand(INDEX_SECOND, dateOfConvertedTransaction);

        // same object -> returns true
        assertTrue(deleteBookmarkFirstCommand.equals(deleteBookmarkFirstCommand));

        // same values -> returns true
        ConvertBookmarkCommand deleteBookmarkFirstCommandCopy =
                new ConvertBookmarkCommand(INDEX_FIRST, dateOfConvertedTransaction);
        assertTrue(deleteBookmarkFirstCommand.equals(deleteBookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(null));

        // different transactions -> returns false
        assertFalse(deleteBookmarkFirstCommand.equals(deleteBookmarkSecondCommand));
    }
}
