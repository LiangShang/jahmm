/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.test;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class GenerateTest {
    public final static String outputDir = "";

    private Hmm<ObservationInteger> hmm;

    @Before
    public void setUp() {
        hmm = new Hmm<ObservationInteger>(4, new OpdfIntegerFactory(2));
    }

    @Test
    public void testDotGenerator() {
        GenericHmmDrawerDot hmmDrawer = new GenericHmmDrawerDot();

        try {
            hmmDrawer.write(hmm, outputDir + "hmm-generate.dot");
        } catch (IOException e) {
            Assert.assertTrue("Writing file triggered an exception: " + e, false);
        }
    }
}
