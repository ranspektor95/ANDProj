package com.ranspektor.andproj.models;

import java.util.LinkedList;
import java.util.List;

public class Model {
    static public final Model instance = new Model();

    private Model() {

    }

    public List<Entry> getAllEntries() {
        List<Entry> list = new LinkedList<>();

        Entry r = new Entry();
        r.title = "title";

        Entry rr = new Entry();
        rr.title = "titlinggg";

        list.add(r);
        list.add(rr);

        return list;
    }

    Entry getEntry(String id) {
        return null;
    }

}
