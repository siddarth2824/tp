package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.DuplicateFrequentTransactionException;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.FrequentTransactionNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of frequent incomes that does not allow nulls.
 * The removal of a frequent income uses FrequentIncome#equals(Object) so as to ensure that the frequent income
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class FrequentIncomeList implements Iterable<FrequentIncome> {
    private final ObservableList<FrequentIncome> internalFrequentIncomeList = FXCollections.observableArrayList();
    private final ObservableList<FrequentIncome> internalUnmodifiableFrequentIncomeList =
            FXCollections.unmodifiableObservableList(internalFrequentIncomeList);

    /**
     * Adds a frequent income to the list.
     */
    public void add(FrequentIncome toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFrequentTransactionException("income");
        }
        internalFrequentIncomeList.add(toAdd);
    }

    /**
     * Returns true if the frequent income list contains an equivalent frequent income as the given argument.
     */
    public boolean contains(FrequentIncome toCheck) {
        requireNonNull(toCheck);
        return internalFrequentIncomeList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Replaces the frequent income {@code target} in the list with {@code editedFrequentIncome}.
     * {@code target} must exist in the list.
     * The frequent income identity of {@code editedFrequentIncome} must not be the same as another existing
     * frequent income in the list.
     */
    public void setFrequentIncome(FrequentIncome target, FrequentIncome editedFrequentIncome) {
        requireAllNonNull(target, editedFrequentIncome);

        int index = internalFrequentIncomeList.indexOf(target);
        if (index == -1) {
            throw new FrequentTransactionNotFoundException();
        }

        internalFrequentIncomeList.set(index, editedFrequentIncome);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FrequentIncome> asUnmodifiableObservableList() {
        return internalUnmodifiableFrequentIncomeList;
    }

    /**
     * Removes the equivalent frequent income from the list.
     * The frequent income must exist in the list.
     */
    public void remove(FrequentIncome toRemove) {
        requireNonNull(toRemove);
        if (!(internalFrequentIncomeList.remove(toRemove))) {
            throw new FrequentTransactionNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code frequentIncomes}.
     */
    public void setFrequentIncomes(List<FrequentIncome> frequentIncomes) {
        requireAllNonNull(frequentIncomes);

        internalFrequentIncomeList.setAll(frequentIncomes);
    }

    public void setFrequentIncomes(FrequentIncomeList replacement) {
        requireNonNull(replacement);
        internalFrequentIncomeList.setAll(replacement.internalFrequentIncomeList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FrequentIncomeList)
                && internalFrequentIncomeList.equals(((FrequentIncomeList) other).internalFrequentIncomeList);
    }

    @Override
    public int hashCode() {
        return internalFrequentIncomeList.hashCode();
    }

    @Override
    public Iterator<FrequentIncome> iterator() {
        return internalFrequentIncomeList.iterator();
    }

}
