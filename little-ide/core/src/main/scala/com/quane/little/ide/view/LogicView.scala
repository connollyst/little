package com.quane.little.ide.view

import com.quane.little.ide.presenter.PresenterAcceptsCode
import com.quane.little.language.LogicOperation._

trait LogicView extends CodeView[LogicViewPresenter] with ViewOfLeftAndRightExpressions {

  /** Change the logical operation in the view.
    *
    * @param operation the new logical operation
    */
  def setOperation(operation: LogicOperation)

}

trait LogicViewPresenter extends CodeViewPresenter with PresenterAcceptsCode {

  /** Called when the logical operation changes in the view.
    *
    * @param operation the new logical operation
    */
  def onOperationChange(operation: LogicOperation): Unit

}