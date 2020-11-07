package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;

/**
 * Parses input arguments and creates a new AddBookmarkExpenseCommand object
 */
public class AddBookmarkExpenseCommandParser implements Parser<AddBookmarkExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookmarkExpenseCommand
     * and returns an AddBookmarkExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookmarkExpenseCommand parse(String args) throws ParseException {
        BookmarkTransactionBuilder bookmarkTransactionBuilder = ParserUtil
                .parseBookmarkTransactionBuilder(args, AddBookmarkExpenseCommand.MESSAGE_USAGE);
        BookmarkExpense toAdd = bookmarkTransactionBuilder.buildBookmarkExpense();
        return new AddBookmarkExpenseCommand(toAdd);
    }

}
