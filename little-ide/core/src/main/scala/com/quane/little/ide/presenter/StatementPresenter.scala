package com.quane.little.ide.presenter

import com.quane.little.language.{Scope, Statement}

/** A presenter for views representing a [[com.quane.little.language.Statement]]
  *
  * @author Sean Connolly
  */
trait StatementPresenter extends ExpressionPresenter {

  /** Compile the presented data to a [[com.quane.little.language.Statement]]
    *
    * @param scope the scope in which to compile
    * @return the compiled statement
    */
  override def compile(scope: Scope): Statement

}
