package org.challenge;

import ch.lambdaj.function.matcher.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.hamcrest.Matcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static ch.lambdaj.Lambda.*;

/**
 * Solution for Challenge One - Set Intersection
 *
 * Given two sorted list of numbers(ascending order).
 * The lists themselves are comma delimited and the two lists
 * are semicolon delimited.
 *
 * Print out the intersection of these two sets.
 */
public class Intersection {

    private List<List<String>> duplicates = new ArrayList< List<String>>();

    /**
     * Returns the intersections for each processing line
     * @return
     */
    public List<List<String>> getDuplicates() {
        return this.duplicates;
    }

    /**
     * Process an inputstream based on the requirement
     *
     * @param input a file inputstream
     * @throws IOException
     */
    public void process(InputStream input) throws IOException {
        try {

            LineIterator it = IOUtils.lineIterator(input, "UTF-8");

            while( it.hasNext() )
            {

                Matcher<String> dupeMatcher = new Predicate<String>() {

                    private Set<Integer> uniqueValues = new HashSet<Integer>();

                    @Override
                    public boolean apply(String s) {
                        Integer output = Integer.valueOf( s );
                        return uniqueValues.add( output ) == false;
                    }
                };

                List<String> dupePerRow = filter( dupeMatcher, Arrays.asList(it.nextLine().split("[\\s,;]+")) );
                this.duplicates.add( dupePerRow );
            }

        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Main function call
     *
     * @param args expecting a given file to process
     */
    public static void main(String[] args) {

        File file = new File(args[0]);
        try {
            Intersection intersection = new Intersection();
            intersection.process(FileUtils.openInputStream( file ));
            for( List<String> duplicateValuesPerRow: intersection.getDuplicates() )
            {
                System.out.println(duplicateValuesPerRow.toString().replace("[", "").replace("]", ""));
            }
        } catch (IOException e) {
            System.out.println("File Read Error: " + e.getMessage());
            System.exit( 1 );
        }
    }

}
