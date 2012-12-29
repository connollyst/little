package com.quane.glass.core.language

/**
 *
 * @author Sean Connolly
 */
trait Expression[+T] {

  def evaluate: T

}