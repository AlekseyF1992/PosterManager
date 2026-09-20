package ru.netology.javaqa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PosterManagerTest {

    // Проверяем, что при создании менеджера без параметров лимит по умолчанию равен 5, а список фильмов изначально пустой.
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

    // Дополнительная проверка конструктора с другим значением лимита,
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

    // Проверяем, что метод findAll возвращает пустой массив
    @Test
    public void shouldFindAllEmptyArray() {
        PosterManager manager = new PosterManager();
        Film[] result = manager.findAll();
        Film[] expected = new Film[0];
        Assertions.assertArrayEquals(expected, result);
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
        Film[] expected = new Film[]{film1, film2, film3};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем возврат пустого массива
    @Test
    public void shouldFindLastWhenEmpty() {
        PosterManager manager = new PosterManager();
        Film[] result = manager.findLast();
        Film[] expected = new Film[0];
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем ветку 'if' (когда фильмов меньше лимита).
    // Если добавлено 3 фильма при лимите 5, должны вернуться все 3 фильма в обратном порядке.
    @Test
    public void shouldFindLastWhenLessThanLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film3, film2, film1};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем граничное условие: когда количество фильмов (5) в точности равно лимиту (5).
    @Test
    public void shouldFindLastExactlyLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");
        Film film4 = new Film("Джентльмены", "боевик");
        Film film5 = new Film("Человек-невидимка", "ужасы");

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);
        manager.save(film4);
        manager.save(film5);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film5, film4, film3, film2, film1};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем ветку 'else' (когда фильмов больше лимита).
    @Test
    public void shouldFindLastMoreThanLimit() {
        PosterManager manager = new PosterManager(); // лимит = 5
        Film film1 = new Film("Бладшот", "боевик");       // 1
        Film film2 = new Film("Вперёд", "мультфильм");    // 2
        Film film3 = new Film("Отель Белград", "комедия");// 3
        Film film4 = new Film("Джентльмены", "боевик");   // 4
        Film film5 = new Film("Человек-невидимка", "ужасы");// 5
        Film film6 = new Film("Тролли", "мультфильм");    // 6
        Film film7 = new Film("Номер один", "комедия");   // 7

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);
        manager.save(film4);
        manager.save(film5);
        manager.save(film6);
        manager.save(film7);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film7, film6, film5, film4, film3};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем, что логика обрезки массива работает корректно
    @Test
    public void shouldFindLastWithCustomLimit3() {
        PosterManager manager = new PosterManager(3); // лимит = 3
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");
        Film film4 = new Film("Джентльмены", "боевик");
        Film film5 = new Film("Человек-невидимка", "ужасы");

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);
        manager.save(film4);
        manager.save(film5);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film5, film4, film3};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем работу с большим кастомным лимитом (7) при добавлении 10 фильмов.
    @Test
    public void shouldFindLastWithCustomLimit7() {
        PosterManager manager = new PosterManager(7); // лимит = 7
        Film[] films = new Film[10];
        for (int i = 1; i <= 10; i++) {
            films[i - 1] = new Film("Фильм" + i, "жанр" + i);
            manager.save(films[i - 1]);
        }

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{
                films[9], // Фильм10
                films[8], // Фильм9
                films[7], // Фильм8
                films[6], // Фильм7
                films[5], // Фильм6
                films[4], // Фильм5
                films[3]  // Фильм4
        };
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем работу метода, когда лимит установлен в 1.
    @Test
    public void shouldFindLastWithLimit1() {
        PosterManager manager = new PosterManager(1); // лимит = 1
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");

        manager.save(film1);
        manager.save(film2);
        manager.save(film3);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film3};
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем, когда заданный лимит (10) заведомо больше количества добавленных фильмов (5).
    @Test
    public void shouldFindLastWithLimit10() {
        PosterManager manager = new PosterManager(10); // лимит = 10
        Film[] films = new Film[5];
        for (int i = 1; i <= 5; i++) {
            films[i - 1] = new Film("Фильм" + i, "жанр" + i);
            manager.save(films[i - 1]);
        }

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{
                films[4], // Фильм5
                films[3], // Фильм4
                films[2], // Фильм3
                films[1], // Фильм2
                films[0]  // Фильм1
        };
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем возврат пустого массива
    @Test
    public void shouldFindLastWithZeroLimit() {
        PosterManager manager = new PosterManager(0); // лимит = 0
        manager.save(new Film("Бладшот", "боевик"));
        manager.save(new Film("Вперёд", "мультфильм"));

        Film[] result = manager.findLast();
        Film[] expected = new Film[0];
        Assertions.assertArrayEquals(expected, result);
    }

    // Проверяем устойчивость к очень большим значениям лимита (100),
    @Test
    public void shouldFindLastWithLargeLimit() {
        PosterManager manager = new PosterManager(100); // лимит = 100
        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");

        manager.save(film1);
        manager.save(film2);

        Film[] result = manager.findLast();
        Film[] expected = new Film[]{film2, film1};
        Assertions.assertArrayEquals(expected, result);
    }

    // Цикличная проверка(добавление, проверка, снова добавление, снова проверка).
    @Test
    public void shouldWorkCorrectlyWithMultipleOperations() {
        PosterManager manager = new PosterManager(3);

        Film film1 = new Film("Бладшот", "боевик");
        Film film2 = new Film("Вперёд", "мультфильм");
        Film film3 = new Film("Отель Белград", "комедия");
        Film film4 = new Film("Джентльмены", "боевик");

        manager.save(film1);
        manager.save(film2);

        Film[] all1 = manager.findAll();
        Film[] expectedAll1 = new Film[]{film1, film2};
        Assertions.assertArrayEquals(expectedAll1, all1);

        Film[] last1 = manager.findLast();
        Film[] expectedLast1 = new Film[]{film2, film1};
        Assertions.assertArrayEquals(expectedLast1, last1);

        manager.save(film3);
        manager.save(film4);

        Film[] all2 = manager.findAll();
        Film[] expectedAll2 = new Film[]{film1, film2, film3, film4};
        Assertions.assertArrayEquals(expectedAll2, all2);

        Film[] last2 = manager.findLast();
        Film[] expectedLast2 = new Film[]{film4, film3, film2};
        Assertions.assertArrayEquals(expectedLast2, last2);
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

    // Проверяем, что hashCode не null
    @Test
    public void testFilmHashCodeNotNull() {
        Film film = new Film("Начало", "фантастика");
        Assertions.assertNotNull(film.hashCode());
    }
}