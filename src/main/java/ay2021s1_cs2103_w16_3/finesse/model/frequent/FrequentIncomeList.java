package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions.DuplicateFrequentTransactionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public class FrequentIncomeList implements Iterable<FrequentIncome> {
    private final ObservableList<FrequentIncome> internalFrequentIncomeList = FXCollections.observableArrayList();
    private final ObservableList<FrequentIncome> internalUnmodifiableFrequentIncomeList =
            FXCollections.unmodifiableObservableList(internalFrequentIncomeList);

    /**
     * Adds a frequent income to the list.
     */
    public void addFrequentIncome(FrequentIncome toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFrequentTransactionException();
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

    @Override
    public Iterator<FrequentIncome> iterator() {
        return this.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FrequentExpenseList)
                && internalFrequentIncomeList.equals(((FrequentIncomeList) other).internalFrequentIncomeList);
    }
}
