package com.corso.ProjectGLO.model;

import com.corso.ProjectGLO.exception.SpringRedditException;

import javax.persistence.Entity;
import java.util.Arrays;


public enum TipoVoto {
    UP_VOTE(1),
    DOWN_VOTE(-1);

    private int direction;

    private TipoVoto (int direction) {

    }
    public static TipoVoto lookup(Integer direction) {
        return Arrays.stream(TipoVoto.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
