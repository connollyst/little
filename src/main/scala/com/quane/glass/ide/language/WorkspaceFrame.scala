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

class WorkspaceFrame(title: String)
        extends InternalFrame
        with Logging {

    super.title = title
    visible = true
    resizable = true
    closable = true
    maximizable = true
    selected = true
    preferredSize = new Dimension(300, 150)

    add(new WorkspaceFramePanel(this))

}

class WorkspaceFramePanel(frame: InternalFrame)
        extends FunctionPanel {

    reactions += {
        case event: DropExpressionEvent =>
            // TODO there's probably a more efficient way to do this
            frame.pack
    }

}
