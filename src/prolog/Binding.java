package prolog;

import java.util.HashMap;
import java.util.Map;

public class Binding {

    final Map<Variable, Unifiable> map = new HashMap<>();

    public Unifiable get(Variable v) {
        return map.get(v);
    }

    public Binding put(Variable v, Unifiable u) {
        map.replaceAll((a, b) -> b.instanciate(v, u));
        // 本では以下のようになっているが、常にnを返すものと思われる。
        // binding = {X=Y} に対して put(Y, X) が呼ばれる場合
        // n = X となる。ただし
        // term(X, Y).unify(term(Y, X), new Binding()) では
        // termの第2項がputされる前にY.instanciate(binding)が実行され、
        // Y.unify(Y) の呼び出しとなってoccursチェックでfailする。
        // map.replaceAll((a, b) -> {
        //     Unifiable n = b.instanciate(v, u);
        //     return a.equals(n) ? b : n;
        // });
        map.computeIfAbsent(v, k -> u);
        return this;
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
