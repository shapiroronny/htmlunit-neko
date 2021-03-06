package net.sourceforge.htmlunit.cyberneko;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.xerces.xni.parser.XMLInputSource;
import junit.framework.TestCase;

/**
 * Unit tests for {@link HTMLEntitiesParserGenerator}.
 * @author Ronald Brill
 */
public class HTMLEntitiesTest extends TestCase {

    public void testParseEuml() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "Euml ";
        int i = 0;
        while(parser.parse(input.charAt(i))) {
            i++;
        }
        assertEquals("\u00CB", parser.getMatch());
        assertEquals(1, parser.getRewindCount());
        assertFalse(parser.endsWithSemicolon());
    }

    public void testParseEuml_() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "Euml; ";
        int i = 0;
        while(parser.parse(input.charAt(i))) {
            i++;
        }
        assertEquals("\u00CB", parser.getMatch());
        assertEquals(0, parser.getRewindCount());
        assertTrue(parser.endsWithSemicolon());
    }

    public void testParseEumlX() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "EumlX";
        int i = 0;
        while(parser.parse(input.charAt(i))) {
            i++;
        }

		// valid without semicolon at end
        assertEquals("\u00CB", parser.getMatch());
        assertEquals(1, parser.getRewindCount());
        assertFalse(parser.endsWithSemicolon());
    }

    public void testParseEumX() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "EumX";
        int i = 0;
        while(parser.parse(input.charAt(i))) {
            i++;
        }
        assertNull(parser.getMatch());
        assertEquals(4, parser.getRewindCount());
        assertFalse(parser.endsWithSemicolon());
    }

    public void testParseEuroLt() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "euro<";
        int i = 0;
        while(parser.parse(input.charAt(i))) {
            i++;
        }

		// not valid without semicolon at end
        assertNull(parser.getMatch());
        assertEquals(5, parser.getRewindCount());
        assertFalse(parser.endsWithSemicolon());
    }

    public void testParseEuro() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "x80;";
        int i = 0;
        while(parser.parseNumeric(input.charAt(i))) {
            i++;
        }

        assertEquals("\u20ac", parser.getMatch());
        assertEquals(0, parser.getRewindCount());
    }

    public void testParseEuroMissingSemicolon() throws Exception {
        HTMLEntitiesParser parser = new HTMLEntitiesParser();

        String input = "x80<";
        int i = 0;
        while(parser.parseNumeric(input.charAt(i))) {
            i++;
        }

        assertEquals("\u20ac", parser.getMatch());
        assertEquals(1, parser.getRewindCount());
    }

    public void testRewind() throws Exception {
        HTMLConfiguration htmlConfiguration = new HTMLConfiguration();
        String content = "<html blah=\"" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfunfun" +
            "funfunfun&fin\"></html>";
        InputStream byteStream = new ByteArrayInputStream(content.getBytes());
        XMLInputSource inputSource = new XMLInputSource("", "", "", byteStream, "UTF-8");
        htmlConfiguration.parse(inputSource);
    }
}
