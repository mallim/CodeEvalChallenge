package org.challenge;

import ch.lambdaj.function.convert.Converter;
import ch.lambdaj.function.matcher.LambdaJMatcher;
import ch.lambdaj.function.matcher.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static ch.lambdaj.Lambda.filter;

/**
 */
public class StackMain {

    private List<List<Integer>> results;

    public StackMain()
    {
        results = new ArrayList<List<Integer>>();
    }

    public List<List<Integer>> getResults() {
        return results;
    }

    public void setResults(List<List<Integer>> results) {
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
            LambdaJMatcher<Integer> positionMatcher = new Predicate<Integer>() {

                int pos = 0;

                @Override
                public boolean apply(Integer s) {
                    boolean result = ( pos % 2 ) == 0;
                    pos++;
                    return result;
                }
            };

            Converter<String,Integer> stringIntegerConvert = new Converter<String,Integer>(){

                public Integer convert(String s) {
                    return Integer.valueOf( s );
                }
            };

            String line = it.next();
            results.add(filter(positionMatcher, new FastListStack(convert(Arrays.asList(line.split("\\s")), stringIntegerConvert))));
        }
    }

    /**
     * Main function call
     *
     * @param args expecting a given file to process
     */
    public static void main( String[] args)
    {
        File file = new File(args[0]);
        try {
            StackMain stackProcessor = new StackMain();
            stackProcessor.process(FileUtils.openInputStream(file));
            for( List<Integer> resultPerRow : stackProcessor.getResults() )
            {
                System.out.println(resultPerRow.toString().replace("[", "").replace("]", ""));
            }
        } catch (IOException e) {
            System.out.println("File Read Error: " + e.getMessage());
            System.exit( 1 );
        }
    }

}
