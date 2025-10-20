package UserRoleTitle;

import java.util.*;

public final class UserRoleTitleStringConverter
{
    private static final Map<String, CommunicationType> stringToEnum = new HashMap<>();
    private static final Map<CommunicationType, String> enumToString = new HashMap<>();
    
    static 
    {
        stringToEnum.put("CONFIGURATOR", UserRoleTitle.CONFIGURATOR);
        stringToEnum.put("USER", UserRoleTitle.USER);
        stringToEnum.put("VOLUNTARY", UserRoleTitle.VOLUNTARY);

        enumToString.put(UserRoleTitle.CONFIGURATOR, "CONFIGURATOR");
        enumToString.put(UserRoleTitle.USER, "USER");
        enumToString.put(UserRoleTitle.VOLUNTARY, "VOLUNTARY");
    }

    public CommunicationType stringToRole(String type)
    {
        return stringToEnum.get(type);
    }

    public String roleToString(CommunicationType type)
    {
        return enumToString.get(type);
    }
}


