package src.test.de.escidoc.sb.gsearch.xslt;

import static org.junit.Assert.*;

import org.junit.Test;

import dk.defxws.fedoragsearch.server.DataStreamCache;

public class TestDataStreamCache {
	
	DataStreamCache cache = DataStreamCache.getInstance();

	@Test
	public void test() {
		cache.put("escidoc:1111", 
				"/ir/item/escidoc:1111:4/components/component/escidoc:2060737/content", 
				"Da steht der Volltext");
		
		cache.put("escidoc:1111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content", 
				"Da steht ein anderer der Volltext");
		
		cache.put("escidoc:2111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content", 
				"Volltext von escidoc:2111");
		
		
		assertTrue(cache.size() == 3);
		
		String s = cache.get("escidoc:1111", 
				"/ir/item/escidoc:1111:4/components/component/escidoc:2060737/content"); 
		assertTrue(s.equals("Da steht der Volltext"));
		
		s = cache.get("escidoc:1111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content"); 
		assertTrue(s.equals("Da steht ein anderer der Volltext"));
		
		s = cache.get("escidoc:2111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content");
		assertTrue(s.equals("Volltext von escidoc:2111"));

		// not found
		assertFalse(cache.containsKey("xxx", "xxxxx"));
		
		s = cache.get("escidoc:2XXX", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content"); 
				
		assertTrue(s.equals(""));
		
		// UTF-8
		cache.put("escidoc:3111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content", 
				"Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ");
		
		s = cache.get("escidoc:3111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content");
				
		assertTrue(s.equals("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ"));
		
		// overwrite
		cache.put("escidoc:3111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content", 
				"a normal string");
		
		s = cache.get("escidoc:3111", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content");
				
		assertTrue(s.equals("a normal string"));
	}
	
	@Test
	public void testLatestReleaseCaching() {
 		cache.put("escidoc:4000:LR", 
				"/ir/item/escidoc:1111/components/component/escidoc:2060737/content", 
				"a normal string");
		
		assertTrue(cache.containsKey("escidoc:4000", "/ir/item/escidoc:1111/components/component/escidoc:2060737/content"));
		assertTrue(cache.containsKey("escidoc:4000:LR", "/ir/item/escidoc:1111/components/component/escidoc:2060737/content"));
		
		String s1 = cache.get("escidoc:4000", "/ir/item/escidoc:1111/components/component/escidoc:2060737/content");
		String s2 = cache.get("escidoc:4000:LR", "/ir/item/escidoc:1111/components/component/escidoc:2060737/content");
		
		assertTrue(s1.equals(s2));
		
	}

}
