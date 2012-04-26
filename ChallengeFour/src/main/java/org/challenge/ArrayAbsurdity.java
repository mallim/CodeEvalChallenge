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

import static ch.lambdaj.Lambda.selectFirst;

/**
 * Solution to Challenge 4 - Array Absurdity
 *
 * Imagine we have an immutable array of size N which we know to be
 * filled with integers ranging from 0 to N-2, inclusive. Suppose we
 * know that the array contains exactly one duplicated entry and that
 * duplicate appears exactly twice. Find the duplicated entry. (For bonus points,
 * ensure your solution has constant space and time proportional to N)
 *
 * Your program should accept as its first argument a path to a filename.
 * Each line in this file is one test case. Ignore all empty lines.
 * Each line begins with a positive integer(N) i.e. the size of the array,
 * then a semicolon followed by a comma separated list of positive numbers ranging
 * from 0 to N-2, inclusive.
 *
 */
public class ArrayAbsurdity {

    /**
     * Main function call
     *
     * @param args expecting a given file to process
     */
    public static void main( String[] args)
    {
        File file = new File(args[0]);
        try {
            ArrayAbsurdity arrayAbsurdity = new ArrayAbsurdity();
            arrayAbsurdity.process(FileUtils.openInputStream(file));
            for( String result: arrayAbsurdity.getResults() )
            {
                System.out.println( result );
            }
        } catch (IOException e) {
            System.out.println("File Read Error: " + e.getMessage());
            System.exit( 1 );
        }
    }

    private List<String> results = new ArrayList<String>();

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    /**
     * Process the given file stream
     * @param inputStream
     * @throws IOException
     */
    public void process(InputStream inputStream) throws IOException {
        LineIterator it = IOUtils.lineIterator(inputStream, "UTF-8");

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

            String theLine = it.next();
            results.add((String) selectFirst(Arrays.asList(theLine.substring(theLine.indexOf(";") + 1).split(",")), dupeMatcher));
        }
    }
}
