package com.quane.little

import org.slf4j.LoggerFactory

trait Logging {

  private val logger = LoggerFactory.getLogger(getClass)

  def debug(message: String) = logger.debug(message)

  def info(message: String) = logger.info(message)

  def warn(message: String) = logger.warn(message)

  def error(message: String) = logger.error(message)

}
