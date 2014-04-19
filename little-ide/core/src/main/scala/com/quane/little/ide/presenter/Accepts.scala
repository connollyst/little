package com.quane.little.ide.presenter

/**
 *
 *
 * @author Sean Connolly
 */
sealed trait Accepts

trait AcceptsValue extends Accepts {

  def requestAddTextLiteral(): Unit

}