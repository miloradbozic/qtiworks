/* Copyright (c) 2012, University of Edinburgh.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of the University of Edinburgh nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * This software is derived from (and contains code from) QTItools and MathAssessEngine.
 * QTItools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package org.qtitools.qti.node.expression.operator;

import uk.ac.ed.ph.jqtiplus.exception.QTIBaseTypeException;
import uk.ac.ed.ph.jqtiplus.exception.QTICardinalityException;
import uk.ac.ed.ph.jqtiplus.exception.QTIRuntimeException;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.qtitools.qti.node.expression.ExpressionRefuseTest;

/**
 * Test of <code>Match</code> expression.
 * 
 * @see uk.ac.ed.ph.jqtiplus.node.expression.operator.Match
 */
@RunWith(Parameterized.class)
public class MatchRefuseTest extends ExpressionRefuseTest {

    /**
     * Creates test data for this test.
     * 
     * @return test data for this test
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // single
                { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<multiple>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</multiple>" +
                        "</match>", QTICardinalityException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<ordered>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</ordered>" +
                        "</match>", QTICardinalityException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<recordEx identifiers='key_1'>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</recordEx>" +
                        "</match>", QTICardinalityException.class },
                // multiple
                { "<match>" +
                        "<multiple>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</multiple>" +
                        "<ordered>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</ordered>" +
                        "</match>", QTICardinalityException.class }, { "<match>" +
                        "<multiple>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</multiple>" +
                        "<recordEx identifiers='key_1'>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</recordEx>" +
                        "</match>", QTICardinalityException.class },
                // ordered
                { "<match>" +
                        "<ordered>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</ordered>" +
                        "<recordEx identifiers='key_1'>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</recordEx>" +
                        "</match>", QTICardinalityException.class },
                // identifier
                { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='boolean'>false</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='identifier'>identifier</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // boolean
                { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='boolean'>true</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // integer
                { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='integer'>1</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // float
                { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='float'>1</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // string
                { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='string'>string</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // point
                { "<match>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='point'>1 1</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // pair
                { "<match>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='pair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // directedPair
                { "<match>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='directedPair'>identifier_1 identifier_2</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
                // duration
                { "<match>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class }, { "<match>" +
                        "<recordEx identifiers='key_1'>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</recordEx>" +
                        "<recordEx identifiers='key_1'>" +
                        "<baseValue baseType='duration'>1</baseValue>" +
                        "</recordEx>" +
                        "</match>", QTIBaseTypeException.class },
                // file
                { "<match>" +
                        "<baseValue baseType='file'>file</baseValue>" +
                        "<baseValue baseType='uri'>uri</baseValue>" +
                        "</match>", QTIBaseTypeException.class },
        });
    }

    /**
     * Constructs <code>Match</code> expression test.
     * 
     * @param xml xml data used for creation tested expression
     * @param expectedException expected exception during evaluation of tested
     *            expression
     */
    public MatchRefuseTest(String xml, Class<? extends QTIRuntimeException> expectedException) {
        super(xml, expectedException);
    }
}