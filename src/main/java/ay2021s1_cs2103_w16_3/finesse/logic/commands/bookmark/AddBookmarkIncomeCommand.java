package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class AddBookmarkIncomeCommand extends Command {

    public static final String COMMAND_WORD = "add-bookmark-income";
    public static final String COMMAND_ALIAS = "addbi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bookmark income to the finance tracker. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Summer Internship "
            + PREFIX_AMOUNT + "1000 "
            + PREFIX_CATEGORY + "Work";

    public static final String MESSAGE_SUCCESS = "New bookmark income added: %1$s";

    private final BookmarkIncome toAdd;

    /**
     * Creates an AddBookmarkIncomeCommand to add the specified {@code BookmarkIncome}
     * @param bookmarkIncome
     */
    public AddBookmarkIncomeCommand(BookmarkIncome bookmarkIncome) {
        requireNonNull(bookmarkIncome);
        toAdd = bookmarkIncome;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addBookmarkIncome(toAdd);
        } catch (DuplicateBookmarkTransactionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), Tab.INCOME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBookmarkIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddBookmarkIncomeCommand) other).toAdd));
    }

}
