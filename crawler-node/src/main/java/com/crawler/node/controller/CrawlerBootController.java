package com.crawler.node.controller;

import com.crawler.api.entity.TaskWrapper;
import com.crawler.spider.client.JDItemClient;
import com.crawler.spider.processor.searchProcessor.JDSearchProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
@Controller
public class CrawlerBootController {

    @Resource
    StringRedisTemplate redis;

    @PostMapping("/search")
    @ResponseBody
    public void dispatchSearchTask(@RequestBody String wrapperJson) {
        //创建爬虫线程并且获取爬取页面（由taskWrapper封装）的各种信息的URI
        accomplishTask(wrapperJson, (taskWrapper) ->
                Spider.create(new JDSearchProcessor())
                        .addUrl(taskWrapper.getMetaData())
                        .run());
    }

    @PostMapping("/item")
    @ResponseBody
    public void dispatchItemTask(@RequestBody String wrapperJson) {
        accomplishTask(wrapperJson, (taskWrapper) -> {
            JDItemClient jdItemClient = new JDItemClient();
            jdItemClient.getInfoByItem(taskWrapper.getMetaData());
        });
    }

    void accomplishTask(String wrapperJson, Consumer<TaskWrapper> crawlerCode) {
        TaskWrapper task = TaskWrapper.fromJson(wrapperJson);

        log.info("收到任务：{}", task);
        //进行接口的并发，每一个接口一个线程，当有任务发生异常时，打印异常信息
        CompletableFuture.runAsync(() -> {
            crawlerCode.accept(task);
            redis.opsForHash().put("crawlerTask", task.toJson(), "true");
            log.info("完成任务：{}", task);
        }).whenComplete((unused, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        });
    }

}
