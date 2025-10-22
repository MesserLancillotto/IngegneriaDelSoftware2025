package RequestReply.UserRoleTitle;

import java.util.*;

public final class UserRoleTitleStringConverter
{
    public static UserRoleTitle stringToRole(String type)
    {
        return UserRoleTitle.valueOf(type);
    }

    public static String roleToString(UserRoleTitle type)
    {
        return type.name();
    }
}


