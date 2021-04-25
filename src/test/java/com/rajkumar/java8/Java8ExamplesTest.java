package com.rajkumar.java8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Java8ExamplesTest {
    
    private static Java8Examples java8Examples;
    
    @BeforeAll
    private static void initialise() {
        java8Examples = new Java8Examples();
    }
    
    @Test
    void testCountOfActors() {
        assertEquals(168727, java8Examples.getActorsSet().size());
    }
    
    @Test
    void testCountOfMovies() {
        assertEquals(13891, java8Examples.getMoviesSet().size());
    }
    
    @Test
    void testCountOfReleaseYears() {
        assertEquals(76, java8Examples.getReleaseYearsCount());
    }
    
    @Test
    void testOldestReleaseYear() {
        assertEquals(1916, java8Examples.getMoviesReleaseYearsSummary().getMin());
    }
    
    @Test
    void testLatestReleaseYear() {
        assertEquals(2004, java8Examples.getMoviesReleaseYearsSummary().getMax());
    }
    
    @Test
    void testLargestActorsPlayedMovie() {
        Movie movieWithLargestPlayers = java8Examples.getMovieWithLargestActors();
        assertEquals("Malcolm X", movieWithLargestPlayers.getTitle());
        assertEquals(238, movieWithLargestPlayers.getActorsSet().size());
    }
    
    @Test
    void testGetYearWithLargestMovies() {
        Entry<Integer, Long> largestYearEntry = java8Examples.getYearWithLargestMovies();
        assertEquals(1997, largestYearEntry.getKey());
        assertEquals(678, largestYearEntry.getValue());
    }
}
