package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class AddExpenseCommandParser implements Parser<AddExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_CATEGORY);

        if (!argMultimap.arePrefixesPresent(PREFIX_TITLE, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_TITLE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_TITLE_CONSTRAINTS));
        }

        if (argMultimap.moreThanOneValuePresent(PREFIX_AMOUNT)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_AMOUNT_CONSTRAINTS));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        Date date;

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (argMultimap.moreThanOneValuePresent(PREFIX_DATE)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, Transaction.MESSAGE_DATE_CONSTRAINTS));
            } else {
                date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            }
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate currentDate = LocalDate.now();
            date = new Date(dateTimeFormatter.format(currentDate));
        }

        Expense expense = new Expense(title, amount, date, categoryList);

        return new AddExpenseCommand(expense);
    }

}
