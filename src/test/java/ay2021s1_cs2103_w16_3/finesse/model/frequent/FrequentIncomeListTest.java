package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_WORK;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INVESTING;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PART_TIME;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFrequentIncome;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.DuplicateFrequentTransactionException;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.FrequentTransactionNotFoundException;
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class FrequentIncomeListTest {

    private final FrequentIncomeList frequentIncomeList = new FrequentIncomeList();

    @Test
    public void add_nullFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> frequentIncomeList.add(null));
    }

    @Test
    public void add_duplicateFrequentIncome_throwsDuplicateFrequentTransactionException() {
        FrequentIncome frequentIncome = new FrequentTransactionBuilder(PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildFrequentIncome();

        frequentIncomeList.add(frequentIncome);

        FrequentIncome frequentIncomeCopy = new FrequentTransactionBuilder(frequentIncome).buildFrequentIncome();

        assertThrows(DuplicateFrequentTransactionException.class, () -> frequentIncomeList.add(frequentIncomeCopy));
    }

    @Test
    public void setFrequentIncome_nullTargetFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> frequentIncomeList.setFrequentIncome(null, PART_TIME));
    }

    @Test
    public void setFrequentIncome_nullEditedTFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> frequentIncomeList.setFrequentIncome(PART_TIME, null));
    }

    @Test
    public void setFrequentIncome_targetFrequentIncomeNotInList_throwsTransactionNotFoundException() {
        assertThrows(FrequentTransactionNotFoundException.class, ()
            -> frequentIncomeList.setFrequentIncome(PART_TIME, PART_TIME));
    }

    @Test
    public void setFrequentIncome_editedFrequentIncomeIsSameFrequentIncome_success() {
        frequentIncomeList.add(PART_TIME);
        frequentIncomeList.setFrequentIncome(PART_TIME, PART_TIME);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        expectedFrequentIncomeList.add(PART_TIME);
        assertEquals(expectedFrequentIncomeList, frequentIncomeList);
    }

    @Test
    public void setFrequentIncome_editedFrequentIncomeHasSameIdentity_success() {
        frequentIncomeList.add(PART_TIME);
        FrequentIncome editedPartTime = new FrequentTransactionBuilder(PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildFrequentIncome();
        frequentIncomeList.setFrequentIncome(PART_TIME, editedPartTime);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        expectedFrequentIncomeList.add(editedPartTime);
        assertEquals(expectedFrequentIncomeList, frequentIncomeList);
    }

    @Test
    public void setFrequentIncome_editedFrequentIncomeHasDifferentIdentity_success() {
        frequentIncomeList.add(PART_TIME);
        frequentIncomeList.setFrequentIncome(PART_TIME, INVESTING);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        expectedFrequentIncomeList.add(INVESTING);
        assertEquals(expectedFrequentIncomeList, frequentIncomeList);
    }

    @Test
    public void remove_nullFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> frequentIncomeList.remove(null));
    }

    @Test
    public void remove_frequentIncomeDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(FrequentTransactionNotFoundException.class, () -> frequentIncomeList.remove(PART_TIME));
    }

    @Test
    public void remove_existingFrequentIncome_removesFrequentIncome() {
        frequentIncomeList.add(PART_TIME);
        frequentIncomeList.remove(PART_TIME);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        assertEquals(expectedFrequentIncomeList, frequentIncomeList);
    }

    @Test
    public void setFrequentIncomes_nullFrequentIncomeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> frequentIncomeList.setFrequentIncomes((FrequentIncomeList) null));
    }

    @Test
    public void setFrequentIncomes_frequentIncomeList_replacesOwnListWithProvidedFrequentIncomeList() {
        frequentIncomeList.add(PART_TIME);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        expectedFrequentIncomeList.add(INVESTING);
        frequentIncomeList.setFrequentIncomes(expectedFrequentIncomeList);
        assertEquals(expectedFrequentIncomeList, frequentIncomeList);
    }

    @Test
    public void setFrequentIncomes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> frequentIncomeList.setFrequentIncomes((List<FrequentIncome>) null));
    }

    @Test
    public void setFrequentIncomes_list_replacesOwnListWithProvidedList() {
        frequentIncomeList.add(PART_TIME);
        List<FrequentIncome> transactionList = Collections.singletonList(INVESTING);
        this.frequentIncomeList.setFrequentIncomes(transactionList);
        FrequentIncomeList expectedFrequentIncomeList = new FrequentIncomeList();
        expectedFrequentIncomeList.add(INVESTING);
        assertEquals(expectedFrequentIncomeList, this.frequentIncomeList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> frequentIncomeList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_distinctFrequentIncomeListsWithSameAttributes_returnsTrue() {
        List<FrequentIncome> frequentIncomes = getTypicalFrequentIncome();
        FrequentIncomeList firstTransactionList = new FrequentIncomeList();
        FrequentIncomeList secondTransactionList = new FrequentIncomeList();

        firstTransactionList.setFrequentIncomes(frequentIncomes);
        secondTransactionList.setFrequentIncomes(frequentIncomes);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList, secondTransactionList);
    }

    @Test
    public void hashCode_distinctFrequentIncomeListsWithSameAttributes_returnsTrue() {
        List<FrequentIncome> frequentIncomes = getTypicalFrequentIncome();
        FrequentIncomeList firstTransactionList = new FrequentIncomeList();
        FrequentIncomeList secondTransactionList = new FrequentIncomeList();

        firstTransactionList.setFrequentIncomes(frequentIncomes);
        secondTransactionList.setFrequentIncomes(frequentIncomes);

        assertNotSame(firstTransactionList, secondTransactionList);
        assertEquals(firstTransactionList.hashCode(), secondTransactionList.hashCode());
    }

}
