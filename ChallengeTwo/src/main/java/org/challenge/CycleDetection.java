package org.challenge;

import ch.lambdaj.function.convert.Converter;
import javolution.util.FastList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static ch.lambdaj.Lambda.convert;

/**
 * Solution for Challenge Two - Detecting Cycles
 *
 * Given a sequence, write a program to detect cycles within it.
 *
 * Ensure to account for numbers that have more than one digit eg. 12.
 * If there is no sequence, ignore that line.
 *
 * Print to stdout the first sequence you find in each line.
 * Ensure that there are no trailing empty spaces on each line you print.
 */
public class CycleDetection {

    /**
     * Containing the numbers which are cyclical
     */
    private List<Map<Integer,Integer>> cycleNumbersList = new ArrayList<Map<Integer, Integer>>();

    public List<Map<Integer,Integer>> getResult()
    {
        return this.cycleNumbersList;
    }

    /**
     * Process a given file
     *
     * @param input file inputstream
     * @throws IOException
     */
    public void process(InputStream input) throws IOException {

        Converter<String,Integer> fromStringToInteger = new Converter<String, Integer>() {
            public Integer convert(String s) {
                return Integer.valueOf( s );
            }
        };

        LineIterator it = IOUtils.lineIterator(input, "UTF-8");
        while( it.hasNext() )
        {
            FastList<Integer> fastList = new FastList<Integer>();
            fastList.addAll( convert(Arrays.asList(it.next().split("\\s")), fromStringToInteger) );
            FastList.Node<Integer> turtle = fastList.head() ;
            FastList.Node<Integer> rabbit = fastList.head().getNext();
            SortedMap<Integer,Integer> cycleNumberRow = new TreeMap<Integer, Integer>();
            while (rabbit.getValue() != null) {

                if (rabbit.getValue().equals(turtle.getValue())) {
                    // the faster moving node has caught up with the slower moving node
                    cycleNumberRow.put( fastList.indexOf( rabbit.getValue() ), rabbit.getValue() );
                    fastList.remove( fastList.indexOf( rabbit.getValue() ) );
                    turtle = fastList.head();
                    rabbit = fastList.head().getNext();
                    // return rabbit.data;
                } else if (rabbit.getNext().getValue() == null) {
                    // reached the end of list
                    break;
                } else {
                    turtle = turtle.getNext();
                    rabbit = rabbit.getNext().getNext();
                }
            }

            if( cycleNumberRow.size() > 0 )
                cycleNumbersList.add( cycleNumberRow );
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
            CycleDetection theCycle = new CycleDetection();
            theCycle.process(FileUtils.openInputStream(file));
            for( Map<Integer,Integer> cyclePerRow: theCycle.getResult() )
            {
                System.out.println(cyclePerRow.values().toString().replace("[", "").replace("]", ""));
            }

        } catch (IOException e) {
            System.out.println("File Read Error: " + e.getMessage());
            System.exit( 1 );
        }
    }

}
