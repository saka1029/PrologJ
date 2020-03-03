package test.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

import prolog.Constant;
import prolog.Term;
import prolog.Variable;

public class TestTerm {

    Variable X = new Variable("X");
    Constant a = Constant.of("a");
    Constant b = Constant.of("b");

    @Test
    public void testToString() {
        assertEquals(Arrays.asList(X, a).toString(), new Term(X, a).toString());
    }

    @Test
    public void testToEqualsSuccess0() {
        assertEquals(new Term(X, a), new Term(X, a));
    }

    @Test
    public void testToEqualsSuccess1() {
        Term t = new Term(X, a);
        assertEquals(t, t);
    }

    @Test
    public void testToEqualsFail0() {
        assertFalse(new Term(X, a).equals(null));
    }

    @Test
    public void testToEqualsFail1() {
        assertFalse(new Term(X, a).equals("Xa"));
    }

    @Test
    public void testToEqualsFail2() {
        assertFalse(new Term(X, a).equals(new Term(X)));
    }

}
