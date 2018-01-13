package by.kraskouski.elasticsearch.api;

import by.kraskouski.elasticsearch.Application;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RestHighLevelClient;
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

    public GetApi(final RestHighLevelClient client, final String index, final String type) {
        this.client = client;
        this.index = index;
        this.type = type;
    }

    public void getRequest() {

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
}
