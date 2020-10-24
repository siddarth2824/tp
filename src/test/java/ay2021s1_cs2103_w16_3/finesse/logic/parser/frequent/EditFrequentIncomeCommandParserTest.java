package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.AMOUNT_DESC_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.CATEGORY_DESC_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.TITLE_DESC_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_MISCELLANEOUS;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers.EditFrequentIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditFrequentTransactionDescriptorBuilder;


public class EditFrequentIncomeCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFrequentIncomeCommand.MESSAGE_USAGE);
    private EditFrequentIncomeCommandParser parser = new EditFrequentIncomeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_PART_TIME, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditFrequentIncomeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_PART_TIME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_PART_TIME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category

        // invalid amount followed by valid category
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + CATEGORY_DESC_WORK,
                Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid amount
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_PART_TIME + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_CATEGORY} alone will reset the categories of the {@code FrequentIncome} being
        // edited, parsing it together with a valid category results in error
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK + CATEGORY_EMPTY,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_EMPTY + CATEGORY_DESC_WORK,
                Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_EMPTY + CATEGORY_DESC_FOOD_BEVERAGE + CATEGORY_DESC_WORK,
                Category.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_AMOUNT_DESC
                + CATEGORY_DESC_UTILITIES, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PART_TIME + CATEGORY_DESC_MISCELLANEOUS
                + TITLE_DESC_PART_TIME + CATEGORY_DESC_WORK;

        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_PART_TIME).withAmount(VALID_AMOUNT_PART_TIME)
                .withCategories(VALID_CATEGORY_WORK, VALID_CATEGORY_MISCELLANEOUS).build();
        EditFrequentIncomeCommand expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_PART_TIME;

        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_PART_TIME).build();
        EditFrequentIncomeCommand expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_PART_TIME;
        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder()
                .withTitle(VALID_TITLE_PART_TIME).build();
        EditFrequentIncomeCommand expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amounts
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_PART_TIME;
        descriptor = new EditFrequentTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_PART_TIME).build();
        expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // categories
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_UTILITIES;
        descriptor = new EditFrequentTransactionDescriptorBuilder().withCategories(VALID_CATEGORY_UTILITIES).build();
        expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetCategories_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;

        EditFrequentTransactionDescriptor descriptor = new EditFrequentTransactionDescriptorBuilder().withCategories()
                .build();
        EditFrequentIncomeCommand expectedCommand = new EditFrequentIncomeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
