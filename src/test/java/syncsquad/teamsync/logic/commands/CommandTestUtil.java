package syncsquad.teamsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_EMAIL;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_NAME;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_PHONE;
import static syncsquad.teamsync.logic.parser.CliSyntax.PREFIX_TAG;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.logic.commands.person.EditCommand;
import syncsquad.teamsync.model.AddressBook;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.person.NameContainsKeywordsPredicate;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_INDEX_CS2103T_MODULE = "1";
    public static final String VALID_MODULE_CODE_CS2103T_MODULE = "CS2103T";
    public static final String VALID_DAY_CS2103T_MODULE = "FRI";
    public static final String VALID_START_TIME_CS2103T_MODULE = "14:00";
    public static final String VALID_END_TIME_CS2103T_MODULE = "16:00";

    public static final String INDEX_DESC_CS2103T_MODULE = " " + VALID_INDEX_CS2103T_MODULE;
    public static final String MODULE_CODE_DESC_CS2103T_MODULE = " " + VALID_MODULE_CODE_CS2103T_MODULE;
    public static final String DAY_DESC_CS2103T_MODULE = " " + VALID_DAY_CS2103T_MODULE;
    public static final String START_TIME_DESC_CS2103T_MODULE = " " + VALID_START_TIME_CS2103T_MODULE;
    public static final String END_TIME_DESC_CS2103T_MODULE = " " + VALID_END_TIME_CS2103T_MODULE;

    public static final String INVALID_INDEX_DESC_CS2103T_MODULE = " " + "first";
    public static final String INVALID_MODULE_CODE_DESC_CS2103T_MODULE = " " + "notamodulecode";
    public static final String INVALID_SAME_START_END_TIME_DESC_CS2103T_MODULE = " " + VALID_START_TIME_CS2103T_MODULE;
    public static final String INVALID_START_TIME_DESC_CS2103T_MODULE = " " + VALID_END_TIME_CS2103T_MODULE;
    public static final String INVALID_END_TIME_DESC_CS2103T_MODULE = " " + VALID_START_TIME_CS2103T_MODULE;

    public static final String VALID_DATE_SEP_MEETING = "09-09-2025";
    public static final String VALID_START_TIME_SEP_MEETING = "16:00";
    public static final String VALID_END_TIME_SEP_MEETING = "19:23";

    public static final String DATE_DESC_SEP_MEETING = " " + VALID_DATE_SEP_MEETING;
    public static final String START_TIME_DESC_SEP_MEETING = " " + VALID_START_TIME_SEP_MEETING;
    public static final String END_TIME_DESC_SEP_MEETING = " " + VALID_END_TIME_SEP_MEETING;

    public static final String INVALID_SAME_START_END_TIME_DESC_SEP_MEETING = " " + VALID_START_TIME_SEP_MEETING;
    public static final String INVALID_START_TIME_DESC_SEP_MEETING = " " + VALID_END_TIME_SEP_MEETING;
    public static final String INVALID_END_TIME_DESC_SEP_MEETING = " " + VALID_END_TIME_SEP_MEETING;

    /* date must be in dd-mm-yyyy format; yyyy is optional */
    public static final String INVALID_DATE_DESC = " " + "tomorrow";
    public static final String INVALID_TIME_DESC = " " + "2pm"; // time must be in HH:mm format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in
     * {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given
     * {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
