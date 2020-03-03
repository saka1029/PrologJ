package prolog;

public interface Unifiable {

    boolean unify(Unifiable u, Binding binding);
    Unifiable instanciate(Binding binding);
    Unifiable instanciate(Variable v, Unifiable u);
    boolean occurs(Variable v);
}
