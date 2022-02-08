package com.ssafy.a105.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckRes {
    long count;

    public static CheckRes of(long count) {
        CheckRes checkRes=new CheckRes();
        checkRes.count=count;
        return checkRes;
    }
}
