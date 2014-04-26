package com.quane.little.ide.presenter

import com.quane.little.data.model.RecordId

/**
 *
 *
 * @author Sean Connolly
 */
sealed trait PresenterAccepts

trait PresenterAcceptsValue extends PresenterAccepts {

  def requestAddTextLiteral(index: Int): Unit

}

trait PresenterAcceptsGetter extends PresenterAccepts {

  def requestAddGetStatement(index: Int): Unit

}

trait PresenterAcceptsFunctionReference extends PresenterAccepts {

  def requestAddFunctionReference(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: RecordId, index: Int): Unit

}