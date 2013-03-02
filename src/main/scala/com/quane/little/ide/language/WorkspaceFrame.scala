package com.quane.little.ide.language

import java.awt.Dimension
import scala.collection.mutable.ListBuffer
import scala.swing.BoxPanel
import scala.swing.InternalFrame
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.google.common.eventbus.Subscribe
import com.quane.little.language.Expression
import com.quane.little.ide.DragAndDropItem
import com.quane.little.ide.DragAndDropTarget
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.IDE
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.SetterToolType
import com.quane.little.ide.StepAddedEvent
import scala.swing.Panel

class WorkspaceFrame(title: String, panel: FunctionPanel)
        extends InternalFrame
        with Logging {

    super.title = title
    visible = true
    resizable = true
    closable = true
    maximizable = true
    selected = true
    preferredSize = new Dimension(300, 150)

    add(panel)

    listenTo(panel)
    reactions += {
        case event: StepAddedEvent =>
            pack
    }
}
