package ru.netology.javaqa;

public class PosterManager {
    private Film[] films = new Film[0];
    private int limit;

    // Конструктор по умолчанию (лимит = 5)
    public PosterManager() {
        this.limit = 5;
    }

    // Конструктор с указанием лимита
    public PosterManager(int limit) {
        this.limit = limit;
    }

    // Добавление фильма
    public void save(Film film) {
        Film[] tmp = new Film[films.length + 1];
        for (int i = 0; i < films.length; i++) {
            tmp[i] = films[i];
        }
        tmp[tmp.length - 1] = film;
        films = tmp;
    }

    // Получить все фильмы
    public Film[] findAll() {
        return films;
    }

    // Получить последние фильмы в обратном порядке
    public Film[] findLast() {
        int resultLength;
        if (films.length < limit) {
            resultLength = films.length;
        } else {
            resultLength = limit;
        }

        Film[] result = new Film[resultLength];
        for (int i = 0; i < result.length; i++) {
            result[i] = films[films.length - 1 - i];
        }
        return result;
    }

    // Геттеры для тестирования
    public int getLimit() {
        return limit;
    }

    public int getFilmsCount() {
        return films.length;
    }
}