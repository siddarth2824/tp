package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Edits the details of an existing transaction using its displayed index from the finance tracker
 * depending on the tab the user is on.
 *
 * Base class for EditExpenseCommand and EditIncomeCommand.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list on the current tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "When on Income tab: Edits from the currently displayed income list.\n"
            + "When on Expenses tab: Edits from the currently displayed expenses list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5 "
            + PREFIX_DATE + "22/09/2020";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param targetIndex Index of the transaction in the filtered transaction list to edit.
     * @param editTransactionDescriptor Details to edit the transaction with.
     */
    public EditCommand(Index targetIndex, EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editTransactionDescriptor);

        this.targetIndex = targetIndex;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    protected Index getTargetIndex() {
        return targetIndex;
    }

    protected EditTransactionDescriptor getEditTransactionDescriptor() {
        return editTransactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownList.get(targetIndex.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor);

        model.setTransaction(transactionToEdit, editedTransaction);
        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(Transaction transactionToEdit,
                                                       EditTransactionDescriptor editTransactionDescriptor) {
        assert transactionToEdit != null;

        Title updatedTitle = editTransactionDescriptor.getTitle().orElse(transactionToEdit.getTitle());
        Amount updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Date updatedDate = editTransactionDescriptor.getDate().orElse(transactionToEdit.getDate());
        Set<Category> updatedCategories = editTransactionDescriptor.getCategories()
                .orElse(transactionToEdit.getCategories());

        if (transactionToEdit instanceof Expense) {
            return new Expense(updatedTitle, updatedAmount, updatedDate, updatedCategories);
        } else {
            assert transactionToEdit instanceof Income;
            return new Income(updatedTitle, updatedAmount, updatedDate, updatedCategories);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editTransactionDescriptor.equals(e.editTransactionDescriptor);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private Title title;
        private Amount amount;
        private Date date;
        private Set<Category> categories;

        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setTitle(toCopy.title);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategories(toCopy.categories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, amount, date, categories);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            // state check
            EditTransactionDescriptor e = (EditTransactionDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getCategories().equals(e.getCategories());
        }
    }
}
