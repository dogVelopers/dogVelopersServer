package com.dogvelopers.dogvelopers.dto.hof;

import com.dogvelopers.dogvelopers.entity.Hof;

import java.util.Comparator;

public class SortByGeneration implements Comparator<Hof> {

    // Hof 객체를 joinDate를 기준으로 정렬시키기 위한 객체
    @Override
    public int compare(Hof hof1 , Hof hof2){
        return -1 * (hof1.getMember().getGeneration().compareTo(hof2.getMember().getGeneration()));
    }
}
