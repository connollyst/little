package com.quane.glass.ide.language

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
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.DragAndDropItem
import com.quane.glass.ide.DragAndDropTarget
import com.quane.glass.ide.DragOutEvent
import com.quane.glass.ide.DragOverEvent
import com.quane.glass.ide.DropExpressionEvent
import com.quane.glass.ide.IDE
import com.quane.glass.ide.swing.HighlightableComponent
import com.quane.glass.ide.SetterToolType
import com.quane.glass.ide.StepAddedEvent
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
            log.info("StepAddedEvent")
            pack
    }
}
