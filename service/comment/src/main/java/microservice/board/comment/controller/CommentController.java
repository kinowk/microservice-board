package microservice.board.comment.controller;

import lombok.RequiredArgsConstructor;
import microservice.board.comment.service.CommentCommand;
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

    @GetMapping("/v1/comments")
    public CommentResponse.Page readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        CommentResult.Page result = commentService.readAll(
                CommentCommand.Page.of(articleId, page, pageSize)
        );
        return CommentResponse.Page.from(result);
    }

    @GetMapping("/v1/comments/infinite-scroll")
    public CommentResponse.Cursor readAllInfiniteScroll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(value = "lastParentCommentId", required = false) Long lastParentCommentId,
            @RequestParam(value = "lastCommentId", required = false) Long lastCommentId
    ) {
        CommentResult.Cursor result = commentService.readAllInfiniteScroll(
                CommentCommand.Cursor.of(articleId, pageSize, lastParentCommentId, lastCommentId)
        );
        return CommentResponse.Cursor.from(result);
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

    @GetMapping("/v1/comments/articles/{articleId}/count")
    public Long count(@RequestParam("articleId") Long articleId) {
        return commentService.count(articleId);
    }
}
