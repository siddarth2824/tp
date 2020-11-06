package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Converts a specified bookmark income and adds it as an income to the finance tracker.
 */
public class ConvertBookmarkIncomeCommand extends ConvertBookmarkCommand {

    public static final String MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS = "Bookmark income has been converted "
            + "and successfully added to finance tracker: %1$s";

    public ConvertBookmarkIncomeCommand(ConvertBookmarkCommand superCommand) {
        super(superCommand.getTargetIndex(), superCommand.getConvertedDate());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToBeConverted = lastShownList.get(getTargetIndex().getZeroBased());
        Income newIncomeToAdd = bookmarkIncomeToBeConverted.convert(getConvertedDate());
        model.addIncome(newIncomeToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS, newIncomeToAdd), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertBookmarkIncomeCommand)) {
            return false;
        }

        ConvertBookmarkIncomeCommand otherConvertBookmarkIncomeCommand = (ConvertBookmarkIncomeCommand) other;
        return getTargetIndex().equals(otherConvertBookmarkIncomeCommand.getTargetIndex())
                && getConvertedDate().equals(otherConvertBookmarkIncomeCommand.getConvertedDate());
    }

}
