package ay2021s1_cs2103_w16_3.finesse.model.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_UTILITIES;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalBookmarkExpenses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.BookmarkTransactionNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions.DuplicateBookmarkTransactionException;
import ay2021s1_cs2103_w16_3.finesse.testutil.BookmarkTransactionBuilder;


public class BookmarkExpenseListTest {

    private final BookmarkExpenseList bookmarkExpenseList = new BookmarkExpenseList();

    @Test
    public void add_nullBookmarkExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkExpenseList.add(null));
    }

    @Test
    public void add_duplicateBookmarkExpense_throwsDuplicateBookmarkTransactionException() {
        BookmarkExpense bookmarkExpense = new BookmarkTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES).buildBookmarkExpense();

        bookmarkExpenseList.add(bookmarkExpense);

        BookmarkExpense bookmarkExpenseCopy = new BookmarkTransactionBuilder(bookmarkExpense).buildBookmarkExpense();

        assertThrows(DuplicateBookmarkTransactionException.class, () -> bookmarkExpenseList.add(bookmarkExpenseCopy));
    }

    @Test
    public void setBookmarkExpense_nullTargetBookmarkExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkExpenseList.setBookmarkExpense(null, PHONE_BILL));
    }

    @Test
    public void setBookmarkExpense_nullEditedBookmarkExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, null));
    }

    @Test
    public void setBookmarkExpense_targetBookmarkExpenseNotInList_throwsTransactionNotFoundException() {
        assertThrows(BookmarkTransactionNotFoundException.class, ()
            -> bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, PHONE_BILL));
    }

    @Test
    public void setBookmarkExpense_editedBookmarkExpenseIsSameBookmarkExpense_success() {
        bookmarkExpenseList.add(PHONE_BILL);
        bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, PHONE_BILL);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        expectedTransactionList.add(PHONE_BILL);
        assertEquals(expectedTransactionList, bookmarkExpenseList);
    }

    @Test
    public void setBookmarkExpense_editedBookmarkExpenseHasSameIdentity_success() {
        bookmarkExpenseList.add(PHONE_BILL);
        BookmarkExpense editedPhone = new BookmarkTransactionBuilder(PHONE_BILL)
                .withCategories(VALID_CATEGORY_UTILITIES).buildBookmarkExpense();
        bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, editedPhone);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        expectedTransactionList.add(editedPhone);
        assertEquals(expectedTransactionList, bookmarkExpenseList);
    }

    @Test
    public void setBookmarkExpense_editedBookmarkExpenseIsDuplicateOfAnotherBookmarkExpense() {
        bookmarkExpenseList.add(PHONE_BILL);
        bookmarkExpenseList.add(SPOTIFY_SUBSCRIPTION);
        BookmarkExpense editedPhoneBill = new BookmarkTransactionBuilder(PHONE_BILL)
                .withTitle(SPOTIFY_SUBSCRIPTION.getTitle().toString()).buildBookmarkExpense();
        assertThrows(DuplicateBookmarkTransactionException.class, ()
            -> bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, editedPhoneBill));
    }

    @Test
    public void setBookmarkExpense_editedBookmarkExpenseHasDifferentIdentity_success() {
        bookmarkExpenseList.add(PHONE_BILL);
        bookmarkExpenseList.setBookmarkExpense(PHONE_BILL, SPOTIFY_SUBSCRIPTION);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        assertEquals(expectedTransactionList, bookmarkExpenseList);
    }

    @Test
    public void remove_nullBookmarkExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkExpenseList.remove(null));
    }

    @Test
    public void remove_bookmarkExpenseDoesNotExist_throwsBookmarkTransactionNotFoundException() {
        assertThrows(BookmarkTransactionNotFoundException.class, () -> bookmarkExpenseList.remove(PHONE_BILL));
    }

    @Test
    public void remove_existingBookmarkExpense_removesBookmarkExpense() {
        bookmarkExpenseList.add(PHONE_BILL);
        bookmarkExpenseList.remove(PHONE_BILL);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        assertEquals(expectedTransactionList, bookmarkExpenseList);
    }

    @Test
    public void setBookmarkExpenses_nullBookmarkExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkExpenseList.setBookmarkExpenses((BookmarkExpenseList) null));
    }

    @Test
    public void setBookmarkExpenses_bookmarkExpenseList_replacesOwnListWithProvidedBookmarkExpenseList() {
        bookmarkExpenseList.add(PHONE_BILL);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        bookmarkExpenseList.setBookmarkExpenses(expectedTransactionList);
        assertEquals(expectedTransactionList, bookmarkExpenseList);
    }

    @Test
    public void setBookmarkExpenses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> bookmarkExpenseList.setBookmarkExpenses((List<BookmarkExpense>) null));
    }

    @Test
    public void setBookmarkExpenses_list_replacesOwnListWithProvidedList() {
        bookmarkExpenseList.add(PHONE_BILL);
        List<BookmarkExpense> transactionList = Collections.singletonList(SPOTIFY_SUBSCRIPTION);
        this.bookmarkExpenseList.setBookmarkExpenses(transactionList);
        BookmarkExpenseList expectedTransactionList = new BookmarkExpenseList();
        expectedTransactionList.add(SPOTIFY_SUBSCRIPTION);
        assertEquals(expectedTransactionList, this.bookmarkExpenseList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> bookmarkExpenseList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_distinctBookmarkExpenseListsWithSameAttributes_returnsTrue() {
        List<BookmarkExpense> bookmarkExpens = getTypicalBookmarkExpenses();
        BookmarkExpenseList firstTransactionList = new BookmarkExpenseList();
        BookmarkExpenseList secondTransactionList = new BookmarkExpenseList();

        firstTransactionList.setBookmarkExpenses(bookmarkExpens);
        secondTransactionList.setBookmarkExpenses(bookmarkExpens);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList, secondTransactionList);
    }

    @Test
    public void hashCode_distinctBookmarkExpenseListsWithSameAttributes_returnsTrue() {
        List<BookmarkExpense> bookmarkExpens = getTypicalBookmarkExpenses();
        BookmarkExpenseList firstTransactionList = new BookmarkExpenseList();
        BookmarkExpenseList secondTransactionList = new BookmarkExpenseList();

        firstTransactionList.setBookmarkExpenses(bookmarkExpens);
        secondTransactionList.setBookmarkExpenses(bookmarkExpens);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList.hashCode(), secondTransactionList.hashCode());
    }

}
