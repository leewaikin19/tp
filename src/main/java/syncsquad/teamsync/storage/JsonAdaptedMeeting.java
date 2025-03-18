package syncsquad.teamsync.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import syncsquad.teamsync.commons.exceptions.IllegalValueException;
import syncsquad.teamsync.logic.parser.ParserUtil;
import syncsquad.teamsync.model.schedule.Meeting;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String date;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("date") String date, @JsonProperty("start time") String startTime,
                             @JsonProperty("end time") String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        date = source.getDateString();
        startTime = source.getStartTimeString();
        endTime = source.getEndTimeString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Meeting toModelType() throws IllegalValueException {
        final LocalDate date = ParserUtil.parseDate(this.date);
        final LocalTime startTime = ParserUtil.parseTime(this.startTime);
        final LocalTime endTime = ParserUtil.parseTime(this.endTime);

        return new Meeting(date, startTime, endTime);
    }

}
