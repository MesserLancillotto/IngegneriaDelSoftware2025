package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class NewUserEngine extends Engine
{
        private String userID;
        private String tmpPassword;
        private String userName;
        private String newPassword;
        private String cityOfResidence;
        private Integer birthYear;
        private UserRoleTitle role;

        public NewUserEngine(
                String userID,
                String tmpPassword,
                String userName,
                String newPassword,
                String cityOfResidence,
                int birthYear,
                UserRoleTitle role
        ) {
                this.userID = userID;
                this.tmpPassword = tmpPassword;
                this.userName = userName;
                this.newPassword = newPassword;
                this.cityOfResidence = cityOfResidence;
                this.birthYear = birthYear;
                this.role = role;
        }

        public String handleRequest()
        {
                try
                (
                        Connection connection = connectDB(dbUrl, "sa", "");
                ) {

                        String query = "SELECT role FROM users WHERE userID = ? AND userPassword = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, userID);
                        statement.setString(2, tmpPassword);

                        ResultSet resultSet = statement.executeQuery();
                        String role = "";
                        if (resultSet.next()) {
                            role = resultSet.getString("role");    
                        } else {
                            throw new Exception("Exception: user not found");
                        }
                        if
                        (
                            UserRoleTitleStringConverter.stringToComunicationType(role)
                            != UserRoleTitle.TEMPORARY
                        ) {
                            throw new Exception("Exception: user not temporary");
                        } 

                        String deleteTmpUser = "DELETE FROM users WHERE userID = ? AND userPassword = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, userID);
                        statement.setString(2, tmpPassword);
                        String userID = String.format("%s.%s.%d", 
                                UserRoleTitleStringConverter.roleToString(role),
                                userName.replaceAll(" ", "."),
                                birthYear % 100);
                                String query = "INSERT INTO users VALUES ('%s', '%s', %d, '%s', '%s', '%s')";
                                query = String.format(
                                    query,
                                    userName,
                                    cityOfResidence,  
                                    birthYear,
                                    userID,
                                    newPassword,
                                    role
                                );
                        Statement statement = connection.createStatement();
                        if(statement.executeUpdate(query) == 1)
                        {
                                return new NewUserReply(true, userID).toJSONString();
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new NewUserReply(true, "").toJSONString();
        }
    }