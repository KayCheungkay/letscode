/**
 * Copyright (C) 2006-2015 tntrip All rights reserved
 */
package com.tntrip.mob.askq.biz.pojo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoJoConfig1 extends AbstractPoJoConfig {
    @Bean(name = "pojo1")
    public Pojo createPojo() {
        return super.createPojo();
    }
}
