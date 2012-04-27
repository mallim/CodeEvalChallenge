package org.challenge;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    private List<String> duplicates = new ArrayList<String>();

    /**
     * Returns the intersections for each processing line
     * @return
     */
    public List<String> getDuplicates() {
        return this.duplicates;
    }

    public void process2( InputStream input ){

        Function<String,String> findDupe = new Function<String,String>() {

            public String apply(@Nullable String s) {

                Function<String,Integer> StringToIntegerFunction = new Function<String, Integer>() {
                    public Integer apply(@Nullable String input) {
                        return Integer.valueOf( input );
                    }
                };

                Set<Integer> firstSet =
                Sets.newLinkedHashSet(
                    Lists.transform(Arrays.asList(StringUtils.substringBefore(s, ";").split(",")), StringToIntegerFunction )
                );

                Set<Integer> secondSet =
                Sets.newLinkedHashSet(
                    Lists.transform(Arrays.asList(StringUtils.substringAfter(s, ";").split(",")), StringToIntegerFunction )
                );

                return Joiner.on(",").join( Sets.intersection(firstSet, secondSet) );
            }
        };

        this.duplicates = ImmutableList.copyOf( Iterators.transform( new Scanner( input ), findDupe ) );
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
            intersection.process2(FileUtils.openInputStream(file));
            for( String duplicateValuesPerRow: intersection.getDuplicates() )
            {
                System.out.println(duplicateValuesPerRow.toString().replace("[", "").replace("]", ""));
            }
        } catch (IOException e) {
            System.out.println("File Read Error: " + e.getMessage());
            System.exit( 1 );
        }
    }

}
