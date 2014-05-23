package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** The view for a [[com.quane.little.language.Setter]] statement displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait SetterView extends StatementView[SetterViewPresenter] {

  /** Change the variable name in the view.
    *
    * @param name the new variable name
    */
  def setName(name: String): Unit

  /** Replace the existing assignment value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createGetterExpression(): GetterView

  /** Replace the existing assignment value expression with a [[com.quane.little.ide.view.MathView]].
    *
    * @return the view which was created
    */
  def createMathExpression(): MathView

  /** Replace the existing assignment value expression with a [[com.quane.little.ide.view.LogicView]].
    *
    * @return the view which was created
    */
  def createLogicExpression(): LogicView

  /** Replace the existing assignment value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createValueExpression(): ValueView

  /** Replace the existing assignment value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was created
    */
  def createFunctionReference(): FunctionReferenceView

}

/** The presenter backing the [[com.quane.little.ide.view.SetterView]].
  *
  * @author Sean Connolly
  */
trait SetterViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression {

  /** Called when the variable name changes in the view.
    *
    * @param name the new variable name
    */
  def onNameChange(name: String): Unit

}