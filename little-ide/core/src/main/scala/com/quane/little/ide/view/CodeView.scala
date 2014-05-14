package com.quane.little.ide.view

import com.quane.little.language.{Statement, Expression}

sealed trait CodeView[P <: CodeViewPresenter] extends View[P]

sealed trait CodeViewPresenter extends ViewPresenter

sealed trait EvaluableCodeView[P <: EvaluableCodeViewPresenter] extends CodeView[P]

sealed trait EvaluableCodeViewPresenter extends CodeViewPresenter

/** A view for a component representing an [[com.quane.little.language.Expression]].
  *
  * @author Sean Connolly
  */
trait ExpressionView[P <: ExpressionViewPresenter] extends EvaluableCodeView[P]

/** A presenter for views representing an [[com.quane.little.language.Expression]].
  *
  * @author Sean Connolly
  */
trait ExpressionViewPresenter extends EvaluableCodeViewPresenter {

  /** Compile the presented data to an [[com.quane.little.language.Expression]].
    *
    * @return the compiled expression
    */
  def compile(): Expression

}

/** A view for a component representing a [[com.quane.little.language.Statement]].
  *
  * @author Sean Connolly
  */
trait StatementView[P <: StatementViewPresenter] extends EvaluableCodeView[P]

/** A presenter for views representing a [[com.quane.little.language.Statement]].
  *
  * @author Sean Connolly
  */
trait StatementViewPresenter extends EvaluableCodeViewPresenter {

  /** Compile the presented data to a [[com.quane.little.language.Statement]].
    *
    * @return the compiled statement
    */
  def compile(): Statement

}