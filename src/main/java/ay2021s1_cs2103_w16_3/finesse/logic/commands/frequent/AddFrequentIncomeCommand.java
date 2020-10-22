package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.DuplicateFrequentTransactionException;

public class AddFrequentIncomeCommand extends Command {
    public static final String COMMAND_WORD = "add-frequent-income";
    public static final String COMMAND_ALIAS = "addfi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a frequent income to the finance tracker. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Phone Bill "
            + PREFIX_AMOUNT + "24 "
            + PREFIX_CATEGORY + "Utilities";

    public static final String MESSAGE_SUCCESS = "New frequent income added: %1$s";

    private final FrequentIncome toAdd;

    /**
     * Creates an AddFrequentIncomeCommand to add the specified {@code FrequentIncome}
     * @param frequentIncome
     */
    public AddFrequentIncomeCommand(FrequentIncome frequentIncome) {
        requireNonNull(frequentIncome);
        toAdd = frequentIncome;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addFrequentIncome(toAdd);
        } catch (DuplicateFrequentTransactionException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFrequentIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddFrequentIncomeCommand) other).toAdd));
    }
}
