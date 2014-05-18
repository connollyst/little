package com.quane.little.ide.view

trait MockViewOfLeftAndRightExpressions
  extends ViewOfLeftAndRightExpressions
  with MockView {

  def createLeftMath() = new MockMathView

  def createRightMath() = new MockMathView

  def createLeftLiteral() = new MockValueView

  def createRightLiteral() = new MockValueView

  def createLeftGetter() = new MockGetterView

  def createRightGetter() = new MockGetterView

  def createLeftLogic() = new MockLogicView

  def createRightLogic() = new MockLogicView

  def createLeftFunctionReference() = new MockFunctionReferenceView

  def createRightFunctionReference() = new MockFunctionReferenceView

}
