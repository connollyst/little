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

  private[presenter] def expression: ExpressionPresenter = {
    _expression match {
      case e: Some[ExpressionPresenter] => e.get
      case _ => throw new IllegalAccessException("No print expression set.")
    }
  }

  /** Set the value expression.
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
          val fun = view.createFunctionReference()
          fun.name = f.name
          // TODO set function name & args
          fun
        case _ => throw new IllegalAccessException("Cannot add " + e)
      }
    expression_=(presenter)
  }

  /** Set the value presenter.
    *
    * @param expression the value presenter
    */
  private[presenter] def expression_=(expression: ExpressionPresenter): Unit = {
    _expression = Some(expression)
    // TODO notify view?
  }

  override def setExpression(p: ExpressionPresenter) = _expression = Some(p)

  /** Compile to a [[com.quane.little.language.PrintStatement]].
    *
    * @param scope the scope in which to compile
    * @return the compiled print statement
    */
  override def compile(scope: Scope): PrintStatement =
    new PrintStatement(_expression.get.compile(scope))

}