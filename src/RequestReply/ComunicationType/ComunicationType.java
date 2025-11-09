package RequestReply.ComunicationType;

public enum ComunicationType
{
    // reply request engine client
    EDIT_EVENT, // x x x x
    EDIT_PASSWORD, // x x x x
    GET_EVENT, // x x x x
    GET_EVENT_WITH_DISPONIBILITY, // x x x x
    GET_USER_DATA, // x x x x
    GET_VOLUNTARIES_FOR_VISIT, // x x x x
    GET_VOLUNTARIES, // x x x x
    SET_CLOSED_DAYS, // x x x x
    SET_DISPONIBILITY, // x x
    SET_NEW_EVENT, // x x x x
    SET_NEW_ORGANIZATION, // x x x x
    SET_NEW_USER,  // x x x x

    DELETE_PLACE, // x x x x
    EDIT_ALLOWED_VISIT_FROM_VOLUNTARY, // x x x x
    SET_VOLUNTARIES_DISPONIBILITY_COLLECT, // x x x x // Edit event
    SET_USERS_SUBSCRIPTION_COLLECT, // x x x x // Edit event
    SET_VOLUNTARY_TO_EVENT, // x x x x

    GET_VOLUNTARY_DISPONIBILITY // x x x x

}
// DAEMON_ENGINE
