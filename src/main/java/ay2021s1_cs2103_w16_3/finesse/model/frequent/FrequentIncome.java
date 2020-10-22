package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

public class FrequentIncome extends FrequentTransaction<Income> {

    public FrequentIncome(Title title, Amount amount, Set<Category> categories) {
        super(title, amount, categories);
    }

    @Override
    public Income convert(Date date) {
        Title title = getTitle();
        Amount amount = getAmount();
        Set<Category> categories = getCategories();

        return new Income(title, amount, date, categories);
    }

    /**
     * Returns true if both frequent incomes have the same identity and data fields.
     * This defines a stronger notion of equality between two frequent incomes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FrequentIncome)) {
            return false;
        }
        FrequentIncome otherFrequentIncome = (FrequentIncome) other;
        return getTitle().equals(otherFrequentIncome.getTitle())
                && getAmount().equals(otherFrequentIncome.getAmount())
                && getCategories().equals(otherFrequentIncome.getCategories());
    }
}
