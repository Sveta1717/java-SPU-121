package step.learning.db;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import step.learning.services.random.RandomService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class DbApp {

    private final Connection connection;
    private final RandomService randomService;

    @Inject
    public DbApp(
            @Named("Local")Connection connection,
            RandomService randomService
    ) {
        this.connection = connection;
        this.randomService = randomService;
    }

    public void demo() {
        if(connection == null) {
            System.err.println( "Connection NULL" );
            return;
        }
        //try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //////Connection conn = DriverManager.getConnection(
            ////        "jdbc:mysql://aws.connect.psdb.cloud/asp?sslMode=VERIFY_IDENTITY",
            ////         "ejz8ct5f8dnasvey5k71",
            ////       "************");

            //connection = DriverManager.getConnection(
            // "jdbc:mysql://localhost:3306/java_spu121?useUnicode=true&characterEncoding=UTF-8",
            //       "spu121", "pass121");

            //} catch (Exception ex) {
            // System.err.println(ex.getMessage());
            // return;
            //}
        System.out.println( "Connection OK" );

        // region Створення табллиці randoms
        String sql = "CREATE TABLE IF NOT EXISTS randoms ( " +
                "id         CHAR(36) PRIMARY KEY," +
                "rand_int   INT," +
                "rand_float FLOAT," +
                "rand_str   TEXT" +
                " ) Engine = InnoDB, DEFAULT CHARSET = utf8" ;
        try( Statement statement = connection.createStatement() ) {  // ~ SqlCommand (ADO)
            statement.executeUpdate( sql ) ;
        }
        catch( Exception ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        System.out.println( "CREATE OK" ) ;
        // endregion

        // region Додаємо до БД випадкове число, дробове число, рядок
        // Підготовлений (параметризований) запит - SQL окремо, дані окремо
        sql = "INSERT INTO randoms (id, rand_int, rand_float, rand_str) VALUES (?, ?, ?, ?)";
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
        catch ( Exception ex ) {
            System.err.println( ex.getMessage() );
            return;
        }
        System.out.println( "INSERT OK");
        // endregion

        // region Виведення даних
        sql = "SELECT * FROM randoms";
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
            res.next();
            System.out.printf( "%s %d %f %s\n",
                   res.getString(1),
                   res.getInt(2),
                   res.getDouble(3),
                   res.getString(4));
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            return;
        }
        System.out.println("----------------------------");
        //endregion
    }

//    public void insertRandomData() {
//        int randomInt = random.nextInt();
//        double randomDouble = random.nextDouble();
//        String randomString = generateRandomString(10);
//
//       String sql = "INSERT INTO randoms (id, rand_int, rand_float, rand_str) VALUES " +
//                "('" + randomString + "', " + randomInt + ", " + randomDouble + ", '" + randomString + "')";
//
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(sql);
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
//
//    private String generateRandomString(int length) {
//        StringBuilder sb = new StringBuilder();
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(characters.length());
//            sb.append(characters.charAt(index));
//        }
//        return sb.toString();
//    }
}

/* JDBC - технологія доступу до даних, аналог ADO.NET
1. Встановлюємо коннектор - драйвер БД (https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)
2. БД та рядок підключення
    (на прикладі PlanetScale):
    jdbc:mysql://aws.connect.psdb.cloud/asp?sslMode=VERIFY_IDENTITY

    (на прикладі локальної БД):
        jdbc:mysql://localhost:3306/java_spu121?useUnicode=true&characterEncoding=UTF-8

    CREATE DATABASE java_spu121 ;
    GRANT ALL PRIVILEGES ON java_spu121.* TO 'spu121'@'localhost' IDENTIFIED BY 'pass121' ;


 */
