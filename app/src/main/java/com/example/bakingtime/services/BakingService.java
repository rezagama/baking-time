package com.example.bakingtime.services;

import com.example.bakingtime.network.NetworkService;

/**
 * Created by rezagama on 9/3/17.
 */

public class BakingService {
    private NetworkService service;

    public BakingService(NetworkService service) {
        this.service = service;
    }
}
