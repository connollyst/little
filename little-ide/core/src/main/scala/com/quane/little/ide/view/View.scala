package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer


trait View[L <: ViewListener] {

  protected val viewListeners = new ListBuffer[L]

  def addViewListener(l: L) = viewListeners += l

}

trait ViewListener