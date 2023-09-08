package step.learning.db.dao;

/* DAO - Data Access Object - Об'єкт доступу до даних -
сукупність методів, які працюють з БД та DTO
 */

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import step.learning.db.dto.RandomRecord;
import step.learning.services.random.RandomService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class RandomDao
{
    private  final Connection connection; // інжектуємо підключення
    private final Logger logger;  // Guice автоматично реєструє логер
    private final RandomService randomService;

    @Inject

    public RandomDao(@Named("Local") Connection connection, Logger logger, RandomService randomService) {
        this.connection = connection;
        this.logger = logger;
        this.randomService = randomService;
    }

    public void ensureCreated() {
        String sql = "CREATE TABLE IF NOT EXISTS randoms ( " +
                "id         CHAR(36) PRIMARY KEY," +
                "rand_int   INT," +
                "rand_float FLOAT," +
                "rand_str   TEXT" +
                " ) Engine = InnoDB, DEFAULT CHARSET = utf8" ;
        try( Statement statement = connection.createStatement() ) {  // ~ SqlCommand (ADO)
            statement.executeUpdate( sql ) ;
        }
        catch( SQLException ex ) {
            logger.log( Level.SEVERE, ex.getMessage() );
            throw  new RuntimeException( "CREATE fails. Details in logs.");
        }
    }

    public List<RandomRecord> getAll() {
        String sql = "SELECT * FROM randoms";
        try (Statement statement = connection.createStatement()){
            ResultSet res = statement.executeQuery( sql );
            /* JDBC                             DBMS
                sql --------------------------> execute ->
                res <-------------------------- iterator
                res.next() -------------------> iterator.next() -> (перший рядок)
                    (1)-id     <--------------- [id, int, float, str]
                    (2)-int ...
                res.getString(1) == id (першого рядку)

                res.next() -------------------> iterator.next() -> (другий рядок)
                    (1)-id     <--------------- [id, int, float, str]
                    (2)-int ... (заміна раніше одержаних даних)
                res.getString(1) == id (другого рядку)
             */
            List<RandomRecord> ret = new ArrayList<>();
            while( res.next() ) {
                ret.add( new RandomRecord( res ));
            }
            return  ret ;
        }
        catch (SQLException ex) {
            logger.log( Level.SEVERE, ex.getMessage() );
            throw  new RuntimeException( "GET fails. Details in logs.");
        }
    }

    public void insertRandom() {
        // Підготовлений (параметризований) запит - SQL окремо, дані окремо
        String sql = "INSERT INTO randoms (id, rand_int, rand_float, rand_str) VALUES (?, ?, ?, ?)";
        try( PreparedStatement preparedStatement = connection.prepareStatement ( sql ) ) {
            // заповнення даних - за номерами параметрів (плейсхолдерів - ?)
            preparedStatement.setString( 1, UUID.randomUUID().toString() );
            // на місце 1-го знака ? буде підставлено id як String
            // !! звертаємо увагу - у JDBC відлік починається з 1 !!!
            preparedStatement.setInt( 2,  randomService.getInt() ); // rand_int
            preparedStatement.setDouble( 3, randomService.getDouble() ); // rand_float
            preparedStatement.setString( 4, randomService.getString() ); // rand_str

            // після встановлення всіх параметрів запит виконується типовим способом
            preparedStatement.executeUpdate();
            // підготовлені запити можна виконувати декілька разів з різними параметрами -
            // один раз підготувати, а циклічно - встановлювати параметри та виконувати
        }
        catch ( SQLException ex ) {
            logger.log( Level.SEVERE, ex.getMessage() );
            throw  new RuntimeException( "INSERT fails. Details in logs.");
        }
    }

    public void insert( RandomRecord record ) {
        String sql = "INSERT INTO randoms (id, rand_int, rand_float, rand_str) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement( sql ) ) {
            preparedStatement.setString( 1, record.getId().toString() );
            preparedStatement.setInt( 2, record.getRandInt() );
            preparedStatement.setDouble( 3, record.getRandFloat() );
            preparedStatement.setString( 4, record.getRandStr() );

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            logger.log( Level.SEVERE, ex.getMessage() );
            throw new RuntimeException( "INSERT fails. Details in logs." );
        }
    }

    public void delete( RandomRecord  record) {
        String sql = "DELETE FROM randoms WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, record.getId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException("DELETE fails. Details in logs.");
        }
    }

    public void update(RandomRecord record) {
        String sql = "UPDATE randoms SET rand_int = ?, rand_float = ?, rand_str = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, record.getRandInt());
            preparedStatement.setDouble(2, record.getRandFloat());
            preparedStatement.setString(3, record.getRandStr());
            preparedStatement.setString(4, record.getId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException("UPDATE fails. Details in logs.");
        }
    }

    public boolean check(RandomRecord record) {
        String sql = "SELECT COUNT(*) FROM randoms WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, record.getId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }
}
