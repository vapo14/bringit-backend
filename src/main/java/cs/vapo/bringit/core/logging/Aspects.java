package cs.vapo.bringit.core.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Aspect
@Component
public class Aspects {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    @Before("@annotation(cs.vapo.bringit.core.logging.LogMethodEntry)")
    public void logEnteringMethod(final JoinPoint joinPoint) {
        final String invokerClass = joinPoint.getSignature().getDeclaringTypeName();
        final String invokerMethod = joinPoint.getSignature().getName();
        log.debug("Entering method {}::{}", invokerClass, invokerMethod);
    }

    @After("@annotation(cs.vapo.bringit.core.logging.LogMethodEntry)")
    public void logExitingMethod(final JoinPoint joinPoint) {
        final String invokerClass = joinPoint.getSignature().getDeclaringTypeName();
        final String invokerMethod = joinPoint.getSignature().getName();
        log.debug("Exiting method {}::{}", invokerClass, invokerMethod);
    }
}
