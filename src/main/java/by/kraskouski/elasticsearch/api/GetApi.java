package by.kraskouski.elasticsearch.api;

import by.kraskouski.elasticsearch.Application;
import by.kraskouski.elasticsearch.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.UUID;

/**
 * Samples of using elasticsearch Get API requests.
 */
public class GetApi {

    private final RestHighLevelClient client;
    private final String index;
    private final String type;
    private final ObjectMapper mapper;

    public GetApi(final RestHighLevelClient client, final String index, final String type) {
        this.client = client;
        this.index = index;
        this.type = type;
        this.mapper = new ObjectMapper();
    }

    public void getRequest() throws IOException {
        final User user = prepareUser();
        createUserRequest(user);

        final GetRequest request = new GetRequest(index, type, user.getId());
        final GetResponse response = client.get(request, Application.prepareAuthHeader());
        final User responseUser = mapper.readValue(response.getSourceAsString(), User.class);
        assert user.equals(responseUser);
    }

    public void getRequestInvalidIndex() throws IOException {
        final GetRequest request = new GetRequest("invalid_index", type, UUID.randomUUID().toString());
        try {
            client.get(request, Application.prepareAuthHeader());
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println(e.getDetailedMessage());
            }
        }
    }

    private void createUserRequest(final User user) throws IOException {
        final IndexRequest indexRequest = new IndexRequest(index, type, user.getId());
        indexRequest.source(mapper.writeValueAsString(user), XContentType.JSON);

        client.index(indexRequest, Application.prepareAuthHeader());
    }

    private User prepareUser() {
        return User.getBuilder()
                .setId(UUID.randomUUID().toString())
                .setFirstname("Alexandr")
                .setLastname("Lukashenko")
                .build();
    }
}
