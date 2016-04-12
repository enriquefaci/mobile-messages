/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.mobilemessages.services

import uk.gov.hmrc.api.sandbox.FileResource
import uk.gov.hmrc.mobilemessages.config.MicroserviceAuditConnector
import uk.gov.hmrc.mobilemessages.connector._
import uk.gov.hmrc.play.audit.model.DataEvent
import uk.gov.hmrc.play.http.HeaderCarrier

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait MobileMessagesService {
  def ping()(implicit hc:HeaderCarrier): Future[Boolean]
}

trait LiveMobileMessagesService extends MobileMessagesService with Auditor {
  def authConnector: AuthConnector

  def ping()(implicit hc:HeaderCarrier): Future[Boolean]

}

object SandboxMobileMessagesService extends MobileMessagesService with FileResource {

  def ping()(implicit hc:HeaderCarrier): Future[Boolean] = Future.successful(true)

}

object LiveMobileMessagesService extends LiveMobileMessagesService {
  override val authConnector: AuthConnector = AuthConnector

  val auditConnector: AuditConnector = MicroserviceAuditConnector

  def ping()(implicit hc:HeaderCarrier): Future[Boolean] = Future.successful(true)
}