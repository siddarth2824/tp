package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteBookmarkCommand object
 */
public class DeleteBookmarkCommandParser implements Parser<DeleteBookmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBookmarkCommand
     * and returns a DeleteBookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBookmarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteBookmarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBookmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
