package com.sachin.risk.common.data.mobile;

import org.junit.Test;

import static org.junit.Assert.*;

public class MobileResolverTest {

    @Test
    public void testResolve() throws Exception {
        MobileResolver resolver = new MobileResolver();
        MobileInfo mobileInfo = resolver.resolve("1785527");
        System.out.println(mobileInfo);
    }
}