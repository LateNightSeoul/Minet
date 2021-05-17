package com.Minet.Minet.domain.enumTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum Genre {
    HIPHOP, RNB, ROCK, POP, BALLAD;

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.toString().equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 genre값 입니다.");
    }
}
