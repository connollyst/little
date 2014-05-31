package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.data.service.{ExpressionService, StatementService}
import com.quane.little.ide.view.{ViewPresenter, View}

object PresenterAccepts {

  def accepts[P <: ViewPresenter](view: View[_ <: P], record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    record.id.oid match {
      // TODO this is sketchy =\
      case StatementService.Set => m <:< manifest[PresenterAcceptsStatement]
      case StatementService.Print => m <:< manifest[PresenterAcceptsStatement]
      case ExpressionService.Get => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Conditional => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Addition => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Subtraction => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Multiplication => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Division => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Equals => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.NotEquals => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.LessThan => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.GreaterThan => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.And => m <:< manifest[PresenterAcceptsExpression]
      case ExpressionService.Or => m <:< manifest[PresenterAcceptsExpression]
      case _ => throw new IllegalArgumentException("Unsupported primitive type: " + record.id.oid)
    }

}

sealed trait PresenterAccepts

trait PresenterAcceptsExpression extends PresenterAccepts {

  // TODO consolidate these

  def requestAddExpression(id: RecordId, index: Int): Unit

  def requestAddFunctionReference(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsStatement extends PresenterAccepts {

  def requestAddStatement(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: RecordId, index: Int): Unit

  def requestAddBlankFunctionDefinition(index: Int): Unit

}

trait PresenterAcceptsEventListener extends PresenterAccepts {

  def requestAddEventListener(id: RecordId, index: Int): Unit

  def requestAddBlankEventListener(index: Int): Unit

}