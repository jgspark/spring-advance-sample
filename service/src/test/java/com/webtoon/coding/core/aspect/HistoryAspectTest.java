package com.webtoon.coding.core.aspect;

import com.webtoon.coding.domain.common.Reader;
import com.webtoon.coding.domain.common.Writer;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryAspectTest {

    @Mock
    private Writer<History> historyWriter;

    @Mock
    private Reader<User> userReader;

    @Mock
    private Reader<Contents> contentsReader;

    private HistoryAspect historyAspect;

    @BeforeEach
    public void init() {

        this.historyAspect = new HistoryAspect(historyWriter, userReader, contentsReader);
    }

    @Test
    public void testDoLogging() throws Throwable {

        setGiven("/contents/100", "1");

        User user = UserMock.createdMock();

        Contents contents = ContentsMock.createdMock();

        when(userReader.get(any())).thenReturn(user);

        when(contentsReader.get(any())).thenReturn(contents);

        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
        Object[] args = new Object[] { 1L };

        when(pjp.getArgs()).thenReturn(args);

        Object result = historyAspect.doLogging(pjp);

        verify(userReader, times(1)).get(any());
        verify(contentsReader, times(1)).get(any());
        verify(historyWriter, times(1)).save(any(History.class));

        assertEquals(result, pjp.proceed(args));
    }

    @Test
    public void testDoLoggingFailByUrlNotPasser() throws Throwable {

        setGiven("/contents100", "1");

        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);

        RuntimeException e = assertThrows(RuntimeException.class, () -> historyAspect.doLogging(pjp));

        assertEquals(e.getMessage(), "Error To Passer");
    }

    private void setGiven(String url, String userId) {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        mockRequest.addHeader("X-USER-ID", userId);

        mockRequest.setRequestURI(url);

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest, mockResponse));
    }

}
