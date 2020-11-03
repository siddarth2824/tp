package ay2021s1_cs2103_w16_3.finesse.testutil;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.AddBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Utility class for Bookmark Transaction.
 */
public class BookmarkTransactionUtil {

    public static String getAddBookmarkIncomeCommand(BookmarkIncome bookmarkIncome) {
        return AddBookmarkIncomeCommand.COMMAND_WORD + " " + getTransactionDetails(bookmarkIncome);
    }

    public static String getAddBookmarkExpenseCommand(BookmarkExpense bookmarkExpense) {
        return AddBookmarkExpenseCommand.COMMAND_WORD + " " + getTransactionDetails(bookmarkExpense);
    }

    /**
     * Returns the part of command string for the given {@code BookmarkTransaction}'s details.
     */
    public static <T extends Transaction> String getTransactionDetails(BookmarkTransaction<T> bookmarkTransaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + bookmarkTransaction.getTitle().toString() + " ");
        sb.append(PREFIX_AMOUNT + bookmarkTransaction.getAmount().toString() + " ");
        bookmarkTransaction.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.getCategoryName() + " ")
        );
        return sb.toString();
    }

    public static String getEditBookmarkTransactionDescriptorDetails(EditBookmarkTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.toString()).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> categories = descriptor.getCategories().get();
            if (categories.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                categories.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.getCategoryName()).append(" "));
            }
        }
        return sb.toString();
    }
}
