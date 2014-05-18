package com.quane.little.ide.view

import com.quane.little.language.LogicOperation._
import com.quane.little.ide.presenter.{PresenterAcceptsFunctionReference, PresenterAcceptsExpression}

trait LogicView
  extends ExpressionView[LogicViewPresenter]
  with ViewOfLeftAndRightExpressions {

  /** Change the logical operation in the view.
    *
    * @param operation the new logical operation
    */
  def setOperation(operation: LogicOperation)


}

trait LogicViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  /** Called when the logical operation changes in the view.
    *
    * @param operation the new logical operation
    */
  def onOperationChange(operation: LogicOperation): Unit

}