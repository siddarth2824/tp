package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A utility class to help with building EditFrequentTransactionDescriptor objects.
 */
public class EditFrequentTransactionDescriptorBuilder {

    private EditFrequentTransactionDescriptor descriptor;

    public EditFrequentTransactionDescriptorBuilder() {
        descriptor = new EditFrequentTransactionDescriptor();
    }

    public EditFrequentTransactionDescriptorBuilder(EditFrequentTransactionDescriptor descriptor) {
        this.descriptor = new EditFrequentTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFrequentTransactionDescriptorBuilder} with fields containing
     * {@code frequentTransaction}'s details
     */
    public <T extends Transaction> EditFrequentTransactionDescriptorBuilder(FrequentTransaction<T>
                                                                            frequentTransaction) {
        descriptor = new EditFrequentTransactionDescriptor();
        descriptor.setTitle(frequentTransaction.getTitle());
        descriptor.setAmount(frequentTransaction.getAmount());
        descriptor.setCategories(frequentTransaction.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code EditFrequentTransactionDescriptorBuilder} that we are building.
     */
    public EditFrequentTransactionDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditFrequentTransactionDescriptorBuilder} that we are building.
     */
    public EditFrequentTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the
     * {@code EditFrequentTransactionDescriptorBuilder} that we are building.
     */
    public EditFrequentTransactionDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditFrequentTransactionDescriptor build() {
        return descriptor;
    }

}
