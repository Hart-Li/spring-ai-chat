package com.study.chat.controller;

import java.util.List;
import java.util.Map;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HartLi
 * @version 1.0.0
 * @desc TODO
 * @date 2025-12-11 01:19
 */
@RestController
public class MyController {

    private final VectorStore vectorStore;

    public MyController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/vector/chroma")
    public Map<String, Object> save() {
        // 创建多分文档
        Document document1 = new Document("doc-001", """
            兰州牛肉面是中国最著名的牛肉面之一，
            以其'一清(汤清)、二白(萝卜白)、三红(辣椒油红)、四绿(香菜蒜苗绿)、五黄(面条黄亮)'著称。兰州本地人常称它为'牛肉拉面’，代表性的品牌有'马子禄’安泊尔’等。
            """,
            Map.of(
                "source", "美食中国",
                "category", "地方特色",
                "created_at", System.currentTimeMillis()
            ));

        Document document2 = new Document("doc-002", """
            台湾牛肉面是台湾的经典美食，主要分为'红烧牛肉面’和'清炖牛肉面’两种。其中'红烧牛肉面’汤头浓郁，常搭配牛腱肉和手工面条;而'清炖牛肉面’则更注重原汤鲜味。台北的'林东芳牛肉面’和'永康牛肉面’是知名老店。
            """,
            Map.of(
                "source", "台湾美食指南",
                "category", "地方特色",
                "created_at", System.currentTimeMillis()
            ));

        Document document3 = new Document("doc-003", """
            湖北襄阳牛肉面以麻辣鲜香闻名，汤底用牛骨熬制，搭配碱水面和卤牛肉片，常佐以豆芽和香菜。襄阳人通常将其作为早餐，本地老字号'张家牛肉面’和'王府牛肉面’深受食客喜爱。
            """,
            Map.of(
                "source", "湖北日报",
                "category", "地方特色",
                "created_at", System.currentTimeMillis()
            ));

        Document document4 = new Document("doc-004", """
            HartLi专注于分享AI技术、软件开发、运维实战、数据库优化和操作系统实用技巧，用'人话'拆解复杂技术。希望通过平台分享经验，帮大家少走弯路、提升效率，一起把技术难题'盘'成生产力工具!
            """,
            Map.of(
                "source", "B站",
                "category", "知识UP",
                "created_at", 1765850123000L
            ));

        List<Document> docs = List.of(document1, document2, document3, document4);
        vectorStore.add(docs);
        return Map.of(
            "status", "success",
            "message", "添加文档成功",
            "count", docs.size()
        );
    }

    @GetMapping("/vector/chroma")
    public Map<String, Object> find(@RequestParam("query") String query) {
        // 1.查询所有文档（按相似得分由高到低排序）
        List<Document> documents1 = vectorStore.similaritySearch(query);
        // 2. 按照相似度分过滤
        List<Document> documents2 = vectorStore.similaritySearch(
            SearchRequest
                .builder().
                query(query)
                .similarityThreshold(0.5)  // 相似度得分 >= 0.5
                .build());
        // 3. 按照排序过滤出最相似的几个文档
        List<Document> documents3 = vectorStore.similaritySearch(
            SearchRequest
                .builder().
                query(query)
                .topK(1)  // 最相似的文档
                .build());
        return Map.of("documents1", documents1, "documents2", documents2, "documents3", documents3);
    }

    @GetMapping("/vector/chroma/filter")
    public Map<String, Object> searchFilter(@RequestParam("query") String query) {
        // 使用字符条件过滤
        String filterExpression1 = "source == '湖北日报'";
        String filterExpression2 = "source == '湖北日报' || source == '美食中国'";
        String filterExpression3 = "source in ['湖北日报', '美食中国']";
        String filterExpression4 = "source nin ['湖北日报'] && category == '地方特色'";

        // 执行相似性查询
        List<Document> documents1 = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .filterExpression(filterExpression1)
                .build());
        List<Document> documents2 = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .filterExpression(filterExpression2)
                .build());
        List<Document> documents3 = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .filterExpression(filterExpression3)
                .build());
        List<Document> documents4 = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .filterExpression(filterExpression4)
                .build());

        return Map.of("documents1", documents1, "documents2", documents2, "documents3", documents3,
            "documents4", documents4);
    }

    @GetMapping("/vector/chroma/filter2")
    public Map<String, Object> searchFilter2(@RequestParam("query") String query) {
        // 条件表达式过滤
        Filter.Expression expression = new Filter.Expression(
            Filter.ExpressionType.GT,
            new Filter.Key("created_at"),
            new Filter.Value(System.currentTimeMillis())
        );
        List<Document> documents1 = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .filterExpression(expression)
                .build()
        );
        return Map.of("documents1", documents1);
    }

    @DeleteMapping("/vector/chroma")
    public Map<String, Object> delete() {
        // 根据文档ID批量删除
        vectorStore.delete(List.of("doc-001", "doc-002"));
        // 删除所有 source 为 湖北日报 的文档
        vectorStore.delete("source == '湖北日报'");
        // 删除所有 category 为 地方特色 的文档
        vectorStore.delete(new Filter.Expression(
            Filter.ExpressionType.EQ,
            new Filter.Key("category"),
            new Filter.Value("地方特色")
        ));
        return Map.of("status", "success");
    }
}
