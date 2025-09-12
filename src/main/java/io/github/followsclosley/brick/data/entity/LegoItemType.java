package io.github.followsclosley.brick.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LegoItemType {
    SET("S"),
    PART("P"),
    MINIFIGURE("M"),
    BOOK("B"),
    GEAR("G"),
    CATALOG("C"),
    INSTRUCTION("I"),
    ORIGINAL_BOX("O"),
    UNSORTED_LOT("U");

    @Getter
    private final String code;
}

