package javaCode.view;

import javaCode.data.Objet;
import javaCode.data.Section;

import java.util.List;

public class Result3 {
    private Section first;
    private Section snd;
    private List<Objet> trd;

    public Result3(Section first, Section snd, List<Objet> trd) {
        this.first = first;
        this.snd = snd;
        this.trd = trd;
    }

    public Section getFirst() {
        return first;
    }

    public Section getSnd() {
        return snd;
    }

    public List<Objet> getTrd() {
        return trd;
    }
}
