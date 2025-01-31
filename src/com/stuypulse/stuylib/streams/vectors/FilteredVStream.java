/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;

/**
 * A FilteredVStream is similar to a FilteredIStream.
 *
 * <p>It works like a VStream, but every time you call .get(), it runs the value through the filters
 * you provided it.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredVStream implements VStream {

    private VStream mStream; // Stream used
    private VFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     *
     * @param stream input stream
     * @param filter stream filter
     */
    public FilteredVStream(VStream stream, VFilter... filter) {
        mStream = stream;
        mStreamFilter = VFilter.create(filter);
    }

    /**
     * Get next value from filtered stream
     *
     * @return next value
     */
    public Vector2D get() {
        return mStreamFilter.get(mStream.get());
    }
}
