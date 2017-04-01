package com.sachin.risk.common.data.idnumber;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdNumberResolverTest {

    @Test
    public void testResolve() throws Exception {

        System.out.println(IdNumberUtil.getValidateCode("37142419901207153"));

        IdNumberResolver resolver = new IdNumberResolver();
        IdNumberInfo idNumberInfo = resolver.resolve("371424");
        System.out.println(idNumberInfo);
    }
}