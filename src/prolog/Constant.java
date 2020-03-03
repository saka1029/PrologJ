package prolog;

import java.util.HashMap;
import java.util.Map;

public class Constant implements Unifiable {

    static final Map<String, Constant> map = new HashMap<>();

    final String name;

    private Constant(String name) {
        this.name = name;
    }

    public static Constant of(String name) {
        return map.computeIfAbsent(name, key -> new Constant(key));
    }

    @Override
    public boolean unify(Unifiable u, Binding binding) {
        if (u instanceof Constant)
            return equals(u);
        else if (u instanceof Variable)
            return u.unify(this, binding);
        else // if (u instanceof Term)
            return false;
    }

    @Override
    public Unifiable instanciate(Binding binding) {
        return this;
    }

    @Override
    public Unifiable instanciate(Variable v, Unifiable u) {
        return this;
    }

    @Override
    public boolean occurs(Variable v) {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

}
