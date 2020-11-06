package ay2021s1_cs2103_w16_3.finesse.logic.commands.stubs;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_METHOD_SHOULD_NOT_BE_CALLED;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.Model;

/**
 * A {@code EditBookmarkCommand} stub that takes in an {@code Index} and returns the same
 * {@code Index} when its {@code getTargetIndex} method is called. All of its other
 * methods fail when called.
 */
public class EditBookmarkCommandStub extends EditBookmarkCommand {
    private final Index targetIndex;
    private final EditBookmarkTransactionDescriptor editBookmarkTransactionDescriptor;

    /**
     * Constructs an {@code EditCommandStub} object.
     *
     * @param targetIndex The target index in the displayed list.
     */
    public EditBookmarkCommandStub(Index targetIndex,
                                   EditBookmarkTransactionDescriptor editBookmarkTransactionDescriptor) {
        super(targetIndex, editBookmarkTransactionDescriptor);
        this.targetIndex = targetIndex;
        this.editBookmarkTransactionDescriptor = editBookmarkTransactionDescriptor;
    }

    @Override
    protected Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    protected EditBookmarkTransactionDescriptor getEditBookmarkTransactionDescriptor() {
        return editBookmarkTransactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }

    @Override
    public boolean equals(Object other) {
        throw new AssertionError(MESSAGE_METHOD_SHOULD_NOT_BE_CALLED);
    }
}
