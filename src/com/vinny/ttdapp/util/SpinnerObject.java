package com.vinny.ttdapp.util;

import java.io.Serializable;

public class SpinnerObject implements Serializable{
	private int  id;
    private String value;

    public SpinnerObject ( int id , String value ) {
        this.id = id;
        this.value = value;
    }

    public int getId () {
        return id;
    }

    public String getValue () {
        return value;
    }

    @Override
    public String toString () {
        return value;
    }
}
