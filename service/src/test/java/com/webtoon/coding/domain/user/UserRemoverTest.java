package com.webtoon.coding.domain.user;

import com.webtoon.coding.core.exception.MsgType;
import com.webtoon.coding.core.exception.NoDataException;
import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.mock.CommentMock;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRemoverTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private HistoryRepository historyRepository;

    private UserRemover userRemover;

    @BeforeEach
    public void init() {
        this.userRemover = new UserRemover(userRepository, commentRepository, historyRepository);
    }

    @Test
    public void testRemove() {

        long userId = 1L;

        User userMock = UserMock.createdMock();

        Contents contentsMock = ContentsMock.createdMock();

        List<Comment> commentsMock = List.of(CommentMock.createdMock(userMock, contentsMock));

        List<History> historiesMock = List.of(HistoryMock.createdMock(userMock, contentsMock));

        when(userRepository.findById(any())).thenReturn(Optional.of(userMock));

        when(commentRepository.findByUser(any())).thenReturn(commentsMock);

        when(historyRepository.findByUser(any())).thenReturn(historiesMock);

        assertDoesNotThrow(() -> userRemover.remove(userId));

        verify(userRepository, times(1)).findById(any());

        verify(commentRepository, times(1)).findByUser(any());

        verify(commentRepository, times(1)).deleteById_UserId(any());

        verify(historyRepository, times(1)).findByUser(any());

        verify(historyRepository, times(1)).deleteByUser_Id(any());

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    public void testRemoveFailByUserNotFound() {

        long userId = 1L;

        when(userRepository.findById(any())).thenThrow(new NoDataException(MsgType.NoUserData));

        NoDataException e = assertThrows(NoDataException.class, () -> userRemover.remove(userId));

        verify(userRepository, times(1)).findById(any());

        assertEquals(e.getMsgType(), MsgType.NoUserData);
        assertEquals(e.getMsgType().getMessage(), MsgType.NoUserData.getMessage());
    }

    @Test
    public void testRemoveByCommentsEmpty() {

        long userId = 1L;

        User userMock = UserMock.createdMock();

        Contents contentsMock = ContentsMock.createdMock();

        List<History> historiesMock = List.of(HistoryMock.createdMock(userMock, contentsMock));

        when(userRepository.findById(any())).thenReturn(Optional.of(userMock));

        when(commentRepository.findByUser(any())).thenReturn(Collections.emptyList());

        when(historyRepository.findByUser(any())).thenReturn(historiesMock);

        assertDoesNotThrow(() -> userRemover.remove(userId));

        verify(userRepository, times(1)).findById(any());

        verify(commentRepository, times(1)).findByUser(any());

        verify(historyRepository, times(1)).findByUser(any());

        verify(historyRepository, times(1)).deleteByUser_Id(any());

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    public void testRemoveByHistoriesEmptyData() {

        long userId = 1L;

        User userMock = UserMock.createdMock();

        Contents contentsMock = ContentsMock.createdMock();

        List<Comment> commentsMock = List.of(CommentMock.createdMock(userMock, contentsMock));

        when(userRepository.findById(any())).thenReturn(Optional.of(userMock));

        when(commentRepository.findByUser(any())).thenReturn(commentsMock);

        when(historyRepository.findByUser(any())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> userRemover.remove(userId));

        verify(userRepository, times(1)).findById(any());

        verify(commentRepository, times(1)).findByUser(any());

        verify(commentRepository, times(1)).deleteById_UserId(any());

        verify(historyRepository, times(1)).findByUser(any());

        verify(userRepository, times(1)).delete(any());
    }

}