/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.test;

import be.ac.ulg.montefiore.run.jahmm.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class BasicIntegerTest {
    final static private double DELTA = 1.E-10;

    private Hmm<ObservationInteger> hmm;
    private List<ObservationInteger> sequence;
    private List<ObservationInteger> randomSequence;

    @Before
    public void setUp() {
        hmm = new Hmm<ObservationInteger>(5, new OpdfIntegerFactory(10));
        hmm.setOpdf(1, new OpdfInteger(6));

        sequence = new ArrayList<ObservationInteger>();
        for (int i = 0; i < 5; i++)
            sequence.add(new ObservationInteger(i));

        randomSequence = new ArrayList<ObservationInteger>();
        for (int i = 0; i < 30000; i++)
            randomSequence.
                    add(new ObservationInteger((int) (Math.random() * 10.)));
    }

    @Test
    public void testForwardBackward() {
        ForwardBackwardCalculator fbc =
                new ForwardBackwardCalculator(sequence, hmm);

        Assert.assertEquals(1.8697705349794245E-5, fbc.probability(), DELTA);

        ForwardBackwardScaledCalculator fbsc =
                new ForwardBackwardScaledCalculator(sequence, hmm);

        Assert.assertEquals(1.8697705349794245E-5, fbsc.probability(), DELTA);
    }


    @Test
    public void testViterbi() {
        ViterbiCalculator vc = new ViterbiCalculator(sequence, hmm);

        Assert.assertEquals(4.1152263374485705E-8,
                Math.exp(vc.lnProbability()), DELTA);
    }


    @Test
    public void testKMeansCalculator() {
        int nbClusters = 20;

        KMeansCalculator<ObservationInteger> kmc = new
                KMeansCalculator<ObservationInteger>(nbClusters, randomSequence);

        Assert.assertEquals("KMeans did not produce expected number of clusters",
                nbClusters, kmc.nbClusters());
    }
}
