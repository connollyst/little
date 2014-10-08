package com.quane.little.ide.view

import com.quane.little.ide.presenter.PresenterAcceptsCode
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation

/** The view for a [[com.quane.little.language.math.Math]] statement displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait MathView extends CodeView[MathViewPresenter] with ViewOfLeftAndRightExpressions {

  /** Change the math operation in the view.
    *
    * @param operation the new math operation
    */
  def setOperation(operation: BasicMathOperation)

}

/** The presenter backing the [[com.quane.little.ide.view.MathView]].
  *
  * @author Sean Connolly
  */
trait MathViewPresenter extends CodeViewPresenter with PresenterAcceptsCode {

  /** Called when the math operation changes in the view.
    *
    * @param operation the new math operation
    */
  def onOperationChange(operation: BasicMathOperation): Unit

}