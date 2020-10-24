package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_SPOTIFY_SUBSCRIPTION;
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
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.ConvertFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

public class ConvertFrequentIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        FrequentIncome frequentIncomeToBeConverted = model.getFinanceTracker().getFrequentIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertFrequentIncomeCommand convertFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Income convertedIncome = frequentIncomeToBeConverted.convert(dateOfConvertedIncome);
        expectedModel.addIncome(convertedIncome);

        String expectedMessage = String.format(ConvertFrequentIncomeCommand.MESSAGE_CONVERT_FREQUENT_INCOME_SUCCESS,
                convertedIncome);

        assertCommandSuccess(convertFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentIncomeList().size() + 1);
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        ConvertFrequentIncomeCommand convertFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(outOfBoundIndex, dateOfConvertedIncome);

        assertCommandFailure(convertFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFrequentIncomeAtIndex(model, INDEX_FIRST);

        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);

        FrequentIncome frequentIncomeToBeConverted = model.getFilteredFrequentIncomeList()
                .get(INDEX_FIRST.getZeroBased());
        ConvertFrequentIncomeCommand convertFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        Income convertedIncome = frequentIncomeToBeConverted.convert(dateOfConvertedIncome);
        expectedModel.addIncome(convertedIncome);

        String expectedMessage = String.format(ConvertFrequentIncomeCommand.MESSAGE_CONVERT_FREQUENT_INCOME_SUCCESS,
                convertedIncome);

        assertCommandSuccess(convertFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFrequentIncomeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        // ensures that outOfBoundIndex is still in bounds of frequent income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentIncomeList().size());

        ConvertFrequentIncomeCommand convertFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(outOfBoundIndex, dateOfConvertedIncome);

        assertCommandFailure(convertFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Date dateOfConvertedIncome = new Date(VALID_DATE_SPOTIFY_SUBSCRIPTION);
        ConvertFrequentIncomeCommand convertFirstFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);
        ConvertFrequentIncomeCommand convertSecondFrequentIncomeCommand =
                new ConvertFrequentIncomeCommand(INDEX_SECOND, dateOfConvertedIncome);

        // same object -> returns true
        assertTrue(convertFirstFrequentIncomeCommand.equals(convertFirstFrequentIncomeCommand));

        // same values -> returns true
        ConvertFrequentIncomeCommand deleteFirstCommandCopy =
                new ConvertFrequentIncomeCommand(INDEX_FIRST, dateOfConvertedIncome);
        assertTrue(convertFirstFrequentIncomeCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(convertFirstFrequentIncomeCommand.equals(1));

        // null -> returns false
        assertFalse(convertFirstFrequentIncomeCommand.equals(null));

        // different income -> returns false
        assertFalse(convertFirstFrequentIncomeCommand.equals(convertSecondFrequentIncomeCommand));
    }
}
