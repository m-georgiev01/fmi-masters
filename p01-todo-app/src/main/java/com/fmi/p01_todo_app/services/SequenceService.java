package com.fmi.p01_todo_app.services;

import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    private int sequenceId = 1;

    public int GetNextId(){
        return sequenceId++;
    }
}
