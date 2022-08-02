package kz.akvelon.twitter.aspect;

import kz.akvelon.twitter.dto.response.WrappedResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {
    @Around(value = "execution(* kz.akvelon.twitter.controller.TweetsController.*(..))")
    public ResponseEntity<WrappedResponse> addTimeToResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        long before = System.currentTimeMillis();
        Object response = joinPoint.proceed();
        long after = System.currentTimeMillis();
        long time = after - before;

        ResponseEntity<?> castedResponse = (ResponseEntity) response;

        return ResponseEntity
                .status(
                        castedResponse.getStatusCode())
                .body(
                        WrappedResponse.builder()
                                .response(castedResponse.getBody())
                                .time(time)
                                .build());
    }
}