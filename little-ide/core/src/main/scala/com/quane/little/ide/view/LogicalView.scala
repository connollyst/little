package com.quane.little.ide.view

import com.quane.little.language.LogicalOperation._
import com.quane.little.ide.presenter.{PresenterAcceptsFunctionReference, PresenterAcceptsExpression}

trait LogicalView
  extends ExpressionView[LogicalViewPresenter]
  with ViewOfLeftAndRightExpressions {

  /** Change the logical operation in the view.
    *
    * @param operation the new logical operation
    */
  def setOperation(operation: LogicalOperation)


}

trait LogicalViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  /** Called when the logical operation changes in the view.
    *
    * @param operation the new logical operation
    */
  def onOperationChange(operation: LogicalOperation): Unit

}