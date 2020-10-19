package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class FrequentExpense extends FrequentTransaction<Expense> {

    public FrequentExpense(Title title, Amount amount, Set<Category> categories) {
        super(title, amount, categories);
    }

    /**
     * Converts a frequent expense to a expense with new date.
     * @param newDate Date of when the frequent expense is converted to an expense.
     * @return a new {@code Expense} object.
     */
    public Expense convertFrequentExpenseToExpense(Date newDate) {
        Title title = super.getTitle();
        Amount amount = super.getAmount();
        Set<Category> categories = super.getCategories();
        return new Expense(title, amount, newDate, categories);
    }

    @Override
    public Expense convert(Date expenseDate) {
        Title title = super.getTitle();
        Amount amount = super.getAmount();
        Set<Category> categories = super.getCategories();

        return new Expense(title, amount, expenseDate, categories);
    }

    /**
     * Returns true if both frequent expenses of the same title have the same amount and same categories.
     * This defines a weaker notion of equality between 2 frequent expenses.
     */
    public boolean isSameFrequentExpense(FrequentExpense otherFrequentExpense) {
        if (otherFrequentExpense == this) {
            return true;
        }

        return otherFrequentExpense != null
                && otherFrequentExpense.getTitle().equals(getTitle())
                && otherFrequentExpense.getAmount().equals(getAmount())
                && otherFrequentExpense.getCategories().equals(getCategories());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FrequentExpense)) {
            return false;
        }

        FrequentExpense otherFrequentExpense = (FrequentExpense) other;
        return otherFrequentExpense.getTitle().equals(getTitle())
                && otherFrequentExpense.getAmount().equals(getAmount())
                && otherFrequentExpense.getCategories().equals(getCategories());
    }
}
