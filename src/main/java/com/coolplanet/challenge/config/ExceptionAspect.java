package com.coolplanet.challenge.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class ExceptionAspect {
	
	@Pointcut(" execution(* com.coolplanet.challenge.repository..*(..)))")
	public void controllerPointcut() {
	}
	
	@Around("controllerPointcut()")
	public void catchRepoException(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			joinPoint.proceed();
		} catch(Exception ex) {
			log.error("Exception Caught in {} :: Message :: ", joinPoint.getSignature(), ex.getMessage());
			throw ex;
		}
	}
	

}
