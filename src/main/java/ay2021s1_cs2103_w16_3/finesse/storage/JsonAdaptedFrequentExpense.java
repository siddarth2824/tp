package ay2021s1_cs2103_w16_3.finesse.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.IllegalValueException;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * JSON-friendly version of {@link FrequentExpense}.
 */
public class JsonAdaptedFrequentExpense {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FrequentExpense's %s is missing!";

    private final String title;
    private final String amount;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFrequentExpense} with the given frequent expense details.
     */
    @JsonCreator
    public JsonAdaptedFrequentExpense(@JsonProperty("title") String title, @JsonProperty("amount") String amount,
            @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.title = title;
        this.amount = amount;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code FrequentExpense} into this class for Jackson use.
     */
    public JsonAdaptedFrequentExpense(FrequentExpense source) {
        title = source.getTitle().fullTitle;
        amount = source.getAmount().toString();
        categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted frequent expense object into the model's {@code FrequentExpense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted frequent expense.
     */
    public FrequentExpense toModelType() throws IllegalValueException {
        final List<Category> transactionCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : categories) {
            transactionCategories.add(category.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        final Set<Category> modelCategories = new HashSet<>(transactionCategories);
        return new FrequentExpense(modelTitle, modelAmount, modelCategories);
    }

}
