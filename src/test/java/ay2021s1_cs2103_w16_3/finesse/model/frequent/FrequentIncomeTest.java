package ay2021s1_cs2103_w16_3.finesse.model.frequent;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_AMOUNT_PHONE_BILL;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_BEVERAGE;
import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.VALID_TITLE_SPOTIFY_SUBSCRIPTION;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.INVESTING;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.PART_TIME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.testutil.FrequentTransactionBuilder;

public class FrequentIncomeTest {

    @Test
    public void equals() {
        // same values -> returns true
        FrequentIncome frequentIncome = new FrequentTransactionBuilder(PART_TIME).buildFrequentIncome();
        assertTrue(PART_TIME.equals(frequentIncome));

        // same object -> returns true
        assertTrue(PART_TIME.equals(PART_TIME));

        // null -> returns false
        assertFalse(PART_TIME.equals(null));

        // different type -> returns false
        assertFalse(PART_TIME.equals(5));

        // different frequent expense -> returns false
        assertFalse(PART_TIME.equals(INVESTING));

        // different title -> returns false
        FrequentIncome editedPartTime = new FrequentTransactionBuilder(PART_TIME)
                .withTitle(VALID_TITLE_SPOTIFY_SUBSCRIPTION).buildFrequentIncome();
        assertFalse(PART_TIME.equals(editedPartTime));

        // different amounts -> returns false
        editedPartTime = new FrequentTransactionBuilder(PART_TIME).withAmount(VALID_AMOUNT_PHONE_BILL)
                .buildFrequentIncome();
        assertFalse(PART_TIME.equals(editedPartTime));

        // different categories -> returns false
        editedPartTime = new FrequentTransactionBuilder(PART_TIME).withCategories(VALID_CATEGORY_FOOD_BEVERAGE)
                .buildFrequentIncome();
        assertFalse(PART_TIME.equals(editedPartTime));
    }
}
