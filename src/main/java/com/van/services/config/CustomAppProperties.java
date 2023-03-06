package com.van.services.config;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Properties are configured in the application.yml file. </p>
 * <p> This class also load properties in the Spring Environment from the git.properties and META-INF/build-info.properties
 * files if they are found in the classpath.</p>
 */
@ConfigurationProperties(prefix = "custom")
@Getter
@Builder
@AllArgsConstructor
@Slf4j
public class CustomAppProperties {
    private final Http http;
    private final Cache cache;
    private final Security security;
    private final CorsConfigurationExt cors;

    public CustomAppProperties() {
        log.debug("CustomAppProperties start");
        this.http = new Http();
        this.cache = new Cache();
        this.security = new Security();
        this.cors = new CorsConfigurationExt();
        log.debug("CustomAppProperties done");
    }

    @Getter
    @Setter
    public static class CorsConfigurationExt extends CorsConfiguration {
        List<String> endPoints;
    }

    public static class Http {
        @Getter
        private final Cache cache = new Cache();

        @Data
        public static class Cache {
            private int timeToLiveInDays = CustomAppPropertiesDefaults.Http.Cache.timeToLiveInDays;
        }
    }

    @Getter
    public static class Cache {
        private final Hazelcast hazelcast = new Hazelcast();
        private final Caffeine caffeine = new Caffeine();
        private final Ehcache ehcache = new Ehcache();
        private final Infinispan infinispan = new Infinispan();
        private final Memcached memcached = new Memcached();
        private final Redis redis = new Redis();

        @Data
        public static class Hazelcast {
            private int timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Hazelcast.timeToLiveSeconds;
            private int backupCount = CustomAppPropertiesDefaults.Cache.Hazelcast.backupCount;
        }

        @Data
        public static class Caffeine {
            private int timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Caffeine.timeToLiveSeconds;
            private long maxEntries = CustomAppPropertiesDefaults.Cache.Caffeine.maxEntries;
        }

        @Data
        public static class Ehcache {
            private int timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Ehcache.timeToLiveSeconds;
            private long maxEntries = CustomAppPropertiesDefaults.Cache.Ehcache.maxEntries;
        }

        @Data
        public static class Infinispan {
            private String configFile = CustomAppPropertiesDefaults.Cache.Infinispan.configFile;
            private boolean statsEnabled = CustomAppPropertiesDefaults.Cache.Infinispan.statsEnabled;
            private final Local local = new Local();
            private final Distributed distributed = new Distributed();
            private final Replicated replicated = new Replicated();

            @Data
            public static class Local {
                private long timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
                private long maxEntries = CustomAppPropertiesDefaults.Cache.Infinispan.Local.maxEntries;
            }

            @Data
            public static class Distributed {
                private long timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
                private long maxEntries = CustomAppPropertiesDefaults.Cache.Infinispan.Distributed.maxEntries;
                private int instanceCount = CustomAppPropertiesDefaults.Cache.Infinispan.Distributed.instanceCount;
            }

            @Data
            public static class Replicated {
                private long timeToLiveSeconds = CustomAppPropertiesDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
                private long maxEntries = CustomAppPropertiesDefaults.Cache.Infinispan.Replicated.maxEntries;
            }
        }

        @Data
        public static class Memcached {
            private boolean enabled = CustomAppPropertiesDefaults.Cache.Memcached.enabled;

            /**
             * Comma or whitespace separated list of servers' addresses.
             */
            private String servers = CustomAppPropertiesDefaults.Cache.Memcached.servers;
            private int expiration = CustomAppPropertiesDefaults.Cache.Memcached.expiration;
            private boolean useBinaryProtocol = CustomAppPropertiesDefaults.Cache.Memcached.useBinaryProtocol;
            private Authentication authentication = new Authentication();

            @Data
            public static class Authentication {
                private boolean enabled = CustomAppPropertiesDefaults.Cache.Memcached.Authentication.enabled;
                private String username;
                private String password;
            }
        }

        @Data
        public static class Redis {
            private String[] server = CustomAppPropertiesDefaults.Cache.Redis.server;
            private int expiration = CustomAppPropertiesDefaults.Cache.Redis.expiration;
            private boolean cluster = CustomAppPropertiesDefaults.Cache.Redis.cluster;
            private int connectionPoolSize = CustomAppPropertiesDefaults.Cache.Redis.connectionPoolSize;
            private int connectionMinimumIdleSize = CustomAppPropertiesDefaults.Cache.Redis.connectionMinimumIdleSize;
            private int subscriptionConnectionPoolSize = CustomAppPropertiesDefaults.Cache.Redis.subscriptionConnectionPoolSize;
            private int subscriptionConnectionMinimumIdleSize = CustomAppPropertiesDefaults.Cache.Redis.subscriptionConnectionMinimumIdleSize;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Security {
        private String contentSecurityPolicy;
        private final ClientAuthorization clientAuthorization;
        private final Authentication authentication;
        private final RememberMe rememberMe;
        private final OAuth2 oauth2;

        public Security() {
            this.contentSecurityPolicy = CustomAppPropertiesDefaults.Security.contentSecurityPolicy;
            this.clientAuthorization = new ClientAuthorization();
            this.authentication = new Authentication();
            this.rememberMe = new RememberMe();
            this.oauth2 = new OAuth2();
        }

        @Data
        public static class ClientAuthorization {
            private String accessTokenUri = CustomAppPropertiesDefaults.Security.ClientAuthorization.accessTokenUri;
            private String tokenServiceId = CustomAppPropertiesDefaults.Security.ClientAuthorization.tokenServiceId;
            private String clientId = CustomAppPropertiesDefaults.Security.ClientAuthorization.clientId;
            private String clientSecret = CustomAppPropertiesDefaults.Security.ClientAuthorization.clientSecret;
        }

        @Data
        @Builder
        @AllArgsConstructor
        public static class Authentication {
            private final Jwt jwt;

            public Authentication() {
                this.jwt = new Jwt();
            }

            @Data
            @Builder
            @AllArgsConstructor
            public static class Jwt {
                private String secret;
                private String base64Secret;
                @Builder.Default
                private long tokenValidityInSeconds = CustomAppPropertiesDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
                @Builder.Default
                private long tokenValidityInSecondsForRememberMe = CustomAppPropertiesDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
                private String keyStoryPath;
                private String keyStoryPassword;
                @Builder.Default
                private String keyStoryLocalAlias = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryIdpAlias;
                @Builder.Default
                private String keyStoryRemoteAlias = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryClientAlias;
                @Builder.Default
                private String issuer = CustomAppPropertiesDefaults.Security.Authentication.Jwt.issuer;

                public Jwt() {
                    this.secret = CustomAppPropertiesDefaults.Security.Authentication.Jwt.secret;
                    this.base64Secret = CustomAppPropertiesDefaults.Security.Authentication.Jwt.base64Secret;
                    this.keyStoryPath = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryPath;
                    this.keyStoryPassword = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryPassword;
                    this.keyStoryLocalAlias = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryIdpAlias;
                    this.keyStoryRemoteAlias = CustomAppPropertiesDefaults.Security.Authentication.Jwt.keyStoryClientAlias;
                    this.issuer = CustomAppPropertiesDefaults.Security.Authentication.Jwt.issuer;
                }
            }
        }

        @Data
        public static class RememberMe {
            private String key = CustomAppPropertiesDefaults.Security.RememberMe.key;
        }

        @Data
        public static class OAuth2 {
            @Singular
            private List<String> audience = new ArrayList<>();
        }
    }

}
