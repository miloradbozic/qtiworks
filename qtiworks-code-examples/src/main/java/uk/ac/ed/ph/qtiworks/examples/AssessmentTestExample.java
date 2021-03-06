/* $Id:SAXErrorHandler.java 2824 2008-08-01 15:46:17Z davemckain $
 *
 * Copyright (c) 2012-2013, The University of Edinburgh.
 * All Rights Reserved
 */
package uk.ac.ed.ph.qtiworks.examples;

import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import uk.ac.ed.ph.jqtiplus.notification.NotificationLogListener;
import uk.ac.ed.ph.jqtiplus.running.TestPlanner;
import uk.ac.ed.ph.jqtiplus.running.TestSessionController;
import uk.ac.ed.ph.jqtiplus.running.TestSessionControllerSettings;
import uk.ac.ed.ph.jqtiplus.state.TestPlan;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode.TestNodeType;
import uk.ac.ed.ph.jqtiplus.state.TestProcessingMap;
import uk.ac.ed.ph.jqtiplus.state.TestSessionState;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.types.ResponseData;
import uk.ac.ed.ph.jqtiplus.types.StringResponseData;
import uk.ac.ed.ph.jqtiplus.validation.TestValidationResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * (This was used for ad hoc test of test functionality. Not documented very well, but might
 * be useful enough to look at.)
 *
 * @author David McKain
 */
public final class AssessmentTestExample {

    public static void main(final String[] args) throws Exception {
        final ResourceLocator inputResourceLocator = new ClassPathResourceLocator();
        final URI inputUri = URI.create("classpath:/testimplementation/selection.xml");

        System.out.println("Reading and validating");
        final SimpleJqtiFacade simpleJqtiFacade = new SimpleJqtiFacade();
        final TestValidationResult testValidationResult = simpleJqtiFacade.loadResolveAndValidateTest(inputResourceLocator, inputUri);
        System.out.println("Validation result: " + ObjectDumper.dumpObject(testValidationResult, DumpMode.DEEP));

        final TestProcessingMap testProcessingMap = simpleJqtiFacade.buildTestProcessingMap(testValidationResult);
        System.out.println("Test processing map: " + ObjectDumper.dumpObject(testProcessingMap, DumpMode.DEEP));

        final TestPlanner testPlanner = simpleJqtiFacade.createTestPlanner(testProcessingMap);
        final NotificationLogListener notificationLogListener = new NotificationLogListener();
        testPlanner.addNotificationListener(notificationLogListener);
        final TestPlan testPlan = testPlanner.generateTestPlan();
        System.out.println("Test plan: " + ObjectDumper.dumpObject(testPlan, DumpMode.DEEP));
        System.out.println("Test plan structure:\n" + testPlan.debugStructure());

        final TestSessionState testSessionState = new TestSessionState(testPlan);
        final TestSessionControllerSettings testSessionControllerSettings = new TestSessionControllerSettings();
        final TestSessionController testSessionController = simpleJqtiFacade.createTestSessionController(testSessionControllerSettings, testProcessingMap, testSessionState);
        testSessionController.addNotificationListener(notificationLogListener);

        final Date timestamp = new Date();
        testSessionController.initialize(timestamp);
        testSessionController.enterTest(timestamp);
        testSessionController.enterNextAvailableTestPart(timestamp);
        System.out.println("Test state after entry into first test part: " + ObjectDumper.dumpObject(testSessionState, DumpMode.DEEP));

        final TestPlanNode firstItemRefNode = testPlan.getTestPartNodes().get(0).searchDescendants(TestNodeType.ASSESSMENT_ITEM_REF).get(0);
        testSessionController.selectItemNonlinear(timestamp, firstItemRefNode.getKey());
        System.out.println("First item is " + firstItemRefNode);

        final Map<Identifier, ResponseData> responseMap = new HashMap<Identifier, ResponseData>();
        responseMap.put(Identifier.parseString("RESPONSE"), new StringResponseData("ChoiceA"));
        testSessionController.handleResponsesToCurrentItem(timestamp, responseMap);

        System.out.println("Test state at end: " + ObjectDumper.dumpObject(testSessionState, DumpMode.DEEP));
    }
}
