package com.quane.little.ide.view

import com.quane.little.ide.presenter.{PresenterAcceptsFunctionReference, PresenterAcceptsExpression}
import com.quane.little.language.math.BasicMathOperation

/** The view for a [[com.quane.little.language.math.Math]] statement displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait MathView extends ExpressionView[MathViewPresenter] {

  /** Change the math operation in the view.
    *
    * @param operation the new math operation
    */
  def setOperation(operation: BasicMathOperation)

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was create
    */
  def createLeftValueStatement(): ValueView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was create
    */
  def createRightValueStatement(): ValueView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was create
    */
  def createLeftGetStatement(): GetterView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was create
    */
  def createRightGetStatement(): GetterView

  /** Replace the existing left value expression with a [[FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createLeftFunctionReference(): FunctionReferenceView

  /** Replace the existing right value expression with a [[FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createRightFunctionReference(): FunctionReferenceView

}

/** The presenter backing the [[MathView]].
  *
  * @author Sean Connolly
  */
trait MathViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  /** Called when the math operation changes in the view.
    *
    * @param operation the new math operation
    */
  def onOperationChange(operation: BasicMathOperation): Unit

}