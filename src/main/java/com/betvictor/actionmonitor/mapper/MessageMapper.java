package com.betvictor.actionmonitor.mapper;

import com.betvictor.actionmonitor.api.model.MessageDto;
import org.mapstruct.Mapper;
import com.betvictor.actionmonitor.storage.model.StoredMessage;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto asMessageDto(StoredMessage storedMessage);

    StoredMessage asStoredMessage(MessageDto messageDto);

    List<MessageDto> asMessageDtoList(List<StoredMessage> storedMessage);
}
