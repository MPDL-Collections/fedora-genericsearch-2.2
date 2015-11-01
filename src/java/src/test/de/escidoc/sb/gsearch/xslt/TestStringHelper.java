package src.test.de.escidoc.sb.gsearch.xslt;

import static org.junit.Assert.*;

import java.text.Normalizer;

import org.junit.Ignore;
import org.junit.Test;

import de.escidoc.sb.gsearch.xslt.StringHelper;

public class TestStringHelper {

	@Test
	public void testNormalizer() {

		System.out.println("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ " +
		    Normalizer.normalize("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ ".toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		
		System.out.println("Čadík " + 
			    Normalizer.normalize("Čadík".toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")); 
		
		System.out.println("本 " +  
			    Normalizer.normalize("本".toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")); 
		
		System.out.println("登録 " + 
			    Normalizer.normalize("登録".toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")); 
		
		System.out.println("Abcd? Def " + 
			    Normalizer.normalize("Abcd? Def".toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")); 
	}
	
	@Test
	public void testStringHelperNormalize() {
		String output = StringHelper.getNormalizedString("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ");
		System.out.println("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ <" + output + ">");
		assertTrue(output.equals("this is a funky string"));
		
		output = StringHelper.getNormalizedString("Čadík");
		System.out.println("Čadík <" + output + ">"); 
		assertTrue(output.equals("cadik"));
		
		output = StringHelper.getNormalizedString("本");
		System.out.println("本 <" + output + ">");  
		assertTrue(output.equals("本"));
		
		output = StringHelper.getNormalizedString("登録");
		System.out.println("登録 <" + output + ">"); 
		assertTrue(output.equals("登録"));
		
		output = StringHelper.getNormalizedString("Ё");
		System.out.println("Ё <" + output + ">"); 
		assertTrue(output.equals("ё"));
		
		output = StringHelper.getNormalizedString("ß");
		System.out.println("ß <" + output + ">"); 
		assertTrue(output.equals("ss"));
		
		output = StringHelper.getNormalizedString("æ");
		System.out.println("æ <" + output + ">"); 
		assertTrue(output.equals("ae"));
		
		output = StringHelper.getNormalizedString("ø");
		System.out.println("ø <" + output + ">"); 
		assertTrue(output.equals("o"));
		
		output = StringHelper.getNormalizedString("ł");
		System.out.println("ł <" + output + ">"); 
		assertTrue(output.equals("l"));
		
		output = StringHelper.getNormalizedString("AÖÜäöü");
		System.out.println("AÖÜäöü <" + output + ">"); 
		assertTrue(output.equals("aouaou"));
		
		output = StringHelper.getNormalizedString("Kılıç");
		System.out.println("Kılıç <" + output + ">"); 
		assertTrue(output.equals("kilic"));
		
		output = StringHelper.getNormalizedString("Abcd? Def");
		System.out.println("Abcd? Def <" + output + ">"); 
		assertTrue(output.equals("abcd? def"));
	}
}
