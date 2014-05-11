package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.data.service.PrimitiveService
import com.quane.little.ide.view.{ViewPresenter, View}

object PresenterAccepts {

  def acceptsPrimitive[P <: ViewPresenter](view: View[_ <: P], record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    record.id.oid match {
      case PrimitiveService.PrimitiveGet => m <:< manifest[PresenterAcceptsGet]
      case PrimitiveService.PrimitiveSet => m <:< manifest[PresenterAcceptsSet]
      case PrimitiveService.PrimitivePrint => m <:< manifest[PresenterAcceptsPrint]
      case PrimitiveService.PrimitiveConditional => m <:< manifest[PresenterAcceptsConditional]
      case _ => throw new IllegalArgumentException("Unsupported primitive type: " + record.id.oid)
    }

}

sealed trait PresenterAccepts

trait PresenterAcceptsPrimitive extends PresenterAccepts {

  def requestAddPrimitive(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsGet extends PresenterAcceptsPrimitive

trait PresenterAcceptsSet extends PresenterAcceptsPrimitive

trait PresenterAcceptsPrint extends PresenterAcceptsPrimitive

trait PresenterAcceptsConditional extends PresenterAcceptsPrimitive

trait PresenterAcceptsFunctionReference extends PresenterAccepts {

  def requestAddFunctionReference(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsEventListener extends PresenterAccepts {

  def requestAddEventListener(id: RecordId, index: Int): Unit

}