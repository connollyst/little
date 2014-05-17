package com.quane.little.ide.view

import com.quane.little.language.math.BasicMathOperation

class MockMathView extends MathView with MockView {

  def setOperation(operation: BasicMathOperation) = Unit


  def createLeftValueStatement() = new MockValueView

  def createRightValueStatement() = new MockValueView


  def createLeftGetStatement() = new MockGetterView

  def createRightGetStatement() = new MockGetterView


  def createLeftFunctionReference() = new MockFunctionReferenceView

  def createRightFunctionReference() = new MockFunctionReferenceView

}
