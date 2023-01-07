package io.techwings.compilers.fsm;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(HierarchicalContextRunner.class)
public class LexerTest implements TokenCollector {
    String tokens = "";
    Lexer lexer;
    private boolean firstToken = true;

    @Before
    public void setUp() {
        lexer = new Lexer(this);
    }

    private void addToken(String token) {
        if (!firstToken)
            tokens += ",";
        tokens += token;
        firstToken = false;
    }

    private void assertLexResult(String input, String expected) {
        lexer.lex(input);
        assertEquals(expected, tokens);
    }
    @Override
    public void openBrace(int line, int pos) {
        addToken("OB");
    }

    @Override
    public void closedBrace(int line, int pos) {
        addToken("CB");
    }

    @Override
    public void openParen(int line, int pos) {
        addToken("OP");
    }

    @Override
    public void closedParen(int line, int pos) {
        addToken("CP");
    }

    @Override
    public void openAngle(int line, int pos) {
        addToken("OA");
    }

    @Override
    public void closedAngle(int line, int pos) {
        addToken("CA");
    }

    @Override
    public void dash(int line, int pos) {
        addToken("D");
    }

    @Override
    public void colon(int line, int pos) {
        addToken("C");
    }

    @Override
    public void name(String name, int line, int pos) {
        addToken("#" + name + "#");
    }

    @Override
    public void error(int line, int pos) {
        addToken("E" + line + "/" + pos);
    }

    public class SingleTokenTests {
        @Test
        public void findsOpenBrace() {
            assertLexResult("{", "OB");
        }

        @Test
        public void findClosedBrace() {
            assertLexResult("}", "CB");
        }

        @Test
        public void findOpenParen() {
            assertLexResult("(", "OP");
        }

        @Test
        public void findClosedParen() {
            assertLexResult(")", "CP");
        }

        @Test
        public void findOpenAngle() {
            assertLexResult("<", "OA");
        }

        @Test
        public void findClosedAngle() {
            assertLexResult(">", "CA");
        }

        @Test
        public void findDash() {
            assertLexResult("-", "D");
        }

        @Test
        public void findColon() {
            assertLexResult(":", "C");
        }

        @Test
        public void findSimpleName() {
            assertLexResult("name", "#name#");
        }

        @Test
        public void findComplexName() {
            assertLexResult("Room_222", "#Room_222#");
        }

        @Test
        public void findError() {
            assertLexResult(".", "E1/1");
        }

        @Test
        public void nothingButWhiteSpace() {
            assertLexResult(" ", "");
        }

        @Test
        public void whiteSpaceBefore() {
            assertLexResult("   \t\n    -", "D");
        }

    }

    public class MultipleTokenTests {
        @Test
        public void simpleSequence() {
            assertLexResult("{}", "OB,CB");
        }

        @Test
        public void complexSequence() {
            assertLexResult("FSM:fsm{this}", "#FSM#,C,#fsm#,OB,#this#,CB");
        }

        @Test
        public void allTokens() {
            assertLexResult("{}()<>-: name .", "OB,CB,OP,CP,OA,CA,D,C,#name#,E1/15");
        }

        @Test
        public void multipleLines() {
            assertLexResult("FSM:fsm.\n{bob-.}", "#FSM#,C,#fsm#,E1/8,OB,#bob#,D,E2/6,CB");
        }
    }
}
