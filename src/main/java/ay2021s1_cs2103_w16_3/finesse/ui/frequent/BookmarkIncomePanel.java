package ay2021s1_cs2103_w16_3.finesse.ui.frequent;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class BookmarkIncomePanel extends UiPart<Region> {
    private static final String FXML = "BookmarkTransactionPanel.fxml";

    @FXML
    private ListView<BookmarkIncome> frequentTransactionList;

    /**
     * Creates a {@code BookmarkIncomePanel} with the given {@code ObservableList}.
     */
    public BookmarkIncomePanel(ObservableList<BookmarkIncome> bookmarkIncomesList) {
        super(FXML);
        frequentTransactionList.setItems(bookmarkIncomesList);
        frequentTransactionList.setCellFactory(listView -> new FrequentIncomeListViewCell());
    }

    class FrequentIncomeListViewCell extends ListCell<BookmarkIncome> {
        @Override
        protected void updateItem(BookmarkIncome bookmarkIncome, boolean empty) {
            super.updateItem(bookmarkIncome, empty);

            if (empty || bookmarkIncome == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #2E2E36");
            } else {
                BookmarkTransactionCard<Income> bookmarkTransactionCard =
                        new BookmarkTransactionCard<>(bookmarkIncome, getIndex() + 1, 12);
                setGraphic(bookmarkTransactionCard.getRoot());
            }
        }
    }

}
