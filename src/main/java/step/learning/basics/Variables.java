package step.learning.basics;

/**
 * Основи Java - змінні та типи данних
 */
public class Variables {
    /**
     * Запуск демонстрації
     */
    public void demo() {
        /* У Java всі типи даних рефересні (за посиланням), окрім примітивних
           типів:
         */
        byte  xb = 10;             // Цілочисельні типи даних
        short xs = 100;            // !! всі ці типи - знакові
        int   xi = 1000;           // беззнакових типів немає
        long  xl = 100000000;      //

        float  yf = 0.01f;
        double yd = 1.15e-3;

        char c = 'A';              // UTF-16 -- 2 байти
        char ii = 'Ї';

        boolean b = true;
        /* З примітивними типами можуть виникати обмеження при спробі
           їх використання у generic виразах (<T>). Для таких цілей
           є рефересні аналоги цих типів:
         */
        Byte    xxb = 10;
        Short   xxs = 100;
        Integer xxi = xxb + xxs;
        // і так далі

        /* String - аналогічно з C# - незмінний (immutable), всі операції
           утворюють нові об'єкти. І також аналогічно діє пул рядків (компілятор
           збирає усі рядки з коду, якщо трапляються однакові, то змінюється
           на всже наявне посилання, новій літерал не створюється)
         */
        String str1 = "Hello World";     // Завдяки пулу це буде один рядок (ресурс)
        String str2 = "Hello World";     // та два посилання на нього
        String str3 = "Hello" + "World"; // такий  "+" виконує компілятор і теж не створюють новий ресурс
        /* Перевантаження операторів у Java немає.
           Оператор порівняння (==) діє за рефересним принципом, тобто true для двох
           посилань на один об'єкт. Це ж стосується і String
         */
        if( str1 == str2 ) {
            System.out.println( "str == str2" );        // це виводиться - різні через пул-рядки
        }
        else {
            System.out.println( "str != str2" );
        }
        String str4 = new String("Hello World" );
        String str5 = new String("Hello World" );
        if( str4 == str5 ) {
            System.out.println( "str 4== str5" );
        }
        else {
            System.out.println( "str4 != str5" );     // це виводиться  - різні об'єкт (референси)
        }
        if( str4.equals( str5 ) ) {
            System.out.println( "str 4 equals str5" );
        }
        else {
            System.out.println( "str4 !equals str5" );     // це виводиться  -  об'єкти мають однаковий вміст
        }
        // Object - батьківський об'єкт для всіх типів
        Object obj = new Object();    // -> obj.toString()
        System.out.println( obj );
    }
}
/*
JavaDoc - система коментарів-документації (/** ...)
 */
