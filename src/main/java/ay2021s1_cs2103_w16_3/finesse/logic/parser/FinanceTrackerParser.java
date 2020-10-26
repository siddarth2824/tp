package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TAB_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.TabCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.ConvertBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.DeleteBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetExpenseLimitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.budget.SetSavingsGoalCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.AddBookmarkExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.AddBookmarkIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.ConvertBookmarkExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.ConvertBookmarkIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.DeleteBookmarkExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.DeleteBookmarkIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.EditBookmarkExpenseCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers.EditBookmarkIncomeCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.budgetparsers.SetExpenseLimitCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.budgetparsers.SetSavingsGoalCommandParser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Command word for the generic "add" command which adds an expense or an income to the finance tracker
     * depending on the tab the user is on.
     */
    public static final String ADD_COMMAND_COMMAND_WORD = "add";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Logger logger = LogsCenter.getLogger(FinanceTrackerParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input string.
     * @param uiState Current state of the UI.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput, UiState uiState) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final Tab uiCurrentTab = uiState.getCurrentTab();
        logger.info("----------------[CURRENT TAB][" + uiCurrentTab.toString() + "]");

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ADD_COMMAND_COMMAND_WORD:
            switch (uiCurrentTab) {
            case EXPENSES:
                return new AddExpenseCommandParser().parse(arguments);
            case INCOME:
                return new AddIncomeCommandParser().parse(arguments);
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.EXPENSES, Tab.INCOME));
            }

        case AddExpenseCommand.COMMAND_WORD:
        case AddExpenseCommand.COMMAND_ALIAS:
            return new AddExpenseCommandParser().parse(arguments);

        case AddIncomeCommand.COMMAND_WORD:
        case AddIncomeCommand.COMMAND_ALIAS:
            return new AddIncomeCommandParser().parse(arguments);

        case AddBookmarkExpenseCommand.COMMAND_WORD:
        case AddBookmarkExpenseCommand.COMMAND_ALIAS:
            return new AddBookmarkExpenseCommandParser().parse(arguments);

        case AddBookmarkIncomeCommand.COMMAND_WORD:
        case AddBookmarkIncomeCommand.COMMAND_ALIAS:
            return new AddBookmarkIncomeCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            switch (uiCurrentTab) {
            case EXPENSES:
                return new EditExpenseCommand(new EditCommandParser().parse(arguments));
            case INCOME:
                return new EditIncomeCommand(new EditCommandParser().parse(arguments));
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.EXPENSES, Tab.INCOME));
            }

        case EditBookmarkExpenseCommand.COMMAND_WORD:
            return new EditBookmarkExpenseCommandParser().parse(arguments);

        case EditBookmarkIncomeCommand.COMMAND_WORD:
            return new EditBookmarkIncomeCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            switch (uiCurrentTab) {
            case EXPENSES:
                return new DeleteExpenseCommand(new DeleteCommandParser().parse(arguments));
            case INCOME:
                return new DeleteIncomeCommand(new DeleteCommandParser().parse(arguments));
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.EXPENSES, Tab.INCOME));
            }

        case DeleteBookmarkExpenseCommand.COMMAND_WORD:
            return new DeleteBookmarkExpenseCommandParser().parse(arguments);

        case DeleteBookmarkIncomeCommand.COMMAND_WORD:
            return new DeleteBookmarkIncomeCommandParser().parse(arguments);

        case ConvertBookmarkExpenseCommand.COMMAND_WORD:
        case ConvertBookmarkExpenseCommand.COMMAND_ALIAS:
            return new ConvertBookmarkExpenseCommandParser().parse(arguments);

        case ConvertBookmarkIncomeCommand.COMMAND_WORD:
        case ConvertBookmarkIncomeCommand.COMMAND_ALIAS:
            return new ConvertBookmarkIncomeCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            switch (uiCurrentTab) {
            case OVERVIEW:
                return new FindTransactionCommand(new FindCommandParser().parse(arguments));
            case EXPENSES:
                return new FindExpenseCommand(new FindCommandParser().parse(arguments));
            case INCOME:
                return new FindIncomeCommand(new FindCommandParser().parse(arguments));
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.OVERVIEW, Tab.EXPENSES, Tab.INCOME));
            }

        case SetExpenseLimitCommand.COMMAND_WORD:
        case SetExpenseLimitCommand.COMMAND_ALIAS:
            return new SetExpenseLimitCommandParser().parse(arguments);

        case SetSavingsGoalCommand.COMMAND_WORD:
        case SetSavingsGoalCommand.COMMAND_ALIAS:
            return new SetSavingsGoalCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            switch (uiCurrentTab) {
            case OVERVIEW:
                return new ListTransactionCommand();
            case EXPENSES:
                return new ListExpenseCommand();
            case INCOME:
                return new ListIncomeCommand();
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.OVERVIEW, Tab.EXPENSES, Tab.INCOME));
            }

        case ListTransactionCommand.COMMAND_WORD:
        case ListTransactionCommand.COMMAND_ALIAS:
            return new ListTransactionCommand();

        case ListExpenseCommand.COMMAND_WORD:
        case ListExpenseCommand.COMMAND_ALIAS:
            return new ListExpenseCommand();

        case ListIncomeCommand.COMMAND_WORD:
        case ListIncomeCommand.COMMAND_ALIAS:
            return new ListIncomeCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case TabCommand.COMMAND_WORD:
            return new TabCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Error message to be used when a command is not applicable to the user's current tab.
     * @param command The command word that was used incorrectly.
     * @param tabs The tabs that the command is applicable to.
     * @return The error message to be displayed to the user.
     */
    private String commandInvalidTabMessage(String command, Tab... tabs) {
        return String.format(MESSAGE_INVALID_TAB_FORMAT, command,
                Stream.of(tabs).map(Tab::toString).collect(Collectors.joining(", ")));
    }

}
