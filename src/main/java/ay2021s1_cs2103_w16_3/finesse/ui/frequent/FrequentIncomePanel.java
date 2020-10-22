package ay2021s1_cs2103_w16_3.finesse.ui.frequent;

import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class FrequentIncomePanel extends UiPart<Region> {
    private static final String FXML = "FrequentTransactionPanel.fxml";

    @FXML
    private ListView<FrequentIncome> frequentTransactionList;

    /**
     * Creates a {@code FrequentIncomePanel} with the given {@code ObservableList}.
     */
    public FrequentIncomePanel(ObservableList<FrequentIncome> frequentIncomesList) {
        super(FXML);
        frequentTransactionList.setItems(frequentIncomesList);
        frequentTransactionList.setCellFactory(listView -> new FrequentIncomeListViewCell());
    }

    class FrequentIncomeListViewCell extends ListCell<FrequentIncome> {
        @Override
        protected void updateItem(FrequentIncome frequentIncome, boolean empty) {
            super.updateItem(frequentIncome, empty);

            if (empty || frequentIncome == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                FrequentTransactionCard<Income> frequentTransactionCard =
                        new FrequentTransactionCard<>(frequentIncome, getIndex() + 1, 12);
                setGraphic(frequentTransactionCard.getRoot());
            }
        }
    }

}
