package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentMultimap;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentTokenizer;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Prefix;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class AddBookmarkIncomeCommandParser implements Parser<AddBookmarkIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookmarkIncomeCommand
     * and returns an AddBookmarkIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookmarkIncomeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AMOUNT, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_AMOUNT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddBookmarkIncomeCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        BookmarkIncome bookmarkIncome = new BookmarkIncome(title, amount, categoryList);
        return new AddBookmarkIncomeCommand(bookmarkIncome);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
