package com.quane.little

import org.slf4j.LoggerFactory

trait Logging {

  private val logger = LoggerFactory.getLogger(getClass)

  protected def debug(message: String) = logger.debug(message)

  protected def info(message: String) = logger.info(message)

  protected def warn(message: String) = logger.warn(message)

  protected def error(message: String) = logger.error(message)

  protected def debug(message: String, cause: Exception) = logger.debug(message, cause)

  protected def info(message: String, cause: Exception) = logger.info(message, cause)

  protected def warn(message: String, cause: Exception) = logger.warn(message, cause)

  protected def error(message: String, cause: Exception) = logger.error(message, cause)

}
