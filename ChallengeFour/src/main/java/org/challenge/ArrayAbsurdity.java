package org.challenge;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
            arrayAbsurdity.process2(FileUtils.openInputStream(file));
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

    public void process2( InputStream inputStream ) throws  IOException {

        Function<String,String> findDupe = new Function<String,String>() {

            public String apply(@Nullable String input) {

                Function<String,Integer> StringToIntegerFunction = new Function<String, Integer>() {
                    public Integer apply(@Nullable String s) {
                        return Integer.valueOf( s );
                    }
                };

                Multiset<Integer> perRow = HashMultiset.create(
                        Lists.transform(Arrays.asList(StringUtils.substringAfter(input, ";").split(",")), StringToIntegerFunction)
                );

                Multiset.Entry<Integer> result = Iterators.find( perRow.entrySet().iterator(),
                    new Predicate<Multiset.Entry<Integer>>(){

                        public boolean apply(@Nullable Multiset.Entry<Integer> stringEntry) {

                            return stringEntry.getCount() > 1;
                        }
                    }
                );

                if( result != null ) return result.getElement().toString();
                return null;
            }
        };

        this.results = ImmutableList.copyOf(Iterators.transform(new Scanner(inputStream), findDupe));
    }

}
