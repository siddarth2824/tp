package ay2021s1_cs2103_w16_3.finesse.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {
    private static final Logger logger = LogsCenter.getLogger(SampleDataUtil.class);

    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Expense(new Title("Bubble Tea"), new Amount("4.80"), new Date("14/10/2020"),
                getCategoriesSet("Food & Beverage")),
            new Expense(new Title("Tuition Fees"), new Amount("4221"), new Date("22/09/2020"),
                getCategoriesSet("NUS", "GIRO")),
            new Expense(new Title("Artificial Intelligence: A Modern Approach"), new Amount("139"),
                new Date("15/08/2020"), getCategoriesSet("Textbook")),
            new Income(new Title("Internship"), new Amount("560"), new Date("04/08/2020"),
                getCategoriesSet("Work")),
            new Income(new Title("Teaching Assistant"), new Amount("1920"), new Date("18/10/2020"),
                getCategoriesSet("CS1101S", "CS2103T")),
            new Income(new Title("Start-up Funding"), new Amount("10000"), new Date("01/10/2020"),
                getCategoriesSet("NUS Enterprise"))
        };
    }

    public static FrequentExpense[] getFrequentExpenses() {
        return new FrequentExpense[] {
            new FrequentExpense(new Title("Phone Bill"), new Amount("60"),
                    getCategoriesSet("Personal", "Utilities")),
            new FrequentExpense(new Title("Spotify Subscription"), new Amount("9.90"),
                    getCategoriesSet("Others"))
        };
    }

    public static FrequentIncome[] getFrequentIncome() {
        return new FrequentIncome[] {
            new FrequentIncome(new Title("Internship"), new Amount("1000"),
                    getCategoriesSet("Work", "Stipend")),
            new FrequentIncome(new Title("Teaching Assistant"), new Amount("1890"),
                    getCategoriesSet("CS1101S", "CS1231S"))
        };
    }

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        logger.info("Populating Fine$$e with sample data...");
        FinanceTracker sampleFt = new FinanceTracker();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleFt.addTransaction(sampleTransaction);
        }
        for (FrequentExpense frequentExpense : getFrequentExpenses()) {
            sampleFt.addFrequentExpense(frequentExpense);
        }
        for (FrequentIncome frequentIncome : getFrequentIncome()) {
            sampleFt.addFrequentIncome(frequentIncome);
        }
        return sampleFt;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategoriesSet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
