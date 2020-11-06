package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Converts a specified bookmark expense and adds it as an expense to the finance tracker.
 */
public class ConvertBookmarkExpenseCommand extends ConvertBookmarkCommand {

    public static final String MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS = "Bookmark expense has been converted "
            + "and successfully added to finance tracker: %1$s";

    public ConvertBookmarkExpenseCommand(ConvertBookmarkCommand superCommand) {
        super(superCommand.getTargetIndex(), superCommand.getConvertedDate());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkExpense> lastShownList = model.getFilteredBookmarkExpenseList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
        }

        BookmarkExpense bookmarkExpenseToBeConverted = lastShownList.get(getTargetIndex().getZeroBased());
        Expense newExpenseToAdd = bookmarkExpenseToBeConverted.convert(getConvertedDate());
        model.addExpense(newExpenseToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS, newExpenseToAdd), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertBookmarkExpenseCommand)) {
            return false;
        }

        ConvertBookmarkExpenseCommand otherConvertBookmarkExpenseCommand = (ConvertBookmarkExpenseCommand) other;
        return getTargetIndex().equals(otherConvertBookmarkExpenseCommand.getTargetIndex())
                && getConvertedDate().equals(otherConvertBookmarkExpenseCommand.getConvertedDate());
    }
}
