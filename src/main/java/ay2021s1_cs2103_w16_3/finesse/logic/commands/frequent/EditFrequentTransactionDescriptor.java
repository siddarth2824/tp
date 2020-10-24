package ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * Stores the details to edit the frequent transaction with. Each non-empty field value will replace the
 * corresponding field value of the frequent transaction.
 */
public class EditFrequentTransactionDescriptor {
    private Title title;
    private Amount amount;
    private Set<Category> categories;

    public EditFrequentTransactionDescriptor() { }

    /**
     * Copy constructor.
     * A defensive copy of {@code categories} is used internally.
     */
    public EditFrequentTransactionDescriptor(EditFrequentTransactionDescriptor toCopy) {
        setTitle(toCopy.title);
        setAmount(toCopy.amount);
        setCategories(toCopy.categories);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(title, amount, categories);
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
        if (!(other instanceof EditFrequentTransactionDescriptor)) {
            return false;
        }

        // state check
        EditFrequentTransactionDescriptor e = (EditFrequentTransactionDescriptor) other;

        return getTitle().equals(e.getTitle())
                && getAmount().equals(e.getAmount())
                && getCategories().equals(e.getCategories());
    }

}
