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
import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class FrequentIncomeListTest {

    private final FrequentIncomeList frequentIncomeList = new FrequentIncomeList();

    @Test
    public void add_nullFrequentIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> frequentIncomeList.add(null));
    }

    @Test
    public void add_duplicateFrequentIncome_throwsDuplicateFrequentExpenseException() {
        FrequentIncome frequentIncome = new FrequentTransactionBuilder(PART_TIME)
                .withCategories(VALID_CATEGORY_WORK).buildFrequentIncome();

        frequentIncomeList.add(frequentIncome);

        FrequentIncome frequentIncomeCopy = new FrequentTransactionBuilder(frequentIncome).buildFrequentIncome();

        assertThrows(DuplicateFrequentTransactionException.class, () -> frequentIncomeList.add(frequentIncomeCopy));
    }

    @Test
    public void setFrequentIncome_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> frequentIncomeList.setFrequentIncomes((List<FrequentIncome>) null));
    }

    @Test
    public void setFrequentIncomes_list_replacesOwnListWithProvidedList() {
        frequentIncomeList.add(PART_TIME);
        List<FrequentIncome> transactionList = Collections.singletonList(INVESTING);
        this.frequentIncomeList.setFrequentIncomes(transactionList);
        FrequentIncomeList expectedTransactionList = new FrequentIncomeList();
        expectedTransactionList.add(INVESTING);
        assertEquals(expectedTransactionList, this.frequentIncomeList);
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
