package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_FREQUENT_INCOMES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * Edits the details of an existing frequent income using its displayed index from the frequent income list
 * in the income tab.
 */
public class EditFrequentIncomeCommand extends Command {

    public static final String COMMAND_WORD = "edit-frequent-income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the frequent income identified "
            + "by the index number used in the displayed frequent income list on the income tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5 ";

    public static final String MESSAGE_EDIT_FREQUENT_INCOME_SUCCESS = "Edited Frequent Income: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditFrequentTransactionDescriptor editFrequentIncomeDescriptor;

    /**
     * @param targetIndex Index of the frequent income in the filtered frequent income list to edit.
     * @param editFrequentIncomeDescriptor Details to edit the frequent income with.
     */
    public EditFrequentIncomeCommand(Index targetIndex,
                                     EditFrequentTransactionDescriptor editFrequentIncomeDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editFrequentIncomeDescriptor);

        this.targetIndex = targetIndex;
        this.editFrequentIncomeDescriptor = editFrequentIncomeDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FrequentIncome> lastShownList = model.getFilteredFrequentIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FREQUENT_INCOME_DISPLAYED_INDEX);
        }

        FrequentIncome frequentIncomeToEdit = lastShownList.get(targetIndex.getZeroBased());
        FrequentIncome editedFrequentIncome = createdEditedFrequentIncome(frequentIncomeToEdit,
                editFrequentIncomeDescriptor);

        model.setFrequentIncome(frequentIncomeToEdit, editedFrequentIncome);
        model.updateFilteredFrequentIncomeList(PREDICATE_SHOW_ALL_FREQUENT_INCOMES);
        return new CommandResult(String.format(MESSAGE_EDIT_FREQUENT_INCOME_SUCCESS, editedFrequentIncome));
    }

    /**
     * Creates and returns a {@code FrequentIncome} with the details of {@code frequentIncomeToEdit}
     * edited with {@code editFrequentIncomeDescriptor}.
     */
    private static FrequentIncome createdEditedFrequentIncome(FrequentIncome frequentIncomeToEdit,
                                                       EditFrequentTransactionDescriptor editFrequentIncomeDescriptor) {
        assert frequentIncomeToEdit != null;

        Title updatedTitle = editFrequentIncomeDescriptor.getTitle().orElse(frequentIncomeToEdit.getTitle());
        Amount updatedAmount = editFrequentIncomeDescriptor.getAmount().orElse(frequentIncomeToEdit.getAmount());
        Set<Category> updatedCategories = editFrequentIncomeDescriptor.getCategories()
                .orElse(frequentIncomeToEdit.getCategories());

        return new FrequentIncome(updatedTitle, updatedAmount, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditFrequentIncomeCommand)) {
            return false;
        }

        // state check
        EditFrequentIncomeCommand otherEditFrequentIncomeCommand = (EditFrequentIncomeCommand) other;
        return targetIndex.equals(otherEditFrequentIncomeCommand.targetIndex)
                && editFrequentIncomeDescriptor.equals(otherEditFrequentIncomeCommand.editFrequentIncomeDescriptor);
    }

}
