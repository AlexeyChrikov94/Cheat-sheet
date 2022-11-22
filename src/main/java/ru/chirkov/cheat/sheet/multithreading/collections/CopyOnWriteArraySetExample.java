package ru.chirkov.cheat.sheet.multithreading.collections;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * В примере формируется набор данных data, на основании которого создается потокобезопасный набор cowSet типа CopyOnWriteArraySet.
 * В качестве данных используется внутренний класс User.
 * Данные коллекции cowSet с помощью итератора выводятся в консоль два раза.
 * В первом цикле в коллекцию вносятся изменения : изменяется имя одного объекта и добавляется другой.
 * Во втором цикле данные выводятся без изменений.
 *
 * В первом цикле в консоль выдены исходные данные несмотря на внесение изменений в набор.
 * Во втором цикле — измененный набор данных.
 * Пример подтверждает, что итератор набора данных CopyOnWriteArraySet не вызвал исключения
 * ConcurrentModificationException при одновременном переборе и изменении значений.
 */
public class CopyOnWriteArraySetExample {

    public static void main(String args[]) {

        List<User> data = new ArrayList<>();
        data.add(new User ("Прохор "));
        data.add(new User ("Георгий"));
        data.add(new User ("Михаил" ));

        CopyOnWriteArraySet<User> cowSet = new CopyOnWriteArraySet<>(data);

        System.out.println("Цикл с измением");

        Iterator<User> itr = cowSet.iterator();
        int cnt = 0;
        while (itr.hasNext()) {
            User user = itr.next();
            System.out.println("  " + user.name);
            if (++cnt == 2) {
                cowSet.add(new User("Павел"));
                user.name += " Иванович";
            }
        }

        System.out.println("\nЦикл без измения");
        itr = cowSet.iterator();
        while (itr.hasNext()) {
            User user = itr.next();
            System.out.println("  " + user.name);
        }
    }

}

class User {
    String name;
    public User(String name) {
        this.name = name;
    }
}

/**
 * вывод в консоль:
 *
 * Цикл с измением
 *   Прохор
 *   Георгий
 *   Михаил
 *
 * Цикл без измения
 *   Прохор
 *   Георгий Иванович
 *   Михаил
 *   Павел
 *
 * Process finished with exit code 0
 */