package com.yangyang.springboot.es;

import com.yangyang.springboot.es.common.ESHelper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecordsTest {
    private final static String indexName = "sms_record_201712";
    public static final String  typeName = "sms-records";
    private final static String clusterName = "elk-test";
    private final static String[] esUrls = new String[]{"localhost:9300"};

    private TransportClient transportClient;

    @Before
    public void before() {

        transportClient = ESHelper.makeEStransportClient(clusterName,esUrls);
    }

    @Test
    public void addSmsRecord() {
        Map<String, Object> map = new HashMap<>();
        long t = System.currentTimeMillis();
        map.put("appKey", "appkey_" + t);
        map.put("smsId", t);
        map.put("content", "content" + t);
        map.put("status", "SUCCESS");
        map.put("type", "SMS_CODE");
        map.put("targetCount", 11);
        map.put("extra","23412");
        map.put("createTime", new Date());
        map.put("updateTime", new Date());
        map.put("platform", "platform");
        map.put("isP2pMarket", 0);
        map.put("channel", "channel");
        transportClient.prepareIndex(indexName, typeName, map.get("smsId").toString()).setRouting(map.get("smsId").toString()).setSource(map).get();
        System.out.println(t);
    }

    @Test
    public void getRecords() {

        Date stratTime = new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60*1000L);
        Date endTime = new Date();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.rangeQuery("createTime").gte(stratTime.getTime()).lte(endTime.getTime()));
//        queryBuilder.must(QueryBuilders.termQuery("appKey", ""));

        SearchResponse response = transportClient.prepareSearch(indexName).setTypes(typeName).setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(queryBuilder).addSort("createTime", SortOrder.DESC).setFrom(0).setSize(10).get();
        SearchHit[] searchHits = response.getHits().getHits();
        System.out.println(searchHits);
    }

}
