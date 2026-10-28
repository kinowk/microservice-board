package microservice.board.comment.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.comment.service.CommentResult;
import microservice.board.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/v1/comments/{commentId}")
    public CommentResponse.Read read(@PathVariable("commentId") Long commentId) {
        CommentResult.Read result = commentService.read(commentId);
        return CommentResponse.Read.from(result);
    }

    @PostMapping("/v1/comments")
    public CommentResponse.Create create(@RequestBody CommentRequest.Create request) {
        CommentResult.Create result = commentService.create(request.toCommand());
        return CommentResponse.Create.from(result);
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
