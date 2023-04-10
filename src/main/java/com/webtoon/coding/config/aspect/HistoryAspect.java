package com.webtoon.coding.config.aspect;

import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.exception.MsgType;
import com.webtoon.coding.domain.content.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.infra.repository.history.HistoryRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class HistoryAspect {

  private final HistoryRepository historyRepository;

  private final UserRepository userRepository;

  private final ContentsRepository contentsRepository;

  private final String USER_ID = "X-USER-ID";

  @Pointcut("execution(* com.webtoon.coding.service.contents.ContentsService.getContentsOne(..))")
  public void onRequest() {}

  @Around("com.webtoon.coding.config.aspect.HistoryAspect.onRequest()")
  public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {

    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    String uri = request.getRequestURI();

    String[] uriArray = uri.split("/");

    long contentsId = Long.parseLong(uriArray[uriArray.length - 1]);

    long userId = Long.parseLong(request.getHeader(USER_ID));

    User user =
        userRepository.findById(userId).orElseThrow(() -> new NoDataException(MsgType.NoUserData));

    Contents contents =
        contentsRepository
            .findById(contentsId)
            .orElseThrow(() -> new NoDataException(MsgType.NoContentsData));

    History entity = History.builder().user(user).contents(contents).build();

    historyRepository.save(entity);

    return pjp.proceed(pjp.getArgs());
  }
}
