package RequestReply.UserRoleTitle;

import java.util.*;

public final class UserRoleTitleStringConverter
{
    public UserRoleTitle stringToRole(String type)
    {
        return UserRoleTitle.valueOf(type);
    }

    public String roleToString(UserRoleTitle type)
    {
        return type.name();
    }
}


