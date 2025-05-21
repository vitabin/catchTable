package com.catchtable.response.implement;

import com.catchtable.response.interfaces.ResponseMetaData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseMetaDataImpl implements ResponseMetaData {

    private final Boolean hasNext;
    private final Integer items;
    private final Integer nextCursor;

}
