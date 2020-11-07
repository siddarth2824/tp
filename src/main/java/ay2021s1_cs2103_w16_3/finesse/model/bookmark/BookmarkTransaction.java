package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import java.util.Collections;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Represents a Bookmark Transaction in the finance tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class BookmarkTransaction<T extends Transaction> {

    public static final String MESSAGE_TITLE_CONSTRAINTS = "Bookmark transactions should only contain one title";
    public static final String MESSAGE_AMOUNT_CONSTRAINTS = "Bookmark transactions should only contain one amount";
    public static final String MESSAGE_CANNOT_CONTAIN_DATE = "Bookmark transactions should not contain any date";

    private final Title title;
    private final Amount amount;
    private final Set<Category> categories;

    protected BookmarkTransaction(Title title, Amount amount, Set<Category> categories) {
        this.title = title;
        this.amount = amount;
        this.categories = categories;
    }

    public Title getTitle() {
        return this.title;
    }

    public Amount getAmount() {
        return this.amount;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public abstract T convert(Date date);

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
