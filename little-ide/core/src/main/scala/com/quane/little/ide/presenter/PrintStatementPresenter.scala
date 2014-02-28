package com.quane.little.ide.presenter

import com.quane.little.ide.view.{PrintStatementViewListener, PrintStatementView}
import com.quane.little.language._
import com.quane.little.language.data.Value
import scala.Some

/** Presenter for views representing a [[com.quane.little.language.PrintStatement]].
  *
  * @author Sean Connolly
  */
class PrintStatementPresenter[V <: PrintStatementView](view: V)
  extends StatementPresenter
  with PrintStatementViewListener {

  private var _expression: Option[ExpressionPresenter] = None

  view.addViewListener(this)

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
          val value = view.createValueStatement()
          value.value = v.asText
          value
        case g: GetStatement =>
          val get = view.createGetStatement()
          get.name = g.name
          get
        case f: FunctionReference =>
          view.createFunctionReference().initialize(f)
        case _ => throw new IllegalAccessException("Cannot add " + e)
      }
    _expression = Some(presenter)
  }

  override def onValueChange(p: ExpressionPresenter) = _expression = Some(p)

  /** Compile to a [[com.quane.little.language.PrintStatement]].
    *
    * @param scope the scope in which to compile
    * @return the compiled print statement
    */
  override def compile(scope: Scope): PrintStatement =
    new PrintStatement(expression.compile(scope))

}