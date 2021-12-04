package org.closure.course.services;

import org.springframework.stereotype.Service;

@Service    
public class TestService {

    public int sum(int a,int b) throws Exception{
        if(a == 5) throw new Exception();
        return a+b;
    }
    
}
