package ca.nait.dmit.domain;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({CircleTest.class, RectangleTest.class})
public class AllTests {

}
