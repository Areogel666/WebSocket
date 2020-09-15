package com.sflc.websocket.util;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;

public class JApiDocsRun {

    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("D:\\openSource\\webSocket"); // 项目根目录
        config.setProjectName("webSocket"); // 项目名称
        config.setApiVersion("V1.0.0"); // 声明该API的版本
        config.setDocsPath("D:\\openSource\\webSocket\\api"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
//        config.addPlugin(new MarkdownDocPlugin()); // 生成markDown文件
        Docs.buildHtmlDocs(config); // 执行生成文档
    }
}
