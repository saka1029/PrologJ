package prolog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Term implements Unifiable {

    final List<Unifiable> elements = new ArrayList<>();

    public Term(Unifiable... elements) {
        for (Unifiable e : elements)
            this.elements.add(e);
    }

    public int size() {
        return elements.size();
    }

    public Unifiable get(int index) {
        return elements.get(index);
    }

    Unifiable map(Function<Unifiable, Unifiable> function) {
        Term term = new Term();
        for (Unifiable e : elements)
            term.elements.add(function.apply(e));
        return term;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        return ((Term)obj).elements.equals(elements);
    }

    @Override
    public boolean unify(Unifiable u, Binding binding) {
        if (u instanceof Constant)
            return false;
        else if (u instanceof Variable)
            return u.unify(this, binding);
        else { // if (u instanceof Term)
            Term term = (Term)u;
            if (term.size() != size())
                return false;
            for (int i = 0; i < size(); ++i)
                if (!get(i).instanciate(binding)
                    .unify(term.get(i).instanciate(binding), binding))
                    return false;
            return true;
        }
    }

    @Override
    public Unifiable instanciate(Binding binding) {
        return map(e -> e.instanciate(binding));
    }

    @Override
    public Unifiable instanciate(Variable v, Unifiable u) {
        return map(e -> e.instanciate(v, u));
    }

    @Override
    public boolean occurs(Variable v) {
        return elements.stream()
            .anyMatch(e -> e.occurs(v));
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
