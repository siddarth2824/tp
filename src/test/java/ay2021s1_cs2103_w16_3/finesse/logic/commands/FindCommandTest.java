package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.AIMA;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.CARLS_JR;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.GST_VOUCHER;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PEN_REFILLS;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.TEACHING_ASSISTANT;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TitleContainsKeywordsPredicate;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0);
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywords_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 6);
        TitleContainsKeywordsPredicate predicate = preparePredicate("gst carl's artificial internship pen teaching");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Ordered by date, then by title.
        assertEquals(Arrays.asList(
                new TransactionBuilder(TEACHING_ASSISTANT).buildIncome(),
                new TransactionBuilder(AIMA).buildExpense(),
                new TransactionBuilder(CARLS_JR).buildExpense(),
                new TransactionBuilder(GST_VOUCHER).buildIncome(),
                new TransactionBuilder(INTERNSHIP).buildIncome(),
                new TransactionBuilder(PEN_REFILLS).buildExpense()
        ), model.getFilteredTransactionList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
