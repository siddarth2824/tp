package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_BOOKMARK_INCOMES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * Edits the details of an existing bookmark income using its displayed index from the bookmark income list
 * in the income tab.
 */
public class EditBookmarkIncomeCommand extends EditBookmarkCommand {

    public static final String MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS = "Edited Bookmark Income: %1$s";

    public EditBookmarkIncomeCommand(EditBookmarkCommand superCommand) {
        super(superCommand.getTargetIndex(), superCommand.getEditBookmarkTransactionDescriptor());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToEdit = lastShownList.get(getTargetIndex().getZeroBased());
        BookmarkIncome editedBookmarkIncome = createdEditedBookmarkIncome(bookmarkIncomeToEdit,
                getEditBookmarkTransactionDescriptor());
        try {
            model.setBookmarkIncome(bookmarkIncomeToEdit, editedBookmarkIncome);
        } catch (DuplicateBookmarkTransactionException e) {
            throw new CommandException(e.getMessage());
        }
        model.updateFilteredBookmarkIncomeList(PREDICATE_SHOW_ALL_BOOKMARK_INCOMES);
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS, editedBookmarkIncome));
    }

    /**
     * Creates and returns a {@code BookmarkIncome} with the details of {@code bookmarkIncomeToEdit}
     * edited with {@code editBookmarkIncomeDescriptor}.
     */
    private static BookmarkIncome createdEditedBookmarkIncome(BookmarkIncome bookmarkIncomeToEdit,
                                                              EditBookmarkTransactionDescriptor
                                                              editBookmarkIncomeDescriptor) {
        assert bookmarkIncomeToEdit != null;

        Title updatedTitle = editBookmarkIncomeDescriptor.getTitle().orElse(bookmarkIncomeToEdit.getTitle());
        Amount updatedAmount = editBookmarkIncomeDescriptor.getAmount().orElse(bookmarkIncomeToEdit.getAmount());
        Set<Category> updatedCategories = editBookmarkIncomeDescriptor.getCategories()
                .orElse(bookmarkIncomeToEdit.getCategories());

        return new BookmarkIncome(updatedTitle, updatedAmount, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookmarkIncomeCommand)) {
            return false;
        }

        // state check
        EditBookmarkIncomeCommand otherEditBookmarkIncomeCommand = (EditBookmarkIncomeCommand) other;
        return getTargetIndex().equals(otherEditBookmarkIncomeCommand.getTargetIndex())
                && getEditBookmarkTransactionDescriptor()
                .equals(otherEditBookmarkIncomeCommand.getEditBookmarkTransactionDescriptor());
    }

}
