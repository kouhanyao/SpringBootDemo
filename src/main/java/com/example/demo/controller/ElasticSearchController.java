package com.example.demo.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by 寇含尧 on 2017/10/30.
 */
@Controller
@RequestMapping(value = "es")
public class ElasticSearchController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TransportClient client;

    /**
     * 根据id查询
     * @return
     */
    @GetMapping(value = "/get/book/novel")
    @ResponseBody
    public ResponseEntity get(String id) {
        if(StringUtils.isEmpty(id))
             id = "AV9ojh9QeszIVVTZKyuC";
        GetResponse result = this.client.prepareGet("book", "novel", id).get();
        if(!result.isExists())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    /**
     * 添加数据
     * @param word_count
     * @param title
     * @param author
     * @param name
     * @param country
     * @param publish_date
     * @return
     */
    @PostMapping(value = "add/book/novel")
    public ResponseEntity add(@RequestParam(name = "word_count") Integer word_count,
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "country") String country,
                              @RequestParam(name = "publish_date")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  Date publish_date
    ) {

        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("word_count", word_count)
                    .field("title", title)
                    .field("author", author)
                    .field("country", country)
                    .field("publish_date", publish_date.getTime())
                    .endObject();
            IndexResponse response = this.client.prepareIndex("book", "novel").setSource(xContentBuilder).get();
            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "delete/book/novel")
    @ResponseBody
    public ResponseEntity delete(String id){
        if(StringUtils.isEmpty(id))
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        DeleteResponse result = this.client.prepareDelete("book", "novel", id).get();
        return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
    }

    /**
     * 通过id更新数据
     * @param id
     * @param word_count
     * @param title
     * @param author
     * @param name
     * @param country
     * @param publish_date
     * @return
     */
    @PutMapping(value = "update/book/novel")
    @ResponseBody
    public ResponseEntity update(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "word_count") Integer word_count,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "country") String country,
            @RequestParam(name = "publish_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            Date publish_date
    ){
        if(StringUtils.isEmpty(id))
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        UpdateRequest updateRequest = new UpdateRequest("book","novel",id);
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject();
            if(word_count != null)
                xContentBuilder.field("word_count", word_count);
            if(title != null)
                xContentBuilder.field("title", title);
            if(author != null)
                xContentBuilder.field("author", author);
            if(name != null)
                xContentBuilder.field("name", name);
            if(country != null)
                xContentBuilder.field("country", country);
            if(publish_date != null)
                xContentBuilder.field("publish_date", publish_date.getTime());
            xContentBuilder.endObject();
            updateRequest.doc(xContentBuilder);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            UpdateResponse result = this.client.update(updateRequest).get();
            return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 复合查询
     * @param title
     * @param author
     * @param gt_word_count
     * @param lt_word_count
     * @return
     */
    @PostMapping(value = "query/book/novel")
    @ResponseBody
    public ResponseEntity complexQuery(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "gt_word_count", defaultValue = "0") Integer  gt_word_count,
            @RequestParam(name = "lt_word_count", required = false) Integer lt_word_count
    ){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(!StringUtils.isEmpty(author))
            boolQuery.must(QueryBuilders.matchQuery("author", author));
        if(!StringUtils.isEmpty(title))
            boolQuery.must(QueryBuilders.matchQuery("title", title));
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count")
                .from(gt_word_count);
        if(lt_word_count != null)
            rangeQueryBuilder.to(lt_word_count);
        boolQuery.filter(rangeQueryBuilder);
        SearchRequestBuilder builder = this.client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);
        System.out.println(builder);
        SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();
        for(SearchHit hit : response.getHits()){
            result.add(hit.getSource());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
