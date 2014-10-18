package com.quane.little.ide.view.html.dnd

import com.quane.little.ide.presenter.PresenterAcceptsCode
import com.quane.little.ide.view.{View, ViewPresenter}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}

/** Drop handler for views which accept code.
  *
  * @param view
  * @param index
  * @tparam P
  * @tparam V
  * @author Sean Connolly
  */
class CodeDropHandler[P <: ViewPresenter with PresenterAcceptsCode, V <: View[P]](view: V, index: Int = 0) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case t: CodeTransferable =>
        view.presenter.requestAddCode(t.id, index)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}