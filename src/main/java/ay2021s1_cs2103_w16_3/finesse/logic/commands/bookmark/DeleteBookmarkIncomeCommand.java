package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;

/**
 * Deletes a bookmark income identified using its displayed index from the finance tracker.
 */
public class DeleteBookmarkIncomeCommand extends DeleteBookmarkCommand {

    public static final String MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS = "Deleted Bookmark Income: %1$s";

    public DeleteBookmarkIncomeCommand(DeleteBookmarkCommand superCommand) {
        super(superCommand.getTargetIndex());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deleteBookmarkIncome(bookmarkIncomeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS, bookmarkIncomeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookmarkIncomeCommand // instanceof handles nulls
                && getTargetIndex().equals(((DeleteBookmarkIncomeCommand) other).getTargetIndex())); // state check
    }
}
