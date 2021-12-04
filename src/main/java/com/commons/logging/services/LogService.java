package com.commons.logging.services;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.commons.logging.annotations.Auditable;

@Aspect
@Component
public class LogService {

	private static final Logger logger = LoggerFactory.getLogger(LogService.class);



	//	@Before("execution(public * com.mariva.hb..*.*(..)) &&  @annotation(auditable) ")
	//	public void logMethodAccessBefore(JoinPoint joinPoint , Auditable auditable) {
	//		//logger.debug("***** Starting: " + joinPoint.getSignature().getName() +" *****");
	//		org.aspectj.lang.Signature signature = joinPoint.getSignature();
	//		String methodName = signature.getName();
	//		String arguments = Arrays.toString(joinPoint.getArgs());
	//		logger.debug("Call method: " + methodName + " with arguments: " + arguments);
	//	}
	//
	//	@AfterReturning("execution(public  * com.mariva.hb..*.*(..)) && @annotation(auditable) ")
	//	public void logMethodAccessAfter(JoinPoint joinPoint, Auditable auditable  ) {
	//		logger.debug("***** Completed: " + joinPoint.getSignature().getName() +" *****");
	//	}

	@Around("execution(public  * com..*.*(..)) && @annotation(auditable) ")
	public Object around(ProceedingJoinPoint joinPoint, Auditable auditable ) throws Throwable {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		String arguments = Arrays.toString(joinPoint.getArgs());

		logger.debug("Call method: " + methodName + " with arguments: " + arguments);

		Object output = joinPoint.proceed();
		stopWatch.stop();
		long elapsedTime = stopWatch.getTotalTimeMillis(); 

		logger.debug("***** Completed: " + joinPoint.getSignature().getName() + " in " + elapsedTime +" ms *****");

		return output;

	}

	@Around("execution(public  * com..controllers..*.*(..)) && @annotation(auditable) ")
	public Object aroundForRestController(ProceedingJoinPoint joinPoint, Auditable auditable ) throws Throwable {

		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		String arguments = Arrays.toString(joinPoint.getArgs());
		logger.info("Service ["+ methodName + "]: sending request with arguments " + arguments);

		Object output = joinPoint.proceed();

		logger.info("Response " + output);

		return output;
	}
}
