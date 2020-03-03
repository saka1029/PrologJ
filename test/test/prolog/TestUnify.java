package test.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import prolog.Binding;
import prolog.Constant;
import prolog.Term;
import prolog.Unifiable;
import prolog.Variable;

public class TestUnify {

    static Constant con(String name) {
        return Constant.of(name);
    }

    static Term term(Unifiable... us) {
        return new Term(us);
    }

    static Variable var(String name) {
        return new Variable(name);
    }

    static Binding unify(Unifiable a, Unifiable b) {
        Binding binding = new Binding();
        return a.unify(b, binding) ? binding : null;
    }

    static Unifiable get(Variable v, Binding binding) {
        return binding.get(v);
    }

    Constant a = con("a");
    Constant b = con("b");
    Constant c = con("c");
    Variable X = var("X");
    Variable Y = var("Y");
    Variable Z = var("Z");

    @Test
    public void testConstantToString() {
        assertEquals("a", a.toString());
    }

    @Test
    public void testVariableToString() {
        assertEquals("X", X.toString());
    }

    @Test
    public void testConstantConstantSuccess() {
        Binding g = unify(a, a);
        assertEquals(0, g.size());
    }

    @Test
    public void testConstantConstantFail() {
        Binding g = unify(a, b);
        assertNull(g);
    }

    @Test
    public void testConstantVariableSuccess() {
        Binding g = unify(a, X);
        assertEquals(1, g.size());
        assertEquals(a, get(X, g));
        assertEquals("{X=a}", g.toString());
    }

    @Test
    public void testConstantVariableFail() {
        Binding g = unify(term(a, b), term(X, X));
        assertNull(g);
    }

    @Test
    public void testConstantTerm() {
        Binding g = unify(a, term(a, X));
        assertNull(g);
    }

    @Test
    public void testVariableConstantSuccess() {
        Binding g = unify(X, a);
        assertEquals(1, g.size());
        assertEquals(a, get(X, g));
    }

    @Test
    public void testVariableConstantFail() {
        Binding g = unify(term(X, X), term(a, b));
        assertNull(g);
    }

    @Test
    public void testVariableVariableSuccess() {
        Binding g = unify(X, Y);
        assertEquals(1, g.size());
        assertEquals(Y, get(X, g));
    }

    @Test
    public void testVariableVariableVariableSuccess() {
        Binding g = unify(term(X, Y, Z), term(Y, Z, a));
        assertEquals(3, g.size());
        assertEquals(a, get(X, g));
        assertEquals(a, get(Y, g));
        assertEquals(a, get(Z, g));
    }

    @Test
    public void testVariableVariableVariableSuccess2() {
        Binding g = unify(term(X, Y, Z), term(a, X, Y));
        assertEquals(3, g.size());
        assertEquals(a, get(X, g));
        assertEquals(a, get(Y, g));
        assertEquals(a, get(Z, g));
    }

    @Test
    public void testVariableVariableVariableSuccess3() {
        Binding g = unify(term(X, Y, Z), term(Y, a, Y));
        assertEquals(3, g.size());
        assertEquals(a, get(X, g));
        assertEquals(a, get(Y, g));
        assertEquals(a, get(Z, g));
    }

    @Test
    public void testVariableVariableFail() {
        Binding g = unify(term(a, X), term(X, b));
        assertNull(g);
    }

    @Test
    public void testVariableOccursFail() {
        Binding g = unify(X, X);
        assertNull(g);
    }

    @Test
    public void testVariableIndirectOccursFail() {
        Binding g = unify(term(X, Y), term(Y, X));
        assertNull(g);
    }

    @Test
    public void testVariableTermSuccess() {
        Binding g = unify(X, term(a, b));
        assertEquals(1, g.size());
        assertEquals(term(a, b), get(X, g));
    }

    @Test
    public void testTermConstantFail() {
        Binding g = unify(term(a), a);
        assertNull(g);
    }

    @Test
    public void testTermVariableSuccess() {
        Binding g = unify(term(a), X);
        assertEquals(1, g.size());
        assertEquals(term(a), get(X, g));
    }

    @Test
    public void testTermVariableFail() {
        Binding g = unify(term(X), X);
        assertNull(g);
    }

    @Test
    public void testTermTermSuccess() {
        Binding g = unify(term(a, b), term(a, b));
        assertEquals(0, g.size());
    }

    @Test
    public void testTermTermLengthFail() {
        Binding g = unify(term(a), term(a, b));
        assertNull(g);
    }

    @Test
    public void testTermTermFail() {
        Binding g = unify(term(a, b), term(a, a));
        assertNull(g);
    }

    @Test
    public void testTermTermVariable() {
        Binding g = unify(term(X, Y), term(Z, b));
        assertEquals(2, g.size());
        assertEquals(Z, get(X, g));
        assertEquals(b, get(Y, g));
    }

    @Test
    public void testTermTerm() {
        Binding g = unify(term(X, term(a)), term(a, term(a)));
        assertEquals(1, g.size());
        assertEquals(a, get(X, g));
    }

    @Test
    public void testTermTerm2() {
        Binding g = unify(term(X, Y), term(term(a), a));
        assertEquals(2, g.size());
        assertEquals(term(a), get(X, g));
        assertEquals(a, get(Y, g));
    }

}
