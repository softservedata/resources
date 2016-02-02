package org.registrator.community.profiler;

import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class VisualizationProfiler {
    
    private static final Logger logger = LoggerFactory.getLogger(VisualizationProfiler.class);
    
    @Around("org.registrator.community.profiler.SystemArchitecture.businessController()")
    public Object visualizationProfile(ProceedingJoinPoint pjp) throws Throwable {
        Object output = pjp.proceed();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("method: "+ pjp.getSignature().getName() + " user: " + auth.getName());
        return output;
    }

}
