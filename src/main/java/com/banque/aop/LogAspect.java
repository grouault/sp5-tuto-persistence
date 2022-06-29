package com.banque.aop;

import org.apache.logging.log4j.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	  private final static Logger LOG = LogManager.getLogger();

	  /* toutes les méthodes de toutes les classes */
	  @Before("execution(* com.banque.service.impl.*.*(..))")
	  public void logBefore(JoinPoint jp) {
	    LogAspect.LOG.info("Passage avant {} {}", jp.getTarget(), jp.getSignature());
	  }

	  /* toutes les méthodes de toutes les classes */
	  @After("execution(* com.banque.service.impl.*.*(..))")
	  public void logAfter(JoinPoint jp) {
	    LogAspect.LOG.info("Passage apres {} {}", jp.getTarget(), jp.getSignature());
	  }
	
}
