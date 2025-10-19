package CommunicationType;

import java.util.*;

public final class CommunicationTypeStringConverter
{
    private static final Map<String, CommunicationType> stringToEnum = new HashMap<>();
    private static final Map<CommunicationType, String> enumToString = new HashMap<>();
    
    static 
    {
        stringToEnum.put("NEW_USER", CommunicationType.NEW_USER);
        stringToEnum.put("LOGIN", CommunicationType.LOGIN);
        stringToEnum.put("PASSWORD_CHANGE", CommunicationType.PASSWORD_CHANGE);
        stringToEnum.put("NEW_ORGANIZATION", CommunicationType.NEW_ORGANIZATION);
        stringToEnum.put("GET_VOLUNTARIES_FOR_VISIT", CommunicationType.GET_VOLUNTARIES_FOR_VISIT);

        enumToString.put(CommunicationType.NEW_USER, "NEW_USER");
        enumToString.put(CommunicationType.LOGIN, "LOGIN");
        enumToString.put(CommunicationType.PASSWORD_CHANGE, "PASSWORD_CHANGE");
        enumToString.put(CommunicationType.NEW_ORGANIZATION, "NEW_ORGANIZATION");
        enumToString.put(CommunicationType.GET_VOLUNTARIES_FOR_VISIT, "GET_VOLUNTARIES_FOR_VISIT");
    }

    public CommunicationType stringToCommunicationType(String type)
    {
        return stringToEnum.get(type);
    }

    public String communicationTypeToString(CommunicationType type)
    {
        return enumToString.get(type);
    }
}


