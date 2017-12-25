package by.kraskouski.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class Initializer {

    private static final RestHighLevelClient client;

    static {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        client = new RestHighLevelClient(builder);
    }

    public static void main(final String... args) throws IOException {
        client.close();
    }
}
