package at.roteskreuz.stopcorona.screens.base

import at.roteskreuz.stopcorona.model.entities.infection.message.MessageType
import at.roteskreuz.stopcorona.model.repositories.InfectionMessengerRepository
import at.roteskreuz.stopcorona.model.repositories.NotificationsRepository
import at.roteskreuz.stopcorona.model.repositories.QuarantineRepository
import at.roteskreuz.stopcorona.model.repositories.QuarantineStatus
import at.roteskreuz.stopcorona.skeleton.core.model.helpers.AppDispatchers
import at.roteskreuz.stopcorona.skeleton.core.screens.base.viewmodel.ScopedViewModel
import kotlinx.coroutines.launch

/**
 * Special viewModel for managing debug tasks.
 *
 * The content in this class might not have fulfill our code quality standards. It's just for debugging.
 */
class DebugViewModel(
    appDispatchers: AppDispatchers,
    private val infectionMessengerRepository: InfectionMessengerRepository,
    private val notificationsRepository: NotificationsRepository,
    private val quarantineRepository: QuarantineRepository
) : ScopedViewModel(appDispatchers) {


    fun displayInfectionNotification(infectionLevel: MessageType.InfectionLevel) {
        launch {
            notificationsRepository.displayInfectionNotification(infectionLevel)
        }
    }

    fun displaySelfRetestNotification() {
        launch {
            notificationsRepository.displaySelfRetestNotification()
        }
    }

    fun displaySomeoneHasRecoveredNotification() {
        launch {
            infectionMessengerRepository.setSomeoneHasRecovered()
            notificationsRepository.displaySomeoneHasRecoveredNotification()
        }
    }

    fun displayEndQuarantineNotification() {
        launch {
            quarantineRepository.setShowQuarantineEnd()
            notificationsRepository.displayEndQuarantineNotification()
        }
    }

    fun getQuarantineStatus(): QuarantineStatus {
        return quarantineRepository.observeQuarantineState().blockingFirst()
    }

    fun addOutgoingMessageRed() {
        launch {
            // DEBUG-TODO 11-Jun-2020 mihbat: This method was intended for debugging the
            //        reporting of a red diagnostic.

            quarantineRepository.reportMedicalConfirmation()
        }
    }

    fun addOutgoingMessageYellow() {
        launch {
            // DEBUG-TODO 11-Jun-2020 mihbat: This method was intended for debugging the
            //        reporting of a yellow diagnostic.

            quarantineRepository.reportPositiveSelfDiagnose()
        }
    }
}