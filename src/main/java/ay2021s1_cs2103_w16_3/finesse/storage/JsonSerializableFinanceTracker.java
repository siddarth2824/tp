package ay2021s1_cs2103_w16_3.finesse.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.IllegalValueException;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "fine$$e")
class JsonSerializableFinanceTracker {

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedBookmarkExpense> bookmarkExpenses = new ArrayList<>();
    private final List<JsonAdaptedBookmarkIncome> bookmarkIncomes = new ArrayList<>();
    private final JsonAdaptedMonthlyBudget monthlyBudget;

    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
            @JsonProperty("incomes") List<JsonAdaptedIncome> incomes,
            @JsonProperty("bookmarkExpenses") List<JsonAdaptedBookmarkExpense> bookmarkExpenses,
            @JsonProperty("bookmarkIncomes") List<JsonAdaptedBookmarkIncome> bookmarkIncomes,
            @JsonProperty("monthlyBudget") JsonAdaptedMonthlyBudget monthlyBudget) {
        this.expenses.addAll(expenses);
        this.incomes.addAll(incomes);
        this.bookmarkExpenses.addAll(bookmarkExpenses);
        this.bookmarkIncomes.addAll(bookmarkIncomes);
        this.monthlyBudget = monthlyBudget;
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(ReadOnlyFinanceTracker source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new)
                .collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new)
                .collect(Collectors.toList()));
        bookmarkExpenses.addAll(source.getBookmarkExpenseList().stream().map(JsonAdaptedBookmarkExpense::new)
                .collect(Collectors.toList()));
        bookmarkIncomes.addAll(source.getBookmarkIncomeList().stream().map(JsonAdaptedBookmarkIncome::new)
                .collect(Collectors.toList()));
        monthlyBudget = new JsonAdaptedMonthlyBudget(source.getMonthlyBudget());
    }

    /**
     * Converts this finance tracker into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            financeTracker.addTransaction(expense);
        }
        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            financeTracker.addTransaction(income);
        }
        for (JsonAdaptedBookmarkExpense jsonAdaptedBookmarkExpense : bookmarkExpenses) {
            BookmarkExpense bookmarkExpense = jsonAdaptedBookmarkExpense.toModelType();
            financeTracker.addBookmarkExpense(bookmarkExpense);
        }
        for (JsonAdaptedBookmarkIncome jsonAdaptedBookmarkIncome : bookmarkIncomes) {
            BookmarkIncome bookmarkIncome = jsonAdaptedBookmarkIncome.toModelType();
            financeTracker.addBookmarkIncome(bookmarkIncome);
        }
        MonthlyBudget budget = monthlyBudget.toModelType();
        financeTracker.setMonthlyBudget(budget);
        financeTracker.calculateBudgetInfo();
        return financeTracker;
    }

}
