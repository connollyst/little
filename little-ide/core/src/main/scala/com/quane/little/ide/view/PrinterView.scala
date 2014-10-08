package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** The view for a [[com.quane.little.language.Printer]] statement displayed in
  * the IDE.
  *
  * @author Sean Connolly
  */
trait PrinterView
  extends StatementView[PrinterViewPresenter] {

  /** Create and add a [[com.quane.little.ide.view.CodeMenuView]] to the view. If
    * one already exists, it will be removed.
    *
    * @return the view which was created
    */
  def createCodeMenu(): CodeMenuView

  /** Replace the existing print value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createValueStatement(): ValueView

  /** Replace the existing print value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createGetStatement(): GetterView

  /** Replace the existing print value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was created
    */
  def createFunctionReference(): FunctionReferenceView

}

/** The presenter backing the [[com.quane.little.ide.view.PrinterView]].
  *
  * @author Sean Connolly
  */
trait PrinterViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsCode