package com.quane.little.ide.view


trait GetStatementView extends View[GetStatementViewListener] {

  def setName(name: String): Unit

}

trait SetStatementView extends View[SetStatementViewListener] {

  def setName(name: String): Unit

  def setValue(value: String): Unit

}

trait PrintStatementView extends View[PrintStatementViewListener] {

  def setValue(value: String): Unit

}

trait GetStatementViewListener extends ViewListener {

  def nameChanged(name: String): Unit

}

trait SetStatementViewListener extends ViewListener {

  def nameChanged(name: String): Unit

}

trait PrintStatementViewListener extends ViewListener {

  def valueChanged(value: String): Unit

}