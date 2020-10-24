package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;

/**
 * Deletes a frequent income identified using its displayed index from the finance tracker.
 */
public class DeleteFrequentIncomeCommand extends Command {
    public static final String COMMAND_WORD = "delete-frequent-income";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the frequent income identified by the index number used in the displayed "
            + "frequent income list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_FREQUENT_INCOME_SUCCESS = "Deleted Frequent Income: %1$s";

    private final Index targetIndex;

    public DeleteFrequentIncomeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentIncome> lastShownList = model.getFilteredFrequentIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
        }

        FrequentIncome frequentIncomeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFrequentIncome(frequentIncomeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FREQUENT_INCOME_SUCCESS, frequentIncomeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFrequentIncomeCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFrequentIncomeCommand) other).targetIndex)); // state check
    }
}
