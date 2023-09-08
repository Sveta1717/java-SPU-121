package step.learning.db;

import com.google.inject.Inject;
//import com.google.inject.name.Named;
import step.learning.db.dao.RandomDao;
import step.learning.db.dto.RandomRecord;
import step.learning.services.random.RandomService;

import java.util.UUID;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.UUID;

public class DbApp {
    //private final Connection connection;
    private final RandomService randomService;
    private final RandomDao randomDao ;

    @Inject
    public DbApp(
            //@Named( "Local" ) Connection connection,
            RandomService randomService,
            RandomDao randomDao
    ) {
        //this.connection = connection;
        this.randomService = randomService;
        this.randomDao = randomDao;
    }

    public void demo() {
//        if(connection == null) {
//            System.err.println( "Connection NULL" );
//            return;
//        }
//        System.out.println( "Connection OK" );
//
//        String sql ;

        // region Створення табллиці randoms - перенесено у DAO
        try {
            randomDao.ensureCreated();
            System.out.println( "CREATE OK" ) ;

            // Додаємо до БД випадкове число, дробове число, рядок
            randomDao.insertRandom();
            System.out.println( "INSERT OK");

            // Виведення даних
            for( RandomRecord rec : randomDao.getAll() ) {
                System.out.println( rec );
            }
            // Додавання нового запису в базу даних
            RandomRecord newRecord = new RandomRecord();
            newRecord.setId(UUID.randomUUID());
            newRecord.setRandInt(17);
            newRecord.setRandFloat(3.141592);
            newRecord.setRandStr("SPU_121");
            randomDao.insert(newRecord);
            System.out.println( "INSERT NEW OK" ) ;

            // Видалення запису з бази даних
            RandomRecord toDeleterecord = randomDao.getAll().get(5);
            randomDao.delete(toDeleterecord);
            System.out.println( "DELETE OK");

            // Оновлення запису в базі даних
            RandomRecord toUpdaterecord = randomDao.getAll().get(1);
            toUpdaterecord.setRandInt(15);
            toUpdaterecord.setRandFloat(randomService.getDouble());
            toUpdaterecord.setRandStr(randomService.getString());
            randomDao.update(toUpdaterecord);
            System.out.println( "UPDATE OK");

            // Перевірка наявності запису в базі даних
            RandomRecord checkrecord = randomDao.getAll().get(3);
            boolean exists = randomDao.check(checkrecord);
            if (exists) {
                System.out.println("Record exists!");
            }
            else
            {
                System.out.println("Entry does not exist");
            }
        }
        catch( RuntimeException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        // endregion

        System.out.println("----------------------------");
    }
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
