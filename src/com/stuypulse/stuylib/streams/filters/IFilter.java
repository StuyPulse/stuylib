package com.stuypulse.stuylib.streams.filters;

/**
 * This is the Filter interface class that gives a definition for how to implement a filter.
 *
 * All that a filter does is take in the next double in a series and gives you the filtered value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public interface IFilter {

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value in the stream
     * @return the output value of the filter
     */
    public double get(double next);
}
