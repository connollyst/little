package com.quane.little.ide.dnd

trait DnDItem extends Serializable {

  override def toString: String = getClass.getSimpleName

}

sealed trait ToolType extends DnDItem

object EventToolType extends ToolType

object SetterToolType extends ToolType

object GetterToolType extends ToolType
