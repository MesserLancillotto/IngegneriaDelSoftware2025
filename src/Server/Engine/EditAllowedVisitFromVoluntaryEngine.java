package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class EditAllowedVisitFromVoluntaryEngine extends Engine
{
    private String userID;
    private String password;
    private String targetID;
    private ArrayList<String> append;
    private ArrayList<String> remove;
    
    private int removedVisits;
    private int appendedVisits;

    public EditAllowedVisitFromVoluntaryEngine(
        String userID, 
        String password,
        String targetID,
        ArrayList<String> append,
        ArrayList<String> remove
    ) {
        this.userID = userID;
        this.password = password;
        this.targetID = targetID;
        this.append = append; // // Inizializza append
        this.remove = remove; // // Inizializza remove
        this.removedVisits = 0;
        this.appendedVisits = 0;
    }

    public String handleRequest()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            String loginQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery(); 
            
            if(!userResult.next()) {
                return new EditAllowedVisitFromVoluntaryReply(false, this.removedVisits, this.appendedVisits).toJSONString(); // //
            }
            
            if(!userResult.getString("role").equals("CONFIGURATOR")) {
                return new EditAllowedVisitFromVoluntaryReply(true, this.removedVisits, this.appendedVisits).toJSONString(); // //
            }
            String userOrganization = userResult.getString("organization");

            // Verifica che targetID appartenga alla stessa organization
            String checkTargetQuery = "SELECT 1 FROM users WHERE userID = ? AND organization = ?";
            PreparedStatement checkTargetStmt = connection.prepareStatement(checkTargetQuery);
            checkTargetStmt.setString(1, targetID);
            checkTargetStmt.setString(2, userOrganization);
            if (!checkTargetStmt.executeQuery().next()) {
                return new EditAllowedVisitFromVoluntaryReply(true, 0, 0).toJSONString();
            }

            // Rimuovi visitType
            for(String visitType : remove) {
                String query = """
                    DELETE FROM allowedVisits 
                    WHERE userID = ? 
                    AND visitType = ?
                    AND EXISTS (
                        SELECT 1 FROM users 
                        WHERE users.userID = allowedVisits.userID 
                        AND users.organization = ?
                    )
                    """; // // Query corretta per DELETE
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, targetID);
                statement.setString(2, visitType);
                statement.setString(3, userOrganization);
                removedVisits += statement.executeUpdate(); 
            }

            // Aggiungi visitType
            for(String visitType : append) {
                String query = """
                    INSERT INTO allowedVisits (userID, visitType)
                    SELECT ?, ?
                    FROM users u1, users u2
                    WHERE u1.userID = ?
                    AND u2.userID = ?
                    AND u1.organization = u2.organization
                    AND u1.organization = ?
                    AND NOT EXISTS (
                        SELECT 1 FROM allowedVisits av 
                        WHERE av.userID = ? AND av.visitType = ?
                    )
                    """; // // Aggiunto controllo organization

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, targetID); 
                statement.setString(2, visitType);  
                statement.setString(3, userID);  
                statement.setString(4, targetID);
                statement.setString(5, userOrganization); // // Aggiunto organization
                statement.setString(6, targetID); 
                statement.setString(7, visitType);

                appendedVisits += statement.executeUpdate(); 
            }
            
            return new EditAllowedVisitFromVoluntaryReply(true, removedVisits, appendedVisits).toJSONString(); // // removedVisits, non rowsDeleted
            
        } catch(Exception e) {
            e.printStackTrace();
            return new EditAllowedVisitFromVoluntaryReply(false, removedVisits, appendedVisits).toJSONString(); // //
        }
    }
}