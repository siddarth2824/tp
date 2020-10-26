package ay2021s1_cs2103_w16_3.finesse.model.bookmark.exceptions;

/**
 * Signals that the following operation will result in a duplicate frequent transaction.
 */
public class DuplicateBookmarkTransactionException extends RuntimeException {
    public DuplicateBookmarkTransactionException(String message) {
        super("Operation would result in duplicate frequent " + message);
    }
}
