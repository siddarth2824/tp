package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Converts a specified frequent income and adds it as an income to the finance tracker.
 */
public class ConvertFrequentIncomeCommand extends Command {
    public static final String COMMAND_WORD = "convert-frequent-income";
    public static final String COMMAND_ALIAS = "convertfi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the specified frequent income and adds"
            + " it as an income to the finance tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "03/10/2020 ";

    public static final String MESSAGE_CONVERT_FREQUENT_INCOME_SUCCESS = "Frequent income has been converted "
            + "and successfully added to finance tracker: %1$s";

    private final Index targetIndex;
    private final Date date;

    /**
     * @param targetIndex Index of the frequent income in the filtered frequent income list to convert.
     * @param convertDate Date of converting a frequent income to an income and adding it to the finance tracker.
     */
    public ConvertFrequentIncomeCommand(Index targetIndex, Date convertDate) {
        this.targetIndex = targetIndex;
        this.date = convertDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentIncome> lastShownList = model.getFilteredFrequentIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
        }

        FrequentIncome frequentIncomeToBeConverted = lastShownList.get(targetIndex.getZeroBased());
        Income newIncomeToAdd = frequentIncomeToBeConverted.convert(date);
        model.addIncome(newIncomeToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_FREQUENT_INCOME_SUCCESS, newIncomeToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertFrequentIncomeCommand)) {
            return false;
        }

        ConvertFrequentIncomeCommand otherConvertFrequentIncomeCommand = (ConvertFrequentIncomeCommand) other;
        return targetIndex.equals(otherConvertFrequentIncomeCommand.targetIndex)
                && date.equals(otherConvertFrequentIncomeCommand.date);
    }

}
