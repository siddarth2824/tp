package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmarkcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
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
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

public class ConvertBookmarkIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        BookmarkIncome bookmarkIncomeToBeConverted = model.getFinanceTracker().getBookmarkIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkIncomeCommand convertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Income convertedIncome = bookmarkIncomeToBeConverted.convert(dateOfConvertedIncome);
        expectedModel.addIncome(convertedIncome);

        String expectedMessage = String.format(ConvertBookmarkIncomeCommand.MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS,
                convertedIncome);

        assertCommandSuccess(convertBookmarkIncomeCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkIncomeList().size() + 1);
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        ConvertBookmarkIncomeCommand convertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(outOfBoundIndex, dateOfConvertedIncome);

        assertCommandFailure(convertBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookmarkIncomeAtIndex(model, INDEX_FIRST);

        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        BookmarkIncome bookmarkIncomeToBeConverted = model.getFilteredBookmarkIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        ConvertBookmarkIncomeCommand convertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Income convertedIncome = bookmarkIncomeToBeConverted.convert(dateOfConvertedIncome);
        expectedModel.addIncome(convertedIncome);

        String expectedMessage = String.format(ConvertBookmarkIncomeCommand.MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS,
                convertedIncome);

        assertCommandSuccess(convertBookmarkIncomeCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookmarkIncomeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        // ensures that outOfBoundIndex is still in bounds of bookmark income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getBookmarkIncomeList().size());

        ConvertBookmarkIncomeCommand convertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(outOfBoundIndex, dateOfConvertedIncome);

        assertCommandFailure(convertBookmarkIncomeCommand, model, MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertBookmarkIncomeCommand convertFirstBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);
        ConvertBookmarkIncomeCommand convertSecondBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(INDEX_SECOND, dateOfConvertedIncome);

        // same object -> returns true
        assertTrue(convertFirstBookmarkIncomeCommand.equals(convertFirstBookmarkIncomeCommand));

        // same values -> returns true
        ConvertBookmarkIncomeCommand deleteFirstCommandCopy =
                new ConvertBookmarkIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);
        assertTrue(convertFirstBookmarkIncomeCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(convertFirstBookmarkIncomeCommand.equals(1));

        // null -> returns false
        assertFalse(convertFirstBookmarkIncomeCommand.equals(null));

        // different income -> returns false
        assertFalse(convertFirstBookmarkIncomeCommand.equals(convertSecondBookmarkIncomeCommand));
    }
}
