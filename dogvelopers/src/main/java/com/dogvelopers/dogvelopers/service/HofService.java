package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.repository.HofRepository;
import org.springframework.stereotype.Service;

@Service
public class HofService {

    private HofRepository hofRepository;

    public HofService(HofRepository hofRepository){
        this.hofRepository = hofRepository;
    }
}
