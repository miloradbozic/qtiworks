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
package uk.ac.ed.ph.jqtiplus.node.expression.general;

import uk.ac.ed.ph.jqtiplus.attribute.value.IntegerAttribute;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.RandomExpression;
import uk.ac.ed.ph.jqtiplus.running.ProcessingContext;
import uk.ac.ed.ph.jqtiplus.validation.AttributeValidationError;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.value.IntegerValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

import java.util.Random;

/**
 * Selects A random integer from the specified range [min,max] satisfying min + step * n for some integer n.
 * For example, with min=2, max=11 and step=3 the values {2,5,8,11} are possible.
 * <p>
 * Additional conditions: max >= min, step >= 1
 * 
 * @author Jiri Kajaba
 */
public class RandomInteger extends RandomExpression {

    private static final long serialVersionUID = 4707680766519679314L;

    /** Name of this class in xml schema. */
    public static final String QTI_CLASS_NAME = "randomInteger";

    /** Name of min attribute in xml schema. */
    public static final String ATTR_MIN_NAME = "min";

    /** Name of max attribute in xml schema. */
    public static final String ATTR_MAX_NAME = "max";

    /** Name of step attribute in xml schema. */
    public static final String ATTR_STEP_NAME = "step";

    /** Default value of step attribute. */
    public static final int ATTR_STEP_DEFAULT_VALUE = 1;

    public RandomInteger(ExpressionParent parent) {
        this(parent, QTI_CLASS_NAME);
    }
    
    protected RandomInteger(ExpressionParent parent, String localName) {
        super(parent, localName);

        getAttributes().add(new IntegerAttribute(this, ATTR_MIN_NAME, true));
        getAttributes().add(new IntegerAttribute(this, ATTR_MAX_NAME, true));
        getAttributes().add(new IntegerAttribute(this, ATTR_STEP_NAME, ATTR_STEP_DEFAULT_VALUE, false));
    }

    /**
     * Gets value of min attribute.
     * 
     * @return value of min attribute
     * @see #setMinimum
     */
    public int getMin() {
        return getAttributes().getIntegerAttribute(ATTR_MIN_NAME).getComputedNonNullValue();
    }

    /**
     * Sets new value of min attribute.
     * 
     * @param minimum new value of min attribute
     * @see #getMinimum
     */
    public void setMin(Integer minimum) {
        getAttributes().getIntegerAttribute(ATTR_MIN_NAME).setValue(minimum);
    }

    /**
     * Gets value of max attribute.
     * 
     * @return value of max attribute
     * @see #setMaximum
     */
    public int getMax() {
        return getAttributes().getIntegerAttribute(ATTR_MAX_NAME).getComputedNonNullValue();
    }

    /**
     * Sets new value of max attribute.
     * 
     * @param maximum new value of max attribute
     * @see #getMaximum
     */
    public void setMax(Integer maximum) {
        getAttributes().getIntegerAttribute(ATTR_MAX_NAME).setValue(maximum);
    }

    /**
     * Gets value of step attribute.
     * 
     * @return value of step attribute
     * @see #setStep
     */
    public int getStep() {
        return getAttributes().getIntegerAttribute(ATTR_STEP_NAME).getComputedNonNullValue();
    }

    /**
     * Sets new value of step attribute.
     * 
     * @param step new value of step attribute
     * @see #getStep
     */
    public void setStep(Integer step) {
        getAttributes().getIntegerAttribute(ATTR_STEP_NAME).setValue(step);
    }

    @Override
    protected Long getSeedAttributeValue() {
        return null;
    }

    @Override
    protected void validateAttributes(ValidationContext context) {
        super.validateAttributes(context);

        if (getMax() < getMin()) {
            context.add(new AttributeValidationError(getAttributes().get(ATTR_MAX_NAME), "Attribute " + ATTR_MAX_NAME + " (" + getMax() +
                    ") cannot be lower than " + ATTR_MIN_NAME + " (" + getMin() + ")."));
        }

        if (getStep() < 1) {
            context.add(new AttributeValidationError(getAttributes().get(ATTR_STEP_NAME), "Attribute " + ATTR_STEP_NAME
                    + " ("
                    + getStep()
                    + ") must be positive."));
        }
    }

    @Override
    protected IntegerValue evaluateSelf(ProcessingContext context, Value[] childValues, int depth) {
        final Random randomGenerator = getRandomGenerator(depth);
        final int randomNumber = randomGenerator.nextInt((getMax() - getMin()) / getStep() + 1);
        final int randomInteger = getMin() + randomNumber * getStep();

        return new IntegerValue(randomInteger);
    }
}
