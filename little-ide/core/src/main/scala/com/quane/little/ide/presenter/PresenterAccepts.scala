package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.data.service.{ExpressionService, StatementService}
import com.quane.little.ide.view.{ViewPresenter, View}

object PresenterAccepts {

  def acceptsPrimitive[P <: ViewPresenter](view: View[_ <: P], record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    record.id.oid match {
      case StatementService.Set => m <:< manifest[PresenterAcceptsStatement]
      case StatementService.Print => m <:< manifest[PresenterAcceptsStatement]
      case ExpressionService.Get => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Conditional => m <:< manifest[PresenterAcceptsExpression]
      case _ => throw new IllegalArgumentException("Unsupported primitive type: " + record.id.oid)
    }

}

sealed trait PresenterAccepts

trait PresenterAcceptsExpression extends PresenterAccepts {

  def requestAddExpression(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsStatement extends PresenterAccepts {

  def requestAddStatement(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionReference extends PresenterAccepts {

  def requestAddFunctionReference(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsEventListener extends PresenterAccepts {

  def requestAddEventListener(id: RecordId, index: Int): Unit

}