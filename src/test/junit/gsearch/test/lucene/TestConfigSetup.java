//$Id:  $
package gsearch.test.lucene;

import junit.extensions.TestSetup;
import junit.framework.Test;

/**
 * Test of lucene plugin
 * 
 * the test suite will
 * - set configDemoOnLucene as current config. 
 */
public class TestConfigSetup
        extends junit.extensions.TestSetup {

    public TestConfigSetup(Test test) {
        super(test);
    }

    @Override
    public void setUp() throws Exception {
        System.out.println("setUp configDemoOnLucene");
        System.setProperty("fedoragsearch.fgsUserName", "fedoraAdmin");
        System.setProperty("fedoragsearch.fgsPassword", "fedoraAdmin");
    }

    @Override
    public void tearDown() throws Exception {
//        System.out.println("tearDown configDemoOnLucene");
    }
}
