package com.quane.little.game

import com.quane.little.tools.Logging

object TimedUpdater {

}

/** Run the game simulation.
  * TODO: follow thread at http://www.java-gaming.org/index.php?topic=24220.0
  *
  * @author Sean Connolly
  */
abstract class TimedUpdater(perSecond: Double)
  extends Runnable
  with Logging {

  private val TIME_BETWEEN_UPDATES = 1000000000 / perSecond
  private val MAX_UPDATES_BEFORE_RENDER = 5
  private var lastUpdateTime: Double = System.nanoTime()

  private var _isRunning = false

  def isRunning = _isRunning

  def stop(): Unit = _isRunning = false

  def run(): Unit = {
    info("Starting @ " + perSecond + " updates/second..")
    _isRunning = true
    while (_isRunning) {
      var now = System.nanoTime()
      var updateCount = 0
      // Do as many game updates as we need to, potentially playing catchup.
      while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
        update()
        lastUpdateTime += TIME_BETWEEN_UPDATES
        updateCount += 1
      }
      // If for some reason an update takes forever, we don't want to do an insane number of catchups.
      // If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
      if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
        lastUpdateTime = now - TIME_BETWEEN_UPDATES.toLong
      }
      // Yield until it has been at least the target time between renders. This saves the CPU from hogging.
      while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
        Thread.`yield`()
        // This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
        // You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
        // FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
        try {
          Thread.sleep(1)
        }
        now = System.nanoTime()
      }
    }
  }

  def update()

}
