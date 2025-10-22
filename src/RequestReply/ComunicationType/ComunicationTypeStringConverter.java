package ComunicationType;

import java.util.*;

public final class ComunicationTypeStringConverter
{
    public ComunicationType stringToComunicationType(String type)
    {
        return ComunicationType.valueOf(type);
    }

    public String ComunicationTypeToString(ComunicationType type)
    {
        return type.name();
    }
}
