package com.study.chat.controller;

import java.util.List;
import java.util.Map;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
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
                "created_at", System.currentTimeMillis()
            ));

        List<Document> docs = List.of(document1, document2, document3, document4);
        vectorStore.add(docs);
        return Map.of(
            "status", "success",
            "message", "添加文档成功",
            "count", docs.size()
        );
    }
}
