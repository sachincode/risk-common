package com.sachin.risk.common.data.mobile;

import org.junit.Test;

import static org.junit.Assert.*;

public class MobileResolverTest {

    @Test
    public void testResolve() throws Exception {
        MobileResolver resolver = new MobileResolver();
        MobileInfo mobileInfo = resolver.resolve("1731476");
        System.out.println(mobileInfo);
    }
}