package es.uvigo.esei.daa.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.entities.EventUnitTest;
import es.uvigo.esei.daa.entities.UserUnitTest;


@SuiteClasses({
	EventUnitTest.class,
	UserUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
