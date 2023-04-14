package com.usgate.gptclient;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 示例客户端
 * @author usgate
 */
public class Chat {

    private static final String url = "https://xxxxxx.com/chatGPT/chat";

    public static void main(String[] args) {
        Map<String, String> question1 = new HashMap<>();
        //role: user(用户输入) , system(系统预设语境) , assistant(gpt-3生成的回复)
        question1.put("role", "user");
        question1.put("content", "swift how to  Turn on the front and rear cameras at the same time");
        Map<String, String> question2 = new HashMap<>();
        question2.put("role", "assistant");
        question2.put("content", "this is a test");
        Map<String, String> question3 = new HashMap<>();
        question3.put("role", "assistant");
        question3.put("content", "tell me how to do it");
        String response = response(Lists.newArrayList(question1, question2, question3));
        System.out.println(response);
    }

    /**
     * 调用gpt-3
     * @param context 上下文
     * @return 回复
     */
    public static String response(List<Map<String,String>> context) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            Map<String, Object> postBody = new HashMap<>();
            postBody.put("model", "gpt-3.5-turbo");
            postBody.put("messages", context);
            HttpEntity<Map> entity = new HttpEntity<>(postBody, headers);
            String s = restTemplate.postForEntity(url,entity,String.class).getBody();
            GPTResponse responseEntity = JSON.parseObject(s, GPTResponse.class);
            String content = responseEntity.getChoices().get(0).getMessage().getContent();
            content = content.replaceAll("AI语言模型", "usgate的小助手");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "坏掉了555";
        }
    }

    /**
     * gpt-3返回的json对象
     */
    @Data
    private static class GPTResponse implements Serializable {
        private int created;
        private UsageBean usage;
        private String model;
        private String id;
        private String object;
        private List<ChoicesBean> choices;

        @Data
        public static class UsageBean implements Serializable {
            private int prompt_tokens;
            private int completion_tokens;
            private int total_tokens;
        }

        @Data
        public static class ChoicesBean implements Serializable {
            private MessageBean message;
            private String finish_reason;
            private int index;

            @Data
            public static class MessageBean implements Serializable {
                private String role;
                private String content;
            }
        }
    }


}
