package com.rajkumar.java8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.ToString;

/**
 * Java8 examples
 *
 */

record Actor(String firstName, String lastName) {
    
}

@Getter
@ToString
class Movie {
    private String title;
    private int releaseYear;
    private Set<Actor> actorsSet = new HashSet<>();
    
    public Movie(String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear != null ? releaseYear : 1900;
    }
    
    public void addActor(Actor actor) {
        this.actorsSet.add(actor);
    }
}

public class Java8Examples {
    
    private Stream<String> fileLinesStream = null;
    private Set<Movie> moviesSet = null;
    private Set<Actor> actorsSet = null;
    private static final String MOVIES_FILE = "movies.txt";
    
    Java8Examples() {
        try {
            fileLinesStream = Files.lines(Paths.get(getClass().getClassLoader().getResource(MOVIES_FILE).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        moviesSet = fileLinesStream.map(eachLine -> {
            String[] elements = eachLine.split("/");
            String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
            String releaseYear = elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));
            
            if (releaseYear.contains(",")) {
                return null;
            }
            
            Movie movie = new Movie(title, Integer.valueOf(releaseYear));
            
            IntStream.range(1, elements.length).forEach(index -> {
                String[] name = elements[index].split(", ");
                String lastName = name[0].trim();
                String firstName = "";
                if (name.length > 1) {
                    firstName = name[1].trim();
                }
                
                Actor actor = new Actor(lastName, firstName);
                movie.addActor(actor);
            });
            
            return movie;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        
        actorsSet = moviesSet.stream().flatMap(eachMovie -> eachMovie.getActorsSet().stream())
                .collect(Collectors.toSet());
    }
    
    public Set<Movie> getMoviesSet() {
        return new HashSet<>(moviesSet);
    }
    
    public Set<Actor> getActorsSet() {
        return new HashSet<>(actorsSet);
    }
    
    public long getReleaseYearsCount() {
        return moviesSet.stream().mapToInt(movie -> movie.getReleaseYear()).distinct().count();
    }
    
    public IntSummaryStatistics getMoviesReleaseYearsSummary() {
        return moviesSet.stream().mapToInt(movie -> movie.getReleaseYear()).summaryStatistics();
    }
    
    public Movie getMovieWithLargestActors() {
        return moviesSet.stream().max(Comparator.comparingInt(movie -> movie.getActorsSet().size()))
                .orElse(new Movie(null, null));
    }
    
    public Entry<Integer, Long> getYearWithLargestMovies() {
        return moviesSet.stream().collect(Collectors.groupingBy(movie -> movie.getReleaseYear(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }
}
