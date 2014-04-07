package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import com.quane.little.tools.json.LittleJSON

/**
 *
 *
 * @author Sean Connolly
 */
trait MongoRepository[O] {

  private val little = new LittleJSON()

  def insert(collection: MongoCollection, o: O) = {
    val json = little.serialize(o)
    JSON.parse(json) match {
      case dbObject: DBObject =>
        val writeResult = collection += dbObject
        if (writeResult.getError != null) {
          throw new RuntimeException(
            "Failed to write object to database: " + writeResult.getError
          )
        }
      case _ => throw new RuntimeException("Expected DBObject for " + json)
    }
  }

}
