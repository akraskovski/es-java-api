package by.kraskouski.elasticsearch;

import by.kraskouski.elasticsearch.api.AggregationApi;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * General bootstrap class.
 */
public class Application {

    private static final RestHighLevelClient CLIENT;
    private static final String AUTH = "Basic ZWxhc3RpYzohRTlnVTBAY05pcjBYXko3M1NyRQ==";
    private static final String HOST = "localhost";
    private static final int PORT = 9200;

    static {
        RestClientBuilder builder = RestClient.builder(new HttpHost(HOST, PORT, "http"));
        CLIENT = new RestHighLevelClient(builder);
    }

    public static void main(final String... args) throws IOException {
//        new IndexApi(CLIENT, AUTH).exampleWithJsonRequest();
        new AggregationApi(CLIENT, AUTH).metricsMaxAggregation();
        CLIENT.close();
    }
}
