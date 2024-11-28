package org.example.services;

import org.example.stereotypes.Autowired;
import org.example.stereotypes.Injectable;

@Injectable
public class CustomerService {

    @Autowired
    private NestedLevel1 nestedService1;

    public String hello() {
        return this.nestedService1.nested();
    }
}