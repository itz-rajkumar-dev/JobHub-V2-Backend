package com.engagement.mapper;

import com.engagement.dto.LikeRequestDto;
import com.engagement.dto.LikeResponseDto;
import com.engagement.entity.Like;

public class LikeMapper {

    public static Like toEntity(LikeRequestDto dto){

        Like like=new Like();

       like.setTargetId(dto.getTargetId());
       like.setTargetType(dto.getTargetType());
        return like;
    }

    public static  LikeResponseDto toResponseDto(Like like){

        LikeResponseDto dto=new LikeResponseDto();

        dto.setLikeId(like.getLikeId());
        dto.setTargetId(like.getTargetId());
        dto.setTargetType(like.getTargetType());
        dto.setUserId(like.getUserId());

        return  dto;
    }
}
