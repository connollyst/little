package com.quane.little.ide.view

import com.quane.little.language.EvaluationOperator._
import com.quane.little.ide.presenter.{PresenterAcceptsFunctionReference, PresenterAcceptsExpression}

trait LogicalView extends ExpressionView[LogicalViewPresenter] {

  /** Change the logical operation in the view.
    *
    * @param operator the new logical operation
    */
  def setOperation(operation: EvaluationOperator)

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

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createLeftFunctionReference(): FunctionReferenceView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createRightFunctionReference(): FunctionReferenceView

}

trait LogicalViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  /** Called when the logical operation changes in the view.
    *
    * @param operation the new logical operation
    */
  def onOperationChange(operation: EvaluationOperator): Unit

}