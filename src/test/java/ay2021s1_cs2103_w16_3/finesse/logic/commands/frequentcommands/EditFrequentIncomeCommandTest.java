package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequentcommands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PART_TIME;
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
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditFrequentTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class EditFrequentIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        FrequentIncome editedFrequentIncome = new FrequentTransactionBuilder().buildFrequentIncome();
        EditFrequentTransactionDescriptor descriptor =
                new EditFrequentTransactionDescriptorBuilder(editedFrequentIncome).build();
        EditFrequentIncomeCommand editFrequentIncomeCommand =
                new EditFrequentIncomeCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditFrequentIncomeCommand.MESSAGE_EDIT_FREQUENT_INCOME_SUCCESS,
                editedFrequentIncome);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setFrequentIncome(model.getFilteredFrequentIncomeList().get(0), editedFrequentIncome);

        assertCommandSuccess(editFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIncome = Index.fromOneBased(model.getFilteredFrequentIncomeList().size());
        FrequentIncome lastIncome = model.getFilteredFrequentIncomeList().get(indexLastIncome.getZeroBased());

        FrequentTransactionBuilder incomeInList = new FrequentTransactionBuilder(lastIncome);
        FrequentIncome editedIncome = incomeInList.withTitle(VALID_TITLE_PART_TIME)
                .withAmount(VALID_AMOUNT_PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildFrequentIncome();

        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_PART_TIME).withAmount(VALID_AMOUNT_PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).build();
        EditFrequentIncomeCommand editFrequentIncomeCommand =
                new EditFrequentIncomeCommand(indexLastIncome, descriptor);

        String expectedMessage = String.format(EditFrequentIncomeCommand.MESSAGE_EDIT_FREQUENT_INCOME_SUCCESS,
                editedIncome);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setFrequentIncome(lastIncome, editedIncome);

        assertCommandSuccess(editFrequentIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFrequentIncomeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFrequentIncomeList().size() + 1);
        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_PART_TIME).build();
        EditFrequentIncomeCommand editFrequentIncomeCommand =
                new EditFrequentIncomeCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered frequent income list where index is larger than size of filtered frequent income list,
     * but smaller than size of finance tracker
     */
    @Test
    public void execute_invalidFrequentIncomeIndexFilteredList_failure() {
        showFrequentIncomeAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of frequent income list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getFrequentIncomeList().size());

        EditFrequentIncomeCommand editFrequentIncomeCommand = new EditFrequentIncomeCommand(outOfBoundIndex,
                new EditFrequentTransactionDescriptorBuilder().withTitle(VALID_TITLE_PART_TIME).build());

        assertCommandFailure(editFrequentIncomeCommand, model, MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditFrequentIncomeCommand standardCommand =
                new EditFrequentIncomeCommand(INDEX_FIRST, DESC_SPOTIFY_SUBSCRIPTION);

        // same values -> returns true
        EditFrequentTransactionDescriptor copyDescriptor =
                new EditFrequentTransactionDescriptor(DESC_SPOTIFY_SUBSCRIPTION);
        EditFrequentIncomeCommand commandWithSameValues =
                new EditFrequentIncomeCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand
                .equals(new EditFrequentIncomeCommand(INDEX_SECOND, DESC_SPOTIFY_SUBSCRIPTION)));

        // different descriptor -> returns false
        assertFalse(standardCommand
                .equals(new EditFrequentIncomeCommand(INDEX_FIRST, DESC_PHONE_BILL)));
    }

}
