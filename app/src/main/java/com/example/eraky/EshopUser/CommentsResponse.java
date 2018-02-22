package com.example.eraky.myapplication;

import java.util.List;

/**
 * Created by mine on 09/02/18.
 */

public class CommentsResponse {
    private String success;
    private List<Comment> Comments;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }
}
