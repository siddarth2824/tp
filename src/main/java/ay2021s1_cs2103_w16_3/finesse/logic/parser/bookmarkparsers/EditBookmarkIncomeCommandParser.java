package ay2021s1_cs2103_w16_3.finesse.logic.parser.bookmarkparsers;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark.EditBookmarkTransactionDescriptor;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentMultimap;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ArgumentTokenizer;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.Parser;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.ParserUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;

/**
 * Parses input arguments and creates a new EditBookmarkIncomeCommand object
 */
public class EditBookmarkIncomeCommandParser implements Parser<EditBookmarkIncomeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditBookmarkIncomeCommand
     * and returns an EditBookmarkIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookmarkIncomeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AMOUNT, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditBookmarkIncomeCommand.MESSAGE_USAGE));
        }

        EditBookmarkTransactionDescriptor editBookmarkIncomeDescriptor = new EditBookmarkTransactionDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editBookmarkIncomeDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editBookmarkIncomeDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY))
                .ifPresent(editBookmarkIncomeDescriptor::setCategories);

        if (!editBookmarkIncomeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBookmarkIncomeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookmarkIncomeCommand(index, editBookmarkIncomeDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("")
                ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
