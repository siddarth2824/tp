package ay2021s1_cs2103_w16_3.finesse.ui.frequent;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class FrequentIncomePanel extends UiPart<Region> {
    private static final String FXML = "FrequentTransactionPanel.fxml";

    @FXML
    private ListView<FrequentExpense> frequentTransactionList;

    /**
     * Creates a {@code FrequentIncomePanel} with the given {@code ObservableList}.
     */
    public FrequentIncomePanel(ObservableList<FrequentExpense> frequentIncomesList) {
        super(FXML);
        frequentTransactionList.setItems(frequentIncomesList);
        frequentTransactionList.setCellFactory(listView -> new FrequentIncomeListViewCell());
    }

    class FrequentIncomeListViewCell extends ListCell<FrequentExpense> {
        @Override
        protected void updateItem(FrequentExpense frequentExpense, boolean empty) {
            super.updateItem(frequentExpense, empty);

            if (empty || frequentExpense == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                FrequentTransactionCard<Expense> frequentTransactionCard =
                        new FrequentTransactionCard<>(frequentExpense, getIndex() + 1, 12);
                setGraphic((Node) frequentTransactionCard.getRoot());
            }
        }
    }
}
