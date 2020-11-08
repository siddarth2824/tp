package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PHONE_BILL;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.AddBookmarkExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.BookmarkTransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class AddBookmarkExpenseCommandParserTest {
    private AddBookmarkExpenseCommandParser parser = new AddBookmarkExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        BookmarkExpense expectedBookmarkExpense = new BookmarkTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES).buildBookmarkExpense();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                + CATEGORY_DESC_UTILITIES, new AddBookmarkExpenseCommand(expectedBookmarkExpense));

        // multiple categories - all accepted
        BookmarkExpense expectedExpenseMultipleCategories = new BookmarkTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES, VALID_CATEGORY_WORK).buildBookmarkExpense();

        assertParseSuccess(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                        + CATEGORY_DESC_UTILITIES + CATEGORY_DESC_WORK,
                new AddBookmarkExpenseCommand(expectedExpenseMultipleCategories));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero categories
        BookmarkExpense expectedBookmarkExpense = new BookmarkTransactionBuilder(PHONE_BILL).withCategories()
                .buildBookmarkExpense();
        assertParseSuccess(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL,
                new AddBookmarkExpenseCommand(expectedBookmarkExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookmarkExpenseCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL + AMOUNT_DESC_PHONE_BILL, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + VALID_AMOUNT_PHONE_BILL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_PHONE_BILL + VALID_AMOUNT_PHONE_BILL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + AMOUNT_DESC_PHONE_BILL
                + CATEGORY_DESC_UTILITIES + CATEGORY_DESC_WORK, Title.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + INVALID_AMOUNT_DESC
                + CATEGORY_DESC_UTILITIES + CATEGORY_DESC_WORK, Amount.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                + INVALID_CATEGORY_DESC + CATEGORY_DESC_UTILITIES, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_AMOUNT_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_PHONE_BILL
                        + CATEGORY_DESC_UTILITIES + CATEGORY_DESC_WORK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookmarkExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple titles
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + TITLE_DESC_SPOTIFY_SUBSCRIPTION
                        + AMOUNT_DESC_PHONE_BILL + CATEGORY_DESC_UTILITIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_TITLE_CONSTRAINTS));

        // multiple amounts
        assertParseFailure(parser, TITLE_DESC_PHONE_BILL + AMOUNT_DESC_PHONE_BILL
                        + AMOUNT_DESC_SPOTIFY_SUBSCRIPTION + CATEGORY_DESC_UTILITIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkTransaction.MESSAGE_AMOUNT_CONSTRAINTS));
    }

}
