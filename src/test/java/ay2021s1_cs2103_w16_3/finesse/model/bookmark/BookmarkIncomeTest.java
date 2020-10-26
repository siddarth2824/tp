package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INVESTING;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PART_TIME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionBuilder;

public class BookmarkIncomeTest {

    @Test
    public void equals() {
        // same values -> returns true
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder(PART_TIME).buildBookmarkIncome();
        assertTrue(PART_TIME.equals(bookmarkIncome));

        // same object -> returns true
        assertTrue(PART_TIME.equals(PART_TIME));

        // null -> returns false
        assertFalse(PART_TIME.equals(null));

        // different type -> returns false
        assertFalse(PART_TIME.equals(5));

        // different bookmark expense -> returns false
        assertFalse(PART_TIME.equals(INVESTING));

        // different title -> returns false
        BookmarkIncome editedPartTime = new BookmarkTransactionBuilder(PART_TIME)
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).buildBookmarkIncome();
        assertFalse(PART_TIME.equals(editedPartTime));

        // different amounts -> returns false
        editedPartTime = new BookmarkTransactionBuilder(PART_TIME).withAmount(VALID_AMOUNT_PHONE_BILL)
                .buildBookmarkIncome();
        assertFalse(PART_TIME.equals(editedPartTime));

        // different categories -> returns false
        editedPartTime = new BookmarkTransactionBuilder(PART_TIME).withCategories(VALID_CATEGORY_FOOD_BEVERAGE)
                .buildBookmarkIncome();
        assertFalse(PART_TIME.equals(editedPartTime));
    }
}
