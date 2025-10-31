package Server.Engine;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public class Engine
{
    protected static String dbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/MAIN_DB";

    public static void main(String[] args) {
        // MEMENTO args[0] -> prima flag passata
        if(args[0].equals("--terraform"))
        {
            boolean x = terraform();
        }
        if(args[0].equals("--debug_data"))
        {
            boolean x = debug_data();
        }
    }

    private static boolean terraform()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            Statement statement = connection.createStatement();
            String query = """
                CREATE TABLE IF NOT EXISTS users (userName VARCHAR(32), cityOfResidence VARCHAR(32), birthYear INT, userID VARCHAR(64) UNIQUE, userPassword VARCHAR(64), role VARCHAR(16), organization VARCHAR(32));
                CREATE TABLE IF NOT EXISTS organizations (organizationName VARCHAR(32), territory VARCHAR(64));
                CREATE TABLE IF NOT EXISTS events (eventName VARCHAR(64), eventDescription VARCHAR(512), city VARCHAR(16), address VARCHAR(64), startDate INT, endDate INT, organizationName VARCHAR(32), minUsers INT, maxUsers INT,  maxFriends INT, visitType VARCHAR(32), confirmed BOOLEAN);
                CREATE TABLE IF NOT EXISTS eventsVoluntaries (eventName VARCHAR(64), userID VARCHAR(64));
                CREATE TABLE IF NOT EXISTS closedDays (startDay INT, endDay INT, organizationName VARCHAR(32));
            """;
            statement.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }
    private static boolean debug_data()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        )
        {
            Statement statement = connection.createStatement();
            String query = """
                INSERT INTO users VALUES ('Arlecchino Valcalepio', 'Bergamo', 1981, 'VOLUNTARY.Arlecchino.Valcalepio.81', 'michelas', 'VOLUNTARY', 'San Genesio');
                INSERT INTO users VALUES ('Balanzone Pignoletto', 'Bologna', 1983, 'VOLUNTARY.Balanzone.Pignoletto.83', 'pettola', 'VOLUNTARY', 'San Genesio');
                INSERT INTO users VALUES ('Pantalone Malvasia', 'Venezia', 1985, 'VOLUNTARY.Pantalone.Malvasia.85', 'schei', 'VOLUNTARY', 'San Genesio');
                INSERT INTO users VALUES ('Carlo Goldoni', 'Venezia', 1997, 'CONFIGURATOR.Carlo.Goldoni.97', 'samuele', 'CONFIGURATOR', 'San Genesio');
                INSERT INTO organizations VALUES ('San Genesio', 'Bergamo');
                INSERT INTO organizations VALUES ('San Genesio', 'Bologna');
                INSERT INTO organizations VALUES ('San Genesio', 'Venezia');
                INSERT INTO events VALUES ('Cinema in Castello: la corazzata Potiomkin', 'Terna di serate per ammirare i grandi capolavori cinematografici del Novecento, ore 21:00', 'Bergamo', 'Castello di Bergamo', 1770411600, 1770584400, 'San Genesio', 50, 500, 4, 'Cinema', true);
                INSERT INTO events VALUES ('Cinema in Castello: il deserto rosso', 'Terna di serate per ammirare i grandi capolavori cinematografici del Novecento, ore 21:00', 'Bologna', 'Castello di Bologna', 1761944400, 1762117200, 'San Genesio', 50, 500, 4, 'Cinema', true);
                INSERT INTO eventsVoluntaries VALUES ('Cinema in Castello: la corazzata Potiomkin', 'VOLUNTARY.Arlecchino.Valcalepio.99');
                INSERT INTO eventsVoluntaries VALUES ('Cinema in Castello: il deserto rosso', 'VOLUNTARY.Balanzone.Pignoletto.83');
                INSERT INTO closedDays VALUES (1766534340, 1767743940, 'San Genesio');
            """;
            statement.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }


}