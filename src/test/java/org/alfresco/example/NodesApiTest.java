package org.alfresco.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.alfresco.rest.core.handler.NodesApi;
import org.alfresco.rest.core.model.NodeEntry;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class NodesApiTest extends AbstractApiTestBase
{
    private NodesApi nodesApi;
    private Response<NodeEntry> response;

    @Given("a node api")
    public void givenANodeApi()
    {
        this.nodesApi = getNodesApi();
    }

    @When("I look for a node <nodeId> via the api")
    public void whenILookForANodeIdViaTheApi(@Named("nodeId") String nodeId) throws Exception
    {
        Call<NodeEntry> call = nodesApi.getNode(nodeId, null, null, null);
        this.response = call.execute();
    }

    @Then("I should get the status code as <status>")
    public void thenIShouldGetTheStatusCodeAsstatus(@Named("status") int status) throws Exception
    {
        assertEquals(status, response.code());
    }

    @Then("I should get the node type as <nodeType>")
    public void thenIShouldGetTheNodeTypeAsnodeType(@Named("nodeType") String nodeType)
    {
        if(response.isSuccessful())
        {
            assertNotNull(response.body());
            assertEquals(nodeType, response.body().getEntry().getNodeType());
        }
        else
        {
            assertEquals(404, response.code());
            assertNull(response.body());
        }

    }

}
