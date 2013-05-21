package com.quane.little.ide.language

import java.awt.Dimension
import scala.swing.InternalFrame
import org.eintr.loglady.Logging
import com.quane.little.ide.StepAddedEvent

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
