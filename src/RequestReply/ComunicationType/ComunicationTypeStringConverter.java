package RequestReply.ComunicationType;

import java.util.*;

public class ComunicationTypeStringConverter
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
