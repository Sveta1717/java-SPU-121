package step.learning.oop;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class library {
    private List<Literature> funds ;

    private void fillFunds() {
        funds = new ArrayList<>() ;
        Book book = new Book() ;
        book.setAuthor( "D. Knuth" ) ;
        book.setTitle( "Art of Programming" ) ;
        funds.add( book ) ;

        Newspaper newspaper = new Newspaper() ;
        newspaper.setTitle( "Daily Telegraph" ) ;
        try { newspaper.setDate( "2023-08-19" ) ; }
        catch (ParseException e) { System.err.println( "Invalid date"); }
        funds.add( newspaper ) ;

        Journal journal = new Journal() ;
        journal.setTitle( "ArgC & ArgV" ) ;
        journal.setNumber( 314 ) ;
        funds.add( journal ) ;

        Booklet booklet = new Booklet() ;
        Booklet booklet1 = new Booklet() ;
        booklet.setTitle( "Step" );
        booklet.setPrintery( "IT STEP Academy" );
        booklet1.setTitle( "Credit Agricole" );
        booklet1.setPrintery( "Wolf.UA" );
        funds.add( booklet ) ;
        funds.add( booklet1 ) ;

        Poster poster = new Poster() ;
        Poster poster1 = new Poster() ;
        poster.setTitle( "Tennis Club" ) ;
        poster.setColor(true);
        poster1.setTitle( "COOL PANDA " ) ;
        poster1.setColor(false);
        funds.add( poster ) ;
        funds.add( poster1 ) ;
    }
    /*
     Виводить тільки періодичні видання
     */
    private  void showPeriodic() {
        for (Literature literature : funds) {
            if (literature instanceof Periodic) {
                System.out.print(literature.getCard());
                System.out.println(
                        " Comes " + ((Periodic) literature).getPeriod()
                );
            }
        }
    }

    private  void showNonPeriodic() {
        for (Literature literature : funds) {
            if (literature instanceof Periodic) {
              System.lineSeparator();
            }
            else {
                System.out.println(literature.getCard());
            }
        }
    }

    private  void showCopyable() {
        for (Literature literature : funds) {
            if (literature instanceof Copyable) {
                System.out.println(literature.getCard());
            }
        }
    }

    private  void showNonCopyable() {
        for (Literature literature : funds) {
            if (literature instanceof Copyable) {
                System.lineSeparator();
            }
            else {
                System.out.println(literature.getCard());
            }

        }
    }

    public void demo() {
        System.out.println( "Library" ) ;
        fillFunds() ;
        for( Literature literature : funds ) {
            System.out.println( literature.getCard() ) ;
        }
        System.out.println("-------------PERIODIC----------");
        showPeriodic();
        System.out.println("-------------NON_PERIODIC----------");
        showNonPeriodic();
        System.out.println("-------------COPYABLE----------");
        showCopyable();
        System.out.println("-------------NON_COPYABLE----------");
        showNonCopyable();
    }
}

/*
Бібліотека: є фонди, які містять різну літературу - книги, газети, журнали тощо.
Є спілні риси - картка для каталога - вона існує для будь-якої літератури
Є відмінні риси - у книги є автор(и), у газет - дата виходу, у журналів - номер
--------------------
Предметна галузь: книги, газети, журнали
  створюємо об'єкти: Book{Author, Title}, Newspaper{Date, Title}, Magazine{Number, Title}
  абстрагування: виділення структурно-ієрархичних "вузлів", які призначені
  для групування (поліморфизму) та спрощення (відокремлення спільних даних)
                                  Literature{Title}
                                /        |         \
                      Book{Author}, Newspaper{Date}, Magazine{Number}
  спільна та індивідуальна функціональність
        потрібний засіб(метод, властивість) для формування картки (каталогу)
        Literature{Title, getCard()}
        на рівні класу Literature метод getCard() не має сенсу реалізуватиб тому
        він залишається абстракним. Значить
 */
/*
ООП -об'єктно-орієнтовна парадигма програмування
Для того щоб опанувати ООП у новій мові програмування слід розглянути:
- оголошення класів, різновиди (чи є інтерфейси, enum, тощо)
- конструктори та створення об'єктів
- спадкування та реалізація
- поліморфізм - сумісність типів даних
- перетворення типів
 */
/*
Іперативна -алгоритмічна - повне управління виконавцем (процесором)
   поняття: інструкція (Її номер/адреса, перехід до іншої інструкції)
   відгалуження: процедурний підхід, структурне програмування
   мови: машиний кодб асемблери, С/С++,Basic, Fortran,
Декларативна - встановлення певних правил, відношень, аксіом та перевірка
        на базі них різних тверджень
    поняття: домен, clause, бектрекінг
    відгалуження: логічна парадигма
    мови: Prolog
OOП - програма подається як об'єкти та їх взаємодія
    поняття: інкапсуляція, поліморфізм, наслідування(спадкування)
    відгалуження: прототипне програмування (JS)
    мови: C#, Java

Функціональна - послідовне виконання функції над вхідними даними до досягнення
        потрібної мети.
    поняття: лямбда (функція), котреж, голова-хвіст
    відгалуженя: чисті парадигми
    мови: LISP, Python, J, Haskell
 */
