package syncsquad.teamsync.logic.commands;

import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandFailure;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static syncsquad.teamsync.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.person.AddPersonCommand;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ModelManager;
import syncsquad.teamsync.model.UserPrefs;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPersonCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddPersonCommand(validPerson), model,
                String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddPersonCommand(personInList), model,
                AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
