package com.quane.little.ide.dnd

trait DragAndDropItem extends Serializable {

  override def toString: String = {
    getClass.getSimpleName
  }

}

sealed trait ToolType extends DragAndDropItem

object EventToolType extends ToolType

object SetterToolType extends ToolType

object GetterToolType extends ToolType

object GetBoolToolType extends ToolType

object GetNumberToolType extends ToolType

object GetTextToolType extends ToolType