package com.nzion.aspects;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class LoggingAspect {

	private static final Logger logger = Logger.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.nzion.service..*.*(..))")
	public void allServicePointCut(){}

	@Pointcut("execution(* com.nzion.repository..*.*(..))")
	public void allReposirotyPointCut(){}

	@Pointcut("execution(* com.nzion.hibernate.ext..*.*(..))")
	public void allExtPointCut(){}

	@Before("allServicePointCut() || allReposirotyPointCut()")
	public void entryLogger(JoinPoint joinPoint){
	if(logger.isInfoEnabled()){
		Arrays.asList(joinPoint.getArgs());
	logger.debug("\n***** Entering --> " + joinPoint.toString() + " Args " +  Arrays.toString(joinPoint.getArgs()));
	}
	}

	@After("allServicePointCut() || allReposirotyPointCut()")
	public void exitLogger(JoinPoint joinPoint){
	if(logger.isInfoEnabled() ){
		logger.debug("\n***** Exiting --> " + joinPoint.toString() + " Args " +  Arrays.toString(joinPoint.getArgs()));
	}
	}

	@AfterThrowing(pointcut="allServicePointCut() || allReposirotyPointCut()",throwing="fatalException")
	public void exceptionLogger(Throwable fatalException){
	if(logger.isEnabledFor(Priority.FATAL)){
		logger.fatal(fatalException.getStackTrace(), fatalException);
	}
	}
}