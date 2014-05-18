package com.quane.little.ide.view

/**
 *
 *
 * @author Sean Connolly
 */
trait ViewOfLeftAndRightExpressions {

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

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createLeftValueStatement(): ValueView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.ValueView]].
    *
    * @return the view which was created
    */
  def createRightValueStatement(): ValueView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createLeftGetStatement(): GetterView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.GetterView]].
    *
    * @return the view which was created
    */
  def createRightGetStatement(): GetterView

  /** Replace the existing left value expression with a [[com.quane.little.ide.view.LogicalView]].
    *
    * @return the view which was created
    */
  def createLeftLogicOperation(): LogicalView

  /** Replace the existing right value expression with a [[com.quane.little.ide.view.LogicalView]].
    *
    * @return the view which was created
    */
  def createRightLogicOperation(): LogicalView

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
