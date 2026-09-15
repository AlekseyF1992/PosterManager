package ru.netology.javaqa;

public class Film {
    private String title;
    private String genre;

    public Film(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Film film = (Film) obj;
        return title.equals(film.title) && genre.equals(film.genre);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }
}