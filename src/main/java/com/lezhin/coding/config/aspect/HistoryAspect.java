package com.lezhin.coding.config.aspect;

import com.lezhin.coding.config.exption.NoDataException;
import com.lezhin.coding.constants.MsgType;
import com.lezhin.coding.domain.content.Contents;
import com.lezhin.coding.domain.content.History;
import com.lezhin.coding.domain.user.User;
import com.lezhin.coding.repository.ContentsRepository;
import com.lezhin.coding.repository.HistoryRepository;
import com.lezhin.coding.repository.UserRepository;
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

  @Pointcut("execution(* com.lezhin.coding.service.ContentsService.getContentsOne(..))")
  public void onRequest() {}

  @Around("com.lezhin.coding.config.aspect.HistoryAspect.onRequest()")
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
