package ay2021s1_cs2103_w16_3.finesse.logic.parser.frequentparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.DeleteFrequentIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;

/**
 * Parser input arguments and creates a new DeleteFrequentIncomeCommand object
 */
public class DeleteFrequentIncomeCommandParser implements Parser<DeleteFrequentIncomeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFrequentIncomeCommand
     * and returns a DeleteFrequentIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFrequentIncomeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFrequentIncomeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFrequentIncomeCommand.MESSAGE_USAGE), pe);
        }
    }
}
