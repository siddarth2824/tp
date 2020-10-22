package ay2021s1_cs2103_w16_3.finesse.model.frequent.exceptions;

public class DuplicateFrequentTransactionException extends RuntimeException {
    public DuplicateFrequentTransactionException(String message) {
        super("Operation would result in duplicate frequent " + message);
    }
}
