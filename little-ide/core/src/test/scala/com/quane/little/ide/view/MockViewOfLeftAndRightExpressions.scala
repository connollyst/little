package com.quane.little.ide.view

trait MockViewOfLeftAndRightExpressions
  extends ViewOfLeftAndRightExpressions
  with MockView {

  def createLeftMath() = new MockMathView

  def createRightMath() = new MockMathView

  def createLeftValueStatement() = new MockValueView

  def createRightValueStatement() = new MockValueView

  def createLeftGetStatement() = new MockGetterView

  def createRightGetStatement() = new MockGetterView

  def createLeftLogicOperation() = new MockLogicalView

  def createRightLogicOperation() = new MockLogicalView

  def createLeftFunctionReference() = new MockFunctionReferenceView

  def createRightFunctionReference() = new MockFunctionReferenceView

}
