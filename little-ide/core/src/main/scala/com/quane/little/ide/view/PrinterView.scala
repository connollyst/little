package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** The view for a [[com.quane.little.language.Printer]] statement displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait PrinterView
  extends StatementView[PrinterViewPresenter] {

  /** Replace the existing print value expression with a [[ValueView]].
    *
    * @return the view which was create
    */
  def createValueStatement(): ValueView

  /** Replace the existing print value expression with a [[GetterView]].
    *
    * @return the view which was create
    */
  def createGetStatement(): GetterView

  /** Replace the existing print value expression with a [[FunctionReferenceView]].
    *
    * @return the view which was create
    */
  def createFunctionReference(): FunctionReferenceView

}

/** The presenter backing the [[PrinterView]].
  *
  * @author Sean Connolly
  */
trait PrinterViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference