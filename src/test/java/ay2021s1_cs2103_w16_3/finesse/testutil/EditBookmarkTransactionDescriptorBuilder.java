package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A utility class to help with building EditBookmarkTransactionDescriptor objects.
 */
public class EditBookmarkTransactionDescriptorBuilder {

    private EditBookmarkTransactionDescriptor descriptor;

    public EditBookmarkTransactionDescriptorBuilder() {
        descriptor = new EditBookmarkTransactionDescriptor();
    }

    public EditBookmarkTransactionDescriptorBuilder(EditBookmarkTransactionDescriptor descriptor) {
        this.descriptor = new EditBookmarkTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookmarkTransactionDescriptorBuilder} with fields containing
     * {@code bookmarkTransaction}'s details
     */
    public <T extends Transaction> EditBookmarkTransactionDescriptorBuilder(BookmarkTransaction<T>
                                                                                    bookmarkTransaction) {
        descriptor = new EditBookmarkTransactionDescriptor();
        descriptor.setTitle(bookmarkTransaction.getTitle());
        descriptor.setAmount(bookmarkTransaction.getAmount());
        descriptor.setCategories(bookmarkTransaction.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code EditBookmarkTransactionDescriptorBuilder} that we are building.
     */
    public EditBookmarkTransactionDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditBookmarkTransactionDescriptorBuilder} that we are building.
     */
    public EditBookmarkTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the
     * {@code EditBookmarkTransactionDescriptorBuilder} that we are building.
     */
    public EditBookmarkTransactionDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditBookmarkTransactionDescriptor build() {
        return descriptor;
    }

}
