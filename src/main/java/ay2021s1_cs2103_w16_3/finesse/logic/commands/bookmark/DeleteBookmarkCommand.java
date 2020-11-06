package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Deletes a bookmark transaction identified using its displayed index from the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for DeleteBookmarkExpenseCommand and DeleteBookmarkIncomeCommand.
 */
public class DeleteBookmarkCommand extends Command {
    public static final String COMMAND_WORD = "delete-bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the bookmark transaction identified by the index number used in the displayed "
            + "bookmark transaction list on the current tab. \n"
            + "When on Income tab: Deletes from the currently displayed bookmark income list.\n"
            + "When on Expenses tab: Deletes from the currently displayed bookmark expenses list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;

    public DeleteBookmarkCommand(Index index) {
        this.targetIndex = index;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookmarkCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBookmarkCommand) other).targetIndex)); // state check
    }
}
