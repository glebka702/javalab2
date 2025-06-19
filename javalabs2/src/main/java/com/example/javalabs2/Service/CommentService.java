package com.example.javalabs2.Service;

import com.example.javalabs2.Entity.Comment;
import com.example.javalabs2.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return null;
        }

        Comment comment = commentOpt.get();
        comment.setAuthor(commentDetails.getAuthor());
        comment.setText(commentDetails.getText());

        return commentRepository.save(comment);
    }

    public boolean deleteComment(Long id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return false;
        }

        commentRepository.deleteById(id);
        return true;
    }

}