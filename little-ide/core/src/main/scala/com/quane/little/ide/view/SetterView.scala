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

  /** Replace the existing assignment value expression with a [[ValueView]].
    *
    * @return the view which was create
    */
  def createValueStatement(): ValueView

  /** Replace the existing assignment value expression with a [[GetterView]].
    *
    * @return the view which was create
    */
  def createGetStatement(): GetterView

  /** Replace the existing assignment value expression with a [[FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createFunctionReference(): FunctionReferenceView

}

/** The presenter backing the [[SetterView]].
  *
  * @author Sean Connolly
  */
trait SetterViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  /** Called when the variable name changes in the view.
    *
    * @param name the new variable name
    */
  def onNameChange(name: String): Unit

}