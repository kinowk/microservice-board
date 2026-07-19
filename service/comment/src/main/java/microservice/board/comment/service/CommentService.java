package microservice.board.comment.service;

import lombok.RequiredArgsConstructor;
import microservice.board.common.snowflake.Snowflake;
import microservice.board.comment.controller.request.CommentCreateRequest;
import microservice.board.comment.controller.response.CommentResponse;
import microservice.board.comment.entity.Comment;
import microservice.board.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final Snowflake snowflake = new Snowflake();
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse create(CommentCreateRequest request) {
        Comment parent = findParent(request);
        Comment comment = commentRepository.save(
                Comment.create(
                        snowflake.nextId(),
                        request.getContent(),
                        request.getArticleId(),
                        parent == null ? null : parent.getCommentId(),
                        request.getWriterId()
                )
        );
        return CommentResponse.from(comment);
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();
        if (parentCommentId == null) {
            return null;
        }

        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }

    public CommentResponse read(Long commentId) {
        return CommentResponse.from(
                commentRepository.findById(commentId).orElseThrow()
        );
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);

        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }
}
