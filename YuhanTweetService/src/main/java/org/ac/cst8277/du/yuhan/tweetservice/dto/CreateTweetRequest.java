package org.ac.cst8277.du.yuhan.tweetservice.dto;

public class CreateTweetRequest {

    private String token;
    private String content;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

