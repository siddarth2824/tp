package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INVESTING;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalBookmarkIncome;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.BookmarkTransactionNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionBuilder;

public class BookmarkIncomeListTest {

    private final BookmarkIncomeList bookmarkIncomeList = new BookmarkIncomeList();

    @Test
    public void add_nullBookmarkIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkIncomeList.add(null));
    }

    @Test
    public void add_duplicateBookmarkIncome_throwsDuplicateBookmarkTransactionException() {
        BookmarkIncome bookmarkIncome = new BookmarkTransactionBuilder(PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildBookmarkIncome();

        bookmarkIncomeList.add(bookmarkIncome);

        BookmarkIncome bookmarkIncomeCopy = new BookmarkTransactionBuilder(bookmarkIncome).buildBookmarkIncome();

        assertThrows(DuplicateBookmarkTransactionException.class, () -> bookmarkIncomeList.add(bookmarkIncomeCopy));
    }

    @Test
    public void setBookmarkIncome_nullTargetBookmarkIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkIncomeList.setBookmarkIncome(null, PART_TIME));
    }

    @Test
    public void setBookmarkIncome_nullEditedBookmarkIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkIncomeList.setBookmarkIncome(PART_TIME, null));
    }

    @Test
    public void setBookmarkIncome_targetBookmarkIncomeNotInList_throwsTransactionNotFoundException() {
        assertThrows(BookmarkTransactionNotFoundException.class, ()
            -> bookmarkIncomeList.setBookmarkIncome(PART_TIME, PART_TIME));
    }

    @Test
    public void setBookmarkIncome_editedBookmarkIncomeIsSameBookmarkIncome_success() {
        bookmarkIncomeList.add(PART_TIME);
        bookmarkIncomeList.setBookmarkIncome(PART_TIME, PART_TIME);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        expectedBookmarkIncomeList.add(PART_TIME);
        assertEquals(expectedBookmarkIncomeList, bookmarkIncomeList);
    }

    @Test
    public void setBookmarkIncome_editedBookmarkIncomeHasSameIdentity_success() {
        bookmarkIncomeList.add(PART_TIME);
        BookmarkIncome editedPartTime = new BookmarkTransactionBuilder(PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildBookmarkIncome();
        bookmarkIncomeList.setBookmarkIncome(PART_TIME, editedPartTime);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        expectedBookmarkIncomeList.add(editedPartTime);
        assertEquals(expectedBookmarkIncomeList, bookmarkIncomeList);
    }

    @Test
    public void setBookmarkIncome_editedBookmarkIncomeHasDifferentIdentity_success() {
        bookmarkIncomeList.add(PART_TIME);
        bookmarkIncomeList.setBookmarkIncome(PART_TIME, INVESTING);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        expectedBookmarkIncomeList.add(INVESTING);
        assertEquals(expectedBookmarkIncomeList, bookmarkIncomeList);
    }

    @Test
    public void remove_nullBookmarkIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkIncomeList.remove(null));
    }

    @Test
    public void remove_bookmarkBookmarkDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(BookmarkTransactionNotFoundException.class, () -> bookmarkIncomeList.remove(PART_TIME));
    }

    @Test
    public void remove_existingBookmarkIncome_removesBookmarkIncome() {
        bookmarkIncomeList.add(PART_TIME);
        bookmarkIncomeList.remove(PART_TIME);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        assertEquals(expectedBookmarkIncomeList, bookmarkIncomeList);
    }

    @Test
    public void setBookmarkIncomes_nullBookmarkIncomeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkIncomeList.setBookmarkIncomes((BookmarkIncomeList) null));
    }

    @Test
    public void setBookmarkIncomes_bookmarkIncomeList_replacesOwnListWithProvidedBookmarkIncomeList() {
        bookmarkIncomeList.add(PART_TIME);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        expectedBookmarkIncomeList.add(INVESTING);
        bookmarkIncomeList.setBookmarkIncomes(expectedBookmarkIncomeList);
        assertEquals(expectedBookmarkIncomeList, bookmarkIncomeList);
    }

    @Test
    public void setBookmarkIncomes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkIncomeList.setBookmarkIncomes((List<BookmarkIncome>) null));
    }

    @Test
    public void setBookmarkIncomes_list_replacesOwnListWithProvidedList() {
        bookmarkIncomeList.add(PART_TIME);
        List<BookmarkIncome> transactionList = Collections.singletonList(INVESTING);
        this.bookmarkIncomeList.setBookmarkIncomes(transactionList);
        BookmarkIncomeList expectedBookmarkIncomeList = new BookmarkIncomeList();
        expectedBookmarkIncomeList.add(INVESTING);
        assertEquals(expectedBookmarkIncomeList, this.bookmarkIncomeList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> bookmarkIncomeList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_distinctBookmarkIncomeListsWithSameAttributes_returnsTrue() {
        List<BookmarkIncome> bookmarkIncomes = getTypicalBookmarkIncome();
        BookmarkIncomeList firstTransactionList = new BookmarkIncomeList();
        BookmarkIncomeList secondTransactionList = new BookmarkIncomeList();

        firstTransactionList.setBookmarkIncomes(bookmarkIncomes);
        secondTransactionList.setBookmarkIncomes(bookmarkIncomes);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList, secondTransactionList);
    }

    @Test
    public void hashCode_distinctBookmarkIncomeListsWithSameAttributes_returnsTrue() {
        List<BookmarkIncome> bookmarkIncomes = getTypicalBookmarkIncome();
        BookmarkIncomeList firstTransactionList = new BookmarkIncomeList();
        BookmarkIncomeList secondTransactionList = new BookmarkIncomeList();

        firstTransactionList.setBookmarkIncomes(bookmarkIncomes);
        secondTransactionList.setBookmarkIncomes(bookmarkIncomes);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList.hashCode(), secondTransactionList.hashCode());
    }

}
