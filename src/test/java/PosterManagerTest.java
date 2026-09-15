package ru.netology.javaqa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PosterManagerTest {


    // Проверяем, что при создании менеджера без параметров лимит по умолчанию равен 5, а список фильмов изначально пуст (0).
    @Test
    public void shouldCreateManagerWithDefaultLimit() {
        PosterManager manager = new PosterManager();
        Assertions.assertEquals(5, manager.getLimit());
        Assertions.assertEquals(0, manager.getFilmsCount());
    }

    // Проверяем сохранение параметра 3 внутри объекта.
    @Test
    public void shouldCreateManagerWithCustomLimit() {
        PosterManager manager = new PosterManager(3);
        Assertions.assertEquals(3, manager.getLimit());
        Assertions.assertEquals(0, manager.getFilmsCount());
    }

    // Дополнительная проверка конструктора с другим значением лимита (7),
    @Test
    public void shouldCreateManagerWithLimit7() {
        PosterManager manager = new PosterManager(7);
        Assertions.assertEquals(7, manager.getLimit());
    }


    // Проверяем, что метод save корректно добавляет один фильм
    @Test
    public void shouldSaveOneFilm() {
        PosterManager manager = new PosterManager();
        Film film = new Film("Бладшот", "боевик");
        manager.save(film);
        Assertions.assertEquals(1, manager.getFilmsCount());
    }

    // Проверяем, что метод save корректно добавляет несколько фильмов подряд
    @Test
    public void shouldSaveMultipleFilms() {
        PosterManager manager = new PosterManager();
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));
        manager.save(new Film("Отель Белград", "комедия"));
        Assertions.assertEquals(3, manager.getFilmsCount());
    }


    // Проверяем, что метод findAll возвращает пустой массив (длиной 0)
    @Test
    public void shouldFindAllEmptyArray() {
        PosterManager manager = new PosterManager();
        Film[] result = manager.findAll();
        Assertions.assertEquals(0, result.length);
    }

    // Проверяем, что метод findAll возвращает все добавленные фильмы
    @Test
    public void shouldFindAllFilmsInOrder() {
        PosterManager manager = new PosterManager();
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);

        Film[] result = manager.findAll();
        Assertions.assertEquals(3, result.length);
        Assertions.assertEquals(film1, result[0]);
        Assertions.assertEquals(film2, result[1]);
        Assertions.assertEquals(film3, result[2]);
    }


    // Проверяем возврат пустого массива
    @Test
    public void shouldFindLastWhenEmpty() {
        PosterManager manager = new PosterManager();
        Film[] result = manager.findLast();
        Assertions.assertEquals(0, result.length);
    }

    // Проверяем ветку 'if' (когда фильмов меньше лимита).
    // Если добавлено 3 фильма при лимите 5, должны вернуться все 3 фильма в обратном порядке.
    @Test
    public void shouldFindLastWhenLessThanLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));
        manager.save(new Film("Отель Белград", "комедия"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(3, result.length); // вернулись все 3 фильма
        Assertions.assertEquals("Отель Белград", result[0].getTitle()); // последний добавленный
        Assertions.assertEquals("Вперёд", result[1].getTitle());
        Assertions.assertEquals("Бладшот", result[2].getTitle()); // первый добавленный
    }

    // Проверяем граничное условие: когда количество фильмов (5) в точности равно лимиту (5).
    @Test
    public void shouldFindLastExactlyLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));
        manager.save(new Film("Отель Белград", "комедия"));
        manager.save(new Film("Джентльмены", "боевик"));
        manager.save(new Film("Человек-невидимка", "ужасы"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(5, result.length); // ровно лимит
        Assertions.assertEquals("Человек-невидимка", result[0].getTitle());
        Assertions.assertEquals("Джентльмены", result[1].getTitle());
        Assertions.assertEquals("Отель Белград", result[2].getTitle());
        Assertions.assertEquals("Вперёд", result[3].getTitle());
        Assertions.assertEquals("Бладшот", result[4].getTitle());
    }

    // Проверяем ветку 'else' (когда фильмов больше лимита).
    @Test
    public void shouldFindLastMoreThanLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        manager.save(new Film("Бладшот", "боевик"));       // 1
        manager.save(new Film("Вперёд", "мультфильм"));    // 2
        manager.save(new Film("Отель Белград", "комедия"));// 3
        manager.save(new Film("Джентльмены", "боевик"));   // 4
        manager.save(new Film("Человек-невидимка", "ужасы"));// 5
        manager.save(new Film("Тролли", "мультфильм"));    // 6
        manager.save(new Film("Номер один", "комедия"));   // 7

        Film[] result = manager.findLast();
        Assertions.assertEquals(5, result.length); // только последние 5
        Assertions.assertEquals("Номер один", result[0].getTitle());        // 7-й
        Assertions.assertEquals("Тролли", result[1].getTitle());            // 6-й
        Assertions.assertEquals("Человек-невидимка", result[2].getTitle()); // 5-й
        Assertions.assertEquals("Джентльмены", result[3].getTitle());       // 4-й
        Assertions.assertEquals("Отель Белград", result[4].getTitle());     // 3-й
        // "Бладшот" и "Вперёд" не вошли
    }

    // Проверяем, что логика обрезки массива работает корректно с кастомным лимитом (3)
    @Test
    public void shouldFindLastWithCustomLimit3() {
        PosterManager manager = new PosterManager(3); // лимит = 3
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));
        manager.save(new Film("Отель Белград", "комедия"));
        manager.save(new Film("Джентльмены", "боевик"));
        manager.save(new Film("Человек-невидимка", "ужасы"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(3, result.length);
        Assertions.assertEquals("Человек-невидимка", result[0].getTitle());
        Assertions.assertEquals("Джентльмены", result[1].getTitle());
        Assertions.assertEquals("Отель Белград", result[2].getTitle());
    }

    // Проверяем работу с большим кастомным лимитом (7) при добавлении 10 фильмов.
    // Должны вернуться ровно 7 последних, первые 3 должны быть отброшены.
    @Test
    public void shouldFindLastWithCustomLimit7() {
        PosterManager manager = new PosterManager(7); // лимит = 7
        for (int i = 1; i <= 10; i++) {
            manager.save(new Film("Фильм" + i, "жанр" + i));
        }

        Film[] result = manager.findLast();
        Assertions.assertEquals(7, result.length);
        Assertions.assertEquals("Фильм10", result[0].getTitle());
        Assertions.assertEquals("Фильм9", result[1].getTitle());
        Assertions.assertEquals("Фильм8", result[2].getTitle());
        Assertions.assertEquals("Фильм7", result[3].getTitle());
        Assertions.assertEquals("Фильм6", result[4].getTitle());
        Assertions.assertEquals("Фильм5", result[5].getTitle());
        Assertions.assertEquals("Фильм4", result[6].getTitle());
        // Фильм1, Фильм2, Фильм3 не вошли
    }

    // Проверяем работу метода, когда лимит установлен в 1.
    // Должен вернуться массив ровно из одного (самого последнего) фильма.
    @Test
    public void shouldFindLastWithLimit1() {
        PosterManager manager = new PosterManager(1); // лимит = 1
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));
        manager.save(new Film("Отель Белград", "комедия"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(1, result.length);
        Assertions.assertEquals("Отель Белград", result[0].getTitle());
    }

    // Проверяем, когда заданный лимит (10) заведомо больше количества добавленных фильмов (5).// Должны вернуться все 5 фильмов без ошибок и обрезки.
    @Test
    public void shouldFindLastWithLimit10() {
        PosterManager manager = new PosterManager(10); // лимит = 10
        for (int i = 1; i <= 5; i++) {
            manager.save(new Film("Фильм" + i, "жанр" + i));
        }

        Film[] result = manager.findLast();
        Assertions.assertEquals(5, result.length); // меньше чем лимит
        Assertions.assertEquals("Фильм5", result[0].getTitle());
        Assertions.assertEquals("Фильм4", result[1].getTitle());
        Assertions.assertEquals("Фильм3", result[2].getTitle());
        Assertions.assertEquals("Фильм2", result[3].getTitle());
        Assertions.assertEquals("Фильм1", result[4].getTitle());
    }

    // Проверяем возврат пустонр массива
    @Test
    public void shouldFindLastWithZeroLimit() {
        PosterManager manager = new PosterManager(0); // лимит = 0
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(0, result.length); // ничего не вернётся
    }

    // Проверяем устойчивость к очень большим значениям лимита (100),
    @Test
    public void shouldFindLastWithLargeLimit() {
        PosterManager manager = new PosterManager(100); // лимит = 100
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));

        Film[] result = manager.findLast();
        Assertions.assertEquals(2, result.length); // вернулись все фильмы
    }


    // Цикличная проверка(добавление, проверка, снова добавление, снова проверка).
    @Test
    public void shouldWorkCorrectlyWithMultipleOperations() {
        PosterManager manager = new PosterManager(3);

        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));

        Film[] all1 = manager.findAll();
        Assertions.assertEquals(2, all1.length);

        Film[] last1 = manager.findLast();
        Assertions.assertEquals(2, last1.length);
        Assertions.assertEquals("Вперёд", last1[0].getTitle());

        manager.save(new Film("Отель Белград", "комедия"));
        manager.save(new Film("Джентльмены", "боевик"));

        Film[] all2 = manager.findAll();
        Assertions.assertEquals(4, all2.length);

        Film[] last2 = manager.findLast();
        Assertions.assertEquals(3, last2.length); // лимит = 3
        Assertions.assertEquals("Джентльмены", last2[0].getTitle());
        Assertions.assertEquals("Отель Белград", last2[1].getTitle());
        Assertions.assertEquals("Вперёд", last2[2].getTitle());
    }

    // Проверяем, что объект равен сам себе (ветка: if (this == obj) return true)
    @Test
    public void testFilmEqualsSameObject() {
        Film film = new Film("Титаник", "драма");
        Assertions.assertTrue(film.equals(film));
    }

    // Проверяем, что фильм не равен null (ветка: if (obj == null) return false)
    @Test
    public void testFilmEqualsNull() {
        Film film = new Film("Титаник", "драма");
        Assertions.assertFalse(film.equals(null));
    }

    // Проверяем, что фильм не равен объекту другого класса
    // (ветка: if (getClass() != obj.getClass()) return false)
    @Test
    public void testFilmEqualsDifferentClass() {
        Film film = new Film("Титаник", "драма");
        String str = "Титаник";
        Assertions.assertFalse(film.equals(str));
    }

    // Проверяем, что два одинаковых фильма равны (ветка: return title.equals && genre.equals)
    @Test
    public void testFilmEqualsSameFilms() {
        Film film1 = new Film("Титаник", "драма");
        Film film2 = new Film("Титаник", "драма");
        Assertions.assertTrue(film1.equals(film2));
    }

    // Проверяем, что фильмы с разными названиями не равны
    @Test
    public void testFilmEqualsDifferentTitle() {
        Film film1 = new Film("Титаник", "драма");
        Film film2 = new Film("Аватар", "драма");
        Assertions.assertFalse(film1.equals(film2));
    }

    // Проверяем, что фильмы с разными жанрами не равны
    @Test
    public void testFilmEqualsDifferentGenre() {
        Film film1 = new Film("Титаник", "драма");
        Film film2 = new Film("Титаник", "боевик");
        Assertions.assertFalse(film1.equals(film2));
    }

    // Проверяем, что hashCode одинаковый для равных фильмов
    @Test
    public void testFilmHashCodeSameForEqualFilms() {
        Film film1 = new Film("Титаник", "драма");
        Film film2 = new Film("Титаник", "драма");
        Assertions.assertEquals(film1.hashCode(), film2.hashCode());
    }

    // Проверяем, что hashCode разный для разных фильмов
    @Test
    public void testFilmHashCodeDifferentForDifferentFilms() {
        Film film1 = new Film("Титаник", "драма");
        Film film2 = new Film("Аватар", "боевик");

        int hash1 = film1.hashCode();
        int hash2 = film2.hashCode();
        Assertions.assertNotNull(hash1);
        Assertions.assertNotNull(hash2);
    }

    // Проверяем геттер getTitle
    @Test
    public void testFilmGetTitle() {
        Film film = new Film("Матрица", "фантастика");
        Assertions.assertEquals("Матрица", film.getTitle());
    }

    // Проверяем геттер getGenre
    @Test
    public void testFilmGetGenre() {
        Film film = new Film("Матрица", "фантастика");
        Assertions.assertEquals("фантастика", film.getGenre());
    }

    // Проверяем, что фильм не равен самому себе
    @Test
    public void testFilmHashCodeNotNull() {
        Film film = new Film("Начало", "фантастика");
        Assertions.assertNotNull(film.hashCode());
    }
}