package com.altoros.hip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.hip.sdk.api.Api;
import se.hip.sdk.bootstrap.Bootstrap;
import se.hip.sdk.config.Profiles;

/**
 * @author Ilya Drabenia
 */
@Configuration
public class HipSdkConfiguration {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public Api api() {
        final Bootstrap bs = new Bootstrap.Builder()
            .withProfile(Profiles.SDK_DEVELOPMENT)
            .withParentContext(ctx)
            .build();
        return bs.getApi();
    }
}