package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.repository.HofRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HofService {

    private final HofRepository hofRepository;
}
