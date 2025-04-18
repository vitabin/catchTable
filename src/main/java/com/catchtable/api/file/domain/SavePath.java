package com.catchtable.api.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SavePath {
    COUPON("coupon"),
    ;

    private final String path;
}
