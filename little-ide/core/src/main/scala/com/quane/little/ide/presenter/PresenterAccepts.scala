package com.quane.little.ide.presenter

/**
 *
 *
 * @author Sean Connolly
 */
sealed trait PresenterAccepts

trait PresenterAcceptsValue extends PresenterAccepts {

  def requestAddTextLiteral(): Unit

}

trait PresenterAcceptsGetter extends PresenterAccepts {

  def requestAddGetStatement(): Unit

}

trait PresenterAcceptsFunctionReference extends PresenterAccepts {

  def requestAddFunctionReference(name: String): Unit

}