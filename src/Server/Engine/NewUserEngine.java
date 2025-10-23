
package Server.Engine;

class NewUserEngine implements Engine
{
    protected static boolean insertUser(
                Connection conn, 
                String userID, 
                String userPassword, 
                String role
            ) throws SQLException {
        String sql = "INSERT INTO users (userID, userPassword, Role) VALUES (?, ?, ?)";
        try 
        (
            PreparedStatement statement = conn.prepareStatement(sql)
        ) {
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            statement.setString(3, role);
            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0);
        } catch(Exception e )
        {
            return false;
        }
    }

    void handleRequest()
    {
        Connection connection = connectDB(dbUrl, "sa", "");
    }
}
