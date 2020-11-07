package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * A utility class to help in building BookmarkExpense and BookmarkIncome objects by parsing the input arguments.
 */
public class BookmarkTransactionBuilder {

    private final Title title;
    private final Amount amount;
    private final Set<Category> categories;

    /**
     * Creates a {@code BookmarkTransactionBuilder} with the given {@code Title}, {@code Amount} and {@code Category}.
     */
    public BookmarkTransactionBuilder(Title title, Amount amount, Set<Category> categories) {
        this.title = title;
        this.amount = amount;
        this.categories = categories;
    }

    public BookmarkExpense buildBookmarkExpense() {
        return new BookmarkExpense(title, amount, categories);
    }

    public BookmarkIncome buildBookmarkIncome() {
        return new BookmarkIncome(title, amount, categories);
    }

}
