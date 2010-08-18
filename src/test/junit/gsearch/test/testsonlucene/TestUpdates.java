//$Id:  $
package gsearch.test.testsonlucene;

import junit.framework.TestSuite;

import org.junit.Test;

import fedora.client.FedoraClient;
import fedora.server.management.FedoraAPIM;
import gsearch.test.FgsTestCase;

/**
 * Tests on lucene plugin
 * 
 * assuming 
 * - all Fedora demo objects are in the repository referenced in
 *   configTestOnLucene/repository/DemoAtDtu/repository.properties
 * 
 * the test suite will
 * - set configTestOnLucene as current config. 
 */
public class TestUpdates
        extends FgsTestCase {

    // Supports legacy test runners
    public static junit.framework.Test suite() {
        TestSuite suite = new TestSuite("configDemoOnLucene TestSuite");
        suite.addTestSuite(TestUpdates.class);
        return new TestConfigSetup(suite);
    }

    @Test
    public void testUpdateIndexDocCount() throws Exception {
  	    StringBuffer result = doOp("?operation=updateIndex&restXslt=copyXml");
  	    assertXpathEvaluatesTo("25", "/resultPage/updateIndex/@docCount", result.toString());
    }

    @Test
    public void testUpdateIndexDeletePid() throws Exception {
  	    StringBuffer result = doOp("?operation=updateIndex&action=deletePid&value=demo:10&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/updateIndex/@deleteTotal", result.toString());
    }

    @Test
    public void testUpdateIndexInsertPid() throws Exception {
  	    StringBuffer result = doOp("?operation=updateIndex&action=fromPid&value=demo:10&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/updateIndex/@insertTotal", result.toString());
    }

    @Test
    public void testUpdateIndexFromPid() throws Exception {
  	    StringBuffer result = doOp("?operation=updateIndex&action=fromPid&value=demo:10&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/updateIndex/@updateTotal", result.toString());
    }

    @Test
    public void testExplicitParamNotFound() throws Exception {
  	    StringBuffer result = doOp("?operation=gfindObjects&query=EXPLICITPARAM1:explicitvalue1+and+EXPLICITPARAM2:explicitvalue2&restXslt=copyXml");
  	    assertXpathEvaluatesTo("0", "/resultPage/gfindObjects/@hitTotal", result.toString());
    }

    @Test
    public void testUpdateIndexFromPidWithParam() throws Exception {
  	    StringBuffer result = doOp("?operation=updateIndex&action=fromPid&value=demo:10&indexDocXslt=testFoxmlToLuceneWithExplicitParams(EXPLICITPARAM1=explicitvalue1,EXPLICITPARAM2=explicitvalue2)&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/updateIndex/@updateTotal", result.toString());
    }

    @Test
    public void testExplicitParamFound() throws Exception {
  	    StringBuffer result = doOp("?operation=gfindObjects&query=EXPLICITPARAM1:explicitvalue1+and+EXPLICITPARAM2:explicitvalue2&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/gfindObjects/@hitTotal", result.toString());
    }

    @Test
    public void testFedoraObjectLabelModifyPre() throws Exception {
  	    StringBuffer result = doOp("?operation=gfindObjects&query=fgs.label:labelmodifiedforindextest&restXslt=copyXml");
  	    assertXpathEvaluatesTo("0", "/resultPage/gfindObjects/@hitTotal", result.toString());
    }

    @Test
    public void testFedoraObjectLabelModify() throws Exception {
    	FedoraClient fedoraClient = new FedoraClient("http://localhost:8080/fedora", "fedoraAdmin", "fedoraAdmin");
    	FedoraAPIM apim = fedoraClient.getAPIM();
    	apim.modifyObject("demo:11", null, "labelmodifiedforindextest", "fedoraAdmin", "test label modify");
  	    delay(5000);
  	    StringBuffer result = doOp("?operation=gfindObjects&query=fgs.label:labelmodifiedforindextest&restXslt=copyXml");
  	    assertXpathEvaluatesTo("1", "/resultPage/gfindObjects/@hitTotal", result.toString());
    	apim.modifyObject("demo:11", null, "labelremodifiedforindextest", "fedoraAdmin", "test label modify");
    }

// The next three tests are commented, because it is not clear how to treat the object states.
// Applications may decide in the indexing stylesheet.
    
//    @Test
//    public void testFedoraObjectStateModifyPre() throws Exception {
//  	    delay(5000);
//  	    StringBuffer result = doOp("?operation=updateIndex&restXslt=copyXml");
//  	    assertXpathEvaluatesTo("25", "/resultPage/updateIndex/@docCount", result.toString());
//    }
//
//    @Test
//    public void testFedoraObjectStateDelete() throws Exception {
//    	FedoraClient fedoraClient = new FedoraClient("http://localhost:8080/fedora", "fedoraAdmin", "fedoraAdmin");
//    	FedoraAPIM apim = fedoraClient.getAPIM();
//    	apim.modifyObject("demo:21", "D", null, "fedoraAdmin", "test state delete");
//  	    delay(10000);
//  	    StringBuffer result = doOp("?operation=updateIndex&restXslt=copyXml");
//  	    assertXpathEvaluatesTo("24", "/resultPage/updateIndex/@docCount", result.toString());
//    }
//
//    @Test
//    public void testFedoraObjectStateActivate() throws Exception {
//    	FedoraClient fedoraClient = new FedoraClient("http://localhost:8080/fedora", "fedoraAdmin", "fedoraAdmin");
//    	FedoraAPIM apim = fedoraClient.getAPIM();
//    	apim.modifyObject("demo:21", "A", null, "fedoraAdmin", "test state activate");
//  	    delay(10000);
//  	    StringBuffer result = doOp("?operation=updateIndex&restXslt=copyXml");
//  	    assertXpathEvaluatesTo("25", "/resultPage/updateIndex/@docCount", result.toString());
//    }
}
