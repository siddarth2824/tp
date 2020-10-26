package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

public class AddBookmarkExpenseCommand extends Command {

    public static final String COMMAND_WORD = "add-bookmark-expense";
    public static final String COMMAND_ALIAS = "addbe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bookmark expense to the finance tracker. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Phone Bill "
            + PREFIX_AMOUNT + "24 "
            + PREFIX_CATEGORY + "Utilities";

    public static final String MESSAGE_SUCCESS = "New bookmark expense added: %1$s";

    private final BookmarkExpense toAdd;

    /**
     * Creates an AddBookmarkExpenseCommand to add the specified {@code BookmarkExpense}
     */
    public AddBookmarkExpenseCommand(BookmarkExpense bookmarkExpense) {
        requireNonNull(bookmarkExpense);
        toAdd = bookmarkExpense;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addBookmarkExpense(toAdd);
        } catch (DuplicateBookmarkTransactionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), Tab.EXPENSES);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBookmarkExpenseCommand // instanceof handles nulls
                && toAdd.equals(((AddBookmarkExpenseCommand) other).toAdd));
    }

}
