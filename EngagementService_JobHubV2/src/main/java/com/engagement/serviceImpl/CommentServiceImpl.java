package com.engagement.serviceImpl;

import com.engagement.client.CompanyPostClient;
import com.engagement.dto.CommentRequestDto;
import com.engagement.dto.CommentResponseDto;
import com.engagement.entity.Comment;
import com.engagement.entity.TargetType;
import com.engagement.exception.CommentNotFoundException;
import com.engagement.exception.UnauthorizedException;
import com.engagement.mapper.CommentMapper;
import com.engagement.repository.CommentRepository;
import com.engagement.security.AuthenticatedUser;
import com.engagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CompanyPostClient companyPostClient;

    private AuthenticatedUser getLoggedUser(){

        return (AuthenticatedUser) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
    }
    @Override
    public CommentResponseDto createComment(CommentRequestDto dto) {

        AuthenticatedUser user = getLoggedUser();

        Comment comment = CommentMapper.toEntity(dto);

        comment.setUserId(user.getUserId());

        Comment savedComment = this.commentRepository.save(comment);

        return CommentMapper.toResponseDto(savedComment);
    }

    @Override
    public List<CommentResponseDto> getAllComment(Long targetId, TargetType targetType) {
      return this.commentRepository.findByTargetIdAndTargetType(targetId,targetType).stream()
               .map(CommentMapper::toResponseDto).toList();
    }

    @Override
    public CommentResponseDto getByCommentId(Long commentId) {

        Comment comment=this.commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException("Comment Not Found:"+commentId));

        return CommentMapper.toResponseDto(comment);

    }

    @Override
    public CommentResponseDto updateComment(
            CommentRequestDto dto,
            Long commentId) {

        AuthenticatedUser user = getLoggedUser();

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found: " + commentId));

        if (!comment.getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException("You can Only Update Your Comment");
        }

        comment.setContent(dto.getContent());

        Comment updatedComment = this.commentRepository.save(comment);

        return CommentMapper.toResponseDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {


        AuthenticatedUser user=getLoggedUser();

        Comment comment=this.commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException("Comment Not Found:"+commentId));

        if(!comment.getUserId().equals(user.getUserId())){

            throw new UnauthorizedException("You can Only Delete Your Comment");
        }

        this.commentRepository.delete(comment);
    }

    @Override
    public long countCompanyComments(Long companyId) {

        return commentRepository.countByTargetIdAndTargetType(
                companyId,
                TargetType.COMPANY
        );
    }

    @Override
    public long countCompanyPostComments(Long companyId) {

        List<Long> postIds = companyPostClient.getPostIdsByCompany(companyId);

        if (postIds == null || postIds.isEmpty()) {
            return 0;
        }

        return commentRepository.countByTargetIdInAndTargetType(
                postIds,
                TargetType.POST
        );
    }
}
