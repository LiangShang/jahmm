/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.test;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchScaledLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;
import be.ac.ulg.montefiore.run.jahmm.toolbox.KullbackLeiblerDistanceCalculator;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class LearnerTest {
    final static private double DELTA = 5.E-3;

    private Hmm<ObservationInteger> hmm;
    private List<List<ObservationInteger>> sequences;
    private KullbackLeiblerDistanceCalculator klc;


    @Before
    public void setUp() {
        hmm = new Hmm<ObservationInteger>(3, new OpdfIntegerFactory(10));
        hmm.getOpdf(0).fit(new ObservationInteger(1), new ObservationInteger(2));

        MarkovGenerator<ObservationInteger> mg =
                new MarkovGenerator<ObservationInteger>(hmm);

        sequences = new ArrayList<List<ObservationInteger>>();
        for (int i = 0; i < 100; i++)
            sequences.add(mg.observationSequence(100));

        klc = new KullbackLeiblerDistanceCalculator();
    }

    @Test
    public void testBaumWelch() {
        /* Model sequences using BW algorithm */

        BaumWelchLearner bwl = new BaumWelchLearner();

        Hmm<ObservationInteger> bwHmm = bwl.learn(hmm, sequences);

        Assert.assertEquals(0., klc.distance(bwHmm, hmm), DELTA);
        
        /* Model sequences using the scaled BW algorithm */

        BaumWelchScaledLearner bwsl = new BaumWelchScaledLearner();
        bwHmm = bwsl.learn(hmm, sequences);

        Assert.assertEquals(0., klc.distance(bwHmm, hmm), DELTA);
    }

    @Test
    public void testKMeans() {
        KMeansLearner<ObservationInteger> kml =
                new KMeansLearner<ObservationInteger>(5,
                        new OpdfIntegerFactory(10), sequences);
        Assert.assertEquals(0., klc.distance(kml.learn(), hmm), DELTA);
    }
}
