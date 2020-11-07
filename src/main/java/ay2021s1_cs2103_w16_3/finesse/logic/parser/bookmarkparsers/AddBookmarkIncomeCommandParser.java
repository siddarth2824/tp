package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;

/**
 * Parses input arguments and creates a new AddBookmarkIncomeCommand object
 */
public class AddBookmarkIncomeCommandParser implements Parser<AddBookmarkIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookmarkIncomeCommand
     * and returns an AddBookmarkIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookmarkIncomeCommand parse(String args) throws ParseException {
        BookmarkTransactionBuilder bookmarkTransactionBuilder = ParserUtil
                .parseBookmarkTransactionBuilder(args, AddBookmarkIncomeCommand.MESSAGE_USAGE);
        BookmarkIncome toAdd = bookmarkTransactionBuilder.buildBookmarkIncome();
        return new AddBookmarkIncomeCommand(toAdd);
    }

}
