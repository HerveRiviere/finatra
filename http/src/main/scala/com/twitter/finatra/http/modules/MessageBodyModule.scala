package com.twitter.finatra.http.modules

import com.google.inject.Module
import com.twitter.finatra.http.internal.marshalling.{
  DefaultMessageBodyReaderImpl,
  DefaultMessageBodyWriterImpl,
  MessageInjectableTypes
}
import com.twitter.finatra.http.marshalling.{DefaultMessageBodyReader, DefaultMessageBodyWriter}
import com.twitter.finatra.jackson.caseclass.InjectableTypes
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

/**
 * Provides implementations for the [[com.twitter.finatra.http.marshalling.DefaultMessageBodyReader]]
 * and the [[com.twitter.finatra.http.marshalling.DefaultMessageBodyWriter]].
 *
 * @see [[com.twitter.finatra.http.marshalling.DefaultMessageBodyReader]]
 * @see [[com.twitter.finatra.http.marshalling.DefaultMessageBodyWriter]]
 * @see [[com.twitter.finatra.http.marshalling.MessageBodyManager]]
 */
object MessageBodyModule extends MessageBodyModule

class MessageBodyModule extends TwitterModule {

  override val modules: Seq[Module] = Seq(MessageBodyFlagsModule)

  override def configure(): Unit = {
    bind[DefaultMessageBodyReader].to[DefaultMessageBodyReaderImpl].in[Singleton]
    bind[DefaultMessageBodyWriter].to[DefaultMessageBodyWriterImpl].in[Singleton]
    // override the default binding of `InjectableTypes` to the more specific `RequestInjectableTypes`
    bindOption[InjectableTypes].setBinding.toInstance(MessageInjectableTypes)
  }
}
