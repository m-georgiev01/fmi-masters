package org.example.services;

import org.example.stereotypes.Autowired;
import org.example.stereotypes.Injectable;

@Injectable
public class NestedLevel1 {
    @Autowired
    private NestedLevel2 nestedLevel2;

    public String nested() {
        return this.nestedLevel2.nested();
    }
}
