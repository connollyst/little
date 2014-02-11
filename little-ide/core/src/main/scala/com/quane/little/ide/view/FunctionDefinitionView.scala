package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer

trait FunctionDefinitionView {

  protected val listeners = new ListBuffer[FunctionDefinitionViewListener]

  def addViewListener(l: FunctionDefinitionViewListener) = listeners += l

}

trait FunctionDefinitionViewListener