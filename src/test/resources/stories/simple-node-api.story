Narrative:
As an Alfresco Engineer I want to run a repeatable set of tests on a deployed instance of the DBP Test Reference Architecture
So that I am confident the DBP works.

Scenario: Testing the node API
Given a node api
When I look for a node <nodeId> via the api
Then I should get the status code as <status>
And I should get the node type as <nodeType>

Examples:
|nodeId|status|nodeType|
|-my-|200|cm:folder|
|testJBehave|404||
