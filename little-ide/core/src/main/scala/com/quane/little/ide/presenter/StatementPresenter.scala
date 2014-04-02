package com.quane.little.ide.presenter

import com.quane.little.language.Statement

/** A presenter for views representing a [[com.quane.little.language.Statement]].
  *
  * @author Sean Connolly
  */
trait StatementPresenter extends ExpressionPresenter {

  /** Compile the presented data to a [[com.quane.little.language.Statement]].
    *
    * @return the compiled statement
    */
  override def compile: Statement

}
