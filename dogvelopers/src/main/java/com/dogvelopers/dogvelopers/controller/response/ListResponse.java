package com.dogvelopers.dogvelopers.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListResponse<T> extends BasicResponse {
    private List<T> data;

    public ListResponse(List<T> data) {
        this.data = data;
    }
}
