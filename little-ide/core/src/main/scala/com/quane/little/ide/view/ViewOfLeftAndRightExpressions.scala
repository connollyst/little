package com.quane.little.ide.view

/** A view of an operation on a left and a right operand.
  *
  * @author Sean Connolly
  */
trait ViewOfLeftAndRightExpressions {

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createLeftLiteral(): ValueView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createRightLiteral(): ValueView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createLeftGetter(): GetterView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createRightGetter(): GetterView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.MathView]].
    *
    * @return the view which was created
    */
  def createLeftMath(): MathView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.MathView]].
    *
    * @return the view which was created
    */
  def createRightMath(): MathView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.LogicView]].
    *
    * @return the view which was created
    */
  def createLeftLogic(): LogicView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.LogicView]].
    *
    * @return the view which was created
    */
  def createRightLogic(): LogicView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was created
    */
  def createLeftFunctionReference(): FunctionReferenceView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.FunctionReferenceView]].
    *
    * @return the view which was created
    */
  def createRightFunctionReference(): FunctionReferenceView

}
