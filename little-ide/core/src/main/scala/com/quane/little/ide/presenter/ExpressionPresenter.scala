package com.quane.little.ide.presenter

import com.quane.little.language.{Expression, Scope}

/** A presenter for views representing an [[com.quane.little.language.Expression]]
  *
  * @author Sean Connolly
  */
trait ExpressionPresenter {

  /** Compile the presented data to an [[com.quane.little.language.Expression]]
    *
    * @param scope the scope in which to compile
    * @return the compiled expression
    */
  def compile(scope: Scope): Expression

}
