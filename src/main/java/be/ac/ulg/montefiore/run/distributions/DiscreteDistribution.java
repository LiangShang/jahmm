/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.distributions;

import java.io.*;


/**
 * This interface must be implemented by all the package's classes implementing
 * a discrete random distribution.  Distributions are not mutable.
 */
public interface DiscreteDistribution
        extends Serializable {
    /**
     * Generates a pseudo-random number.  The numbers generated by this function
     * are drawn according to the pseudo-random distribution described by the
     * object that implements it.
     *
     * @return A pseudo-random number.
     */
    public int generate();


    /**
     * Returns the probability of a given number.
     *
     * @param n An integer.
     */
    public double probability(int n);
}
