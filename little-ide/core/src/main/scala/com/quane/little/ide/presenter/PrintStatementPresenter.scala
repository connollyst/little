package com.quane.little.ide.presenter

import com.quane.little.ide.view.{PrintStatementViewPresenter, PrintStatementView}
import com.quane.little.language._
import com.quane.little.language.data.Value
import scala._

/** Presenter for views representing a [[com.quane.little.language.PrintStatement]].
  *
  * @author Sean Connolly
  */
class PrintStatementPresenter[V <: PrintStatementView](view: V)
  extends StatementPresenter
  with PrintStatementViewPresenter {

  private var _expression: Option[_ <: ExpressionPresenter] = None

  view.registerViewPresenter(this)

  /** Initialize the print presenter.
    *
    * @param p the print expression
    * @return the initialized presenter
    */
  private[presenter] def initialize(p: PrintStatement): PrintStatementPresenter[V] = {
    expression = p.value
    this
  }

  /** Get the print value expression.
    *
    * @return the value expression
    */
  private[presenter] def expression: ExpressionPresenter =
    _expression match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No print expression specified.")
    }

  /** Set the print value expression.
    *
    * @param e the value expression
    */
  private[presenter] def expression_=(e: Expression): Unit = {
    println("Setting print expression: " + e)
    val presenter =
      e match {
        case v: Value =>
          view.createValueStatement().initialize(v)
        case g: GetStatement =>
          view.createGetStatement().initialize(g)
        case f: FunctionReference =>
          view.createFunctionReference().initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    _expression = Some(presenter)
  }

  // TODO skip if already a value statement
  override def requestAddTextLiteral() = _expression = Some(view.createValueStatement())

  // TODO skip if already a get statement
  override def requestAddGetStatement() = _expression = Some(view.createGetStatement())

  // TODO skip if already this function reference
  // TODO we need to look up the function definition
  override def requestAddFunctionReference(name: String) = _expression = Some(view.createFunctionReference())

  /** Compile to a [[com.quane.little.language.PrintStatement]].
    *
    * @return the compiled print statement
    */
  override def compile: PrintStatement = new PrintStatement(expression.compile)

}