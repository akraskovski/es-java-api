package by.kraskouski.elasticsearch.api;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * Samples of using elasticsearch index API requests.
 */
public class AggregationApi {

    private static final String index = "my-index";
    private static final String type = "my-type";
    private final RestHighLevelClient client;
    private final String credentials;

    public AggregationApi(final RestHighLevelClient client, final String credentials) {
        this.client = client;
        this.credentials = credentials;
    }

    public void metricsMaxAggregation() throws IOException {
        final BulkRequest request = new BulkRequest();
        request.add(new IndexRequest(index, type, "1")
                .source(XContentType.JSON, "country", "Belarus", "age", 20));
        request.add(new IndexRequest(index, type, "2")
                .source(XContentType.JSON, "country", "Ukraine", "age", 30));
        request.add(new IndexRequest(index, type, "3")
                .source(XContentType.JSON, "country", "Poland", "age", 40));
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        client.bulk(request, prepareAuthHeader());

        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.max("age_aggr").field("age"));
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        final SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = client.search(searchRequest, prepareAuthHeader());
        final double result = ((Max) searchResponse.getAggregations().get("age_aggr")).getValue();
        System.out.println("Max age from documents: " + result);
    }

    private Header[] prepareAuthHeader() {
        return new BasicHeader[]{
                new BasicHeader(HttpHeaders.AUTHORIZATION, credentials),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")};
    }
}
