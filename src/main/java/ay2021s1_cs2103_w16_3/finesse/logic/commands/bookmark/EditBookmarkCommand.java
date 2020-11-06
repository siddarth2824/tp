package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * Edits the details of an existing bookmark transaction using its displayed index from the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for EditBookmarkExpenseCommand and EditBookmarkIncomeCommand.
 */
public class EditBookmarkCommand extends Command {
    public static final String COMMAND_WORD = "edit-bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bookmark transaction "
            + "identified by the index number used in the displayed bookmark transaction list on the current tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "When on Income tab: Edits from the currently displayed bookmark incomes list.\n"
            + "When on Expenses tab: Edits from the currently displayed bookmark expenses list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5 ";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditBookmarkTransactionDescriptor editBookmarkTransactionDescriptor;

    /**
     * @param targetIndex Index of the bookmark transaction in the respective filtered bookmark transaction list to
     *                    edit.
     * @param editBookmarkTransactionDescriptor Details to edit the bookmark transaction with.
     */
    public EditBookmarkCommand(Index targetIndex, EditBookmarkTransactionDescriptor editBookmarkTransactionDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editBookmarkTransactionDescriptor);

        this.targetIndex = targetIndex;
        this.editBookmarkTransactionDescriptor = editBookmarkTransactionDescriptor;
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    protected EditBookmarkTransactionDescriptor getEditBookmarkTransactionDescriptor() {
        return editBookmarkTransactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookmarkCommand)) {
            return false;
        }

        // state check
        EditBookmarkCommand e = (EditBookmarkCommand) other;
        return targetIndex.equals(e.targetIndex)
                && getEditBookmarkTransactionDescriptor().equals(e.getEditBookmarkTransactionDescriptor());
    }

}
