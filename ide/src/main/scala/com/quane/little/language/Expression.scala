package com.quane.little.language


/** @author Sean Connolly
  */
trait Expression[+T] {

  def evaluate: T

}