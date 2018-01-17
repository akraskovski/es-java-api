package by.kraskouski.elasticsearch.api;

import by.kraskouski.elasticsearch.Application;
import org.apache.http.Header;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.UUID;

/**
 * Samples of using elasticsearch index API requests.
 */
public class IndexApi {

    private final RestHighLevelClient client;
    private final String index;
    private final String type;

    public IndexApi(final RestHighLevelClient client, final String index, final String type) {
        this.client = client;
        this.index = index;
        this.type = type;
    }

    /**
     * Create an index, type and document post with custom id.
     * As request body set json string.
     */
    public void exampleWithJsonRequest() throws IOException {
        final String id = UUID.randomUUID().toString();

        final IndexRequest indexRequest = new IndexRequest(index, type, id);
        final String jsonString = "{" +
                "\"user\":\"Artsiom Kraskouski\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);

        final Header[] headers = Application.prepareAuthHeader();
        final IndexResponse response = client.index(indexRequest, headers);
        assert response.status().equals(RestStatus.OK);

    }
}
