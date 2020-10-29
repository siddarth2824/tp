package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.DATE_DESC_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_DATE_INTERNSHIP;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.ConvertBookmarkIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

public class ConvertBookmarkIncomeCommandParserTest {

    private static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertBookmarkIncomeCommand.MESSAGE_USAGE);

    private ConvertBookmarkIncomeCommandParser parser = new ConvertBookmarkIncomeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DATE_DESC_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DATE_DESC_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DATE_DESC_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // multiple dates
        assertParseFailure(parser, INDEX_SECOND + DATE_DESC_INTERNSHIP + DATE_DESC_SPOTIFY_SUBSCRIPTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_DATE_CONSTRAINTS));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        Date convertedDate = new Date(VALID_DATE_INTERNSHIP);

        String userInput = targetIndex.getOneBased() + DATE_DESC_INTERNSHIP;

        ConvertBookmarkIncomeCommand expectedConvertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(targetIndex, convertedDate);

        assertParseSuccess(parser, userInput, expectedConvertBookmarkIncomeCommand);
    }

    @Test
    public void parse_optionalDateFieldMissing_success() {
        Index targetIndex = INDEX_SECOND;
        Date convertedDate = new Date(CURRENT_DATE);

        String userInput = String.valueOf(targetIndex.getOneBased());

        ConvertBookmarkIncomeCommand expectedConvertBookmarkIncomeCommand =
                new ConvertBookmarkIncomeCommand(targetIndex, convertedDate);

        assertParseSuccess(parser, userInput, expectedConvertBookmarkIncomeCommand);
    }

}
