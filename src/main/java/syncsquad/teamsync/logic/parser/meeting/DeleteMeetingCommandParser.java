package syncsquad.teamsync.logic.parser.meeting;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.meeting.DeleteMeetingCommand;
import syncsquad.teamsync.logic.parser.Parser;
import syncsquad.teamsync.logic.parser.ParserUtil;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetingCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetingCommand
     * and returns a DeleteMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMeetingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE), pe);
        }
    }

}
