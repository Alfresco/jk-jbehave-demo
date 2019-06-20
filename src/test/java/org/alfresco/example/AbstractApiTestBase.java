package org.alfresco.example;

import static org.junit.Assert.assertNotNull;

import org.alfresco.rest.authn.AuthnConfigBuilder;
import org.alfresco.rest.authn.TokenProvider;
import org.alfresco.rest.config.ApiClient;
import org.alfresco.rest.config.auth.OAuth;
import org.alfresco.rest.core.handler.NodesApi;

import org.alfresco.example.util.PropertyUtil;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class AbstractApiTestBase
{

    protected ApiClient getAdapterBuilder(String serviceUrl, String servicePath)
    {
        assertNotNull("serviceUrl property is not provided.", serviceUrl);
        assertNotNull("servicePath property is not provided.", servicePath);

        AuthnConfigBuilder authnConfigBuilder = new AuthnConfigBuilder.Builder(PropertyUtil.getProperties()).build();
        TokenProvider tokenProvider = new TokenProvider(authnConfigBuilder);

        OAuth oAuth = new OAuth(tokenProvider);

        return new ApiClient.Builder(serviceUrl + servicePath).setOAuth(oAuth).build();
    }

    protected NodesApi getNodesApi()
    {
        String baseUrl = PropertyUtil.getProperty("alfresco.content.repo.base.url");
        String path = PropertyUtil.getProperty("alfresco.content.repo.core.path", "/api/-default-/public/alfresco/versions/1");

        return getAdapterBuilder(baseUrl, path).createService(NodesApi.class);
    }

}
