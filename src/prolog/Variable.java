package prolog;

public class Variable implements Unifiable {

    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean unify(Unifiable u, Binding binding) {
        // x.unify(x, b) は true とする場合
        // （ [x, y].unify([y, x], b) も true となる）
//        if (equals(u))
//            return true;
        if (u.occurs(this))
            return false;
        else {
            binding.put(this, u);
            return true;
        }
    }

    @Override
    public Unifiable instanciate(Binding binding) {
        Unifiable u = binding.get(this);
        return u == null ? this : u;
    }

    @Override
    public Unifiable instanciate(Variable v, Unifiable u) {
        return equals(v) ? u : this;
    }

    @Override
    public boolean occurs(Variable v) {
        return v.equals(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
