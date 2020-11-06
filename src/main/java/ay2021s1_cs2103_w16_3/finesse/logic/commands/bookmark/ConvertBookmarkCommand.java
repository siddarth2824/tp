package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;

/**
 * Converts a specified bookmark transaction and adds it as an expense to the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for ConvertBookmarkExpenseCommand and ConvertBookmarkIncomeCommand.
 */
public class ConvertBookmarkCommand extends Command {
    public static final String COMMAND_WORD = "convert-bookmark";
    public static final String COMMAND_ALIAS = "convertb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the specified bookmark transaction and adds"
            + " it as a transaction to the finance tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Inputting the date is optional. If no input is given for d/DATE, the current date will be used.\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "03/10/2020 ";

    private final Index targetIndex;
    private final Date convertedDate;

    /**
     * @param targetIndex Index of the bookmark transaction in its respective bookmark transaction list to convert.
     * @param convertedDate Date of conversion of bookmark transaction to a transaction.
     */
    public ConvertBookmarkCommand(Index targetIndex, Date convertedDate) {
        this.targetIndex = targetIndex;
        this.convertedDate = convertedDate;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    protected Date getConvertedDate() {
        return convertedDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConvertBookmarkCommand // instanceof handles nulls
                && targetIndex.equals(((ConvertBookmarkCommand) other).targetIndex)); // state check
    }
}
