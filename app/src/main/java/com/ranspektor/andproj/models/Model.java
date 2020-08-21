package com.ranspektor.andproj.models;

import java.util.LinkedList;
import java.util.List;

public class Model {
    static public final Model instance = new Model();

    private Model(){

    }

    public List<Request> getAllRequests(){
        List list = new LinkedList<Request>();

        Request r = new Request();
        r.title = "title";

        Request rr = new Request();
        rr.title = "titlinggg";

        list.add(r);
        list.add(rr);

        return list;
    }

    Request getRequest(String id){
        return null;
    }

}
