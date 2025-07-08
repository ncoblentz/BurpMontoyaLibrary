package com.nickcoblentz.montoya

import burp.api.montoya.MontoyaApi
import burp.api.montoya.collaborator.CollaboratorClient
import burp.api.montoya.collaborator.Interaction
import burp.api.montoya.collaborator.SecretKey
import burp.api.montoya.extension.ExtensionUnloadingHandler
import burp.api.montoya.ui.settings.SettingsPanelBuilder
import burp.api.montoya.ui.settings.SettingsPanelPersistence
import com.nickcoblentz.montoya.settings.PanelSettingsDelegate
import java.time.Duration
import java.util.*


class CollabHelperPanelSettings(hostname : String, secret : String) {
    val settingsPanelBuilder : SettingsPanelBuilder = SettingsPanelBuilder.settingsPanel()
        .withPersistence(SettingsPanelPersistence.PROJECT_SETTINGS)
        .withTitle(CollabHelper.PLUGIN_NAME)
        .withDescription("Do not modify these settings. This is intended to be read only.")
        .withKeywords("Collab")

    private val settingsManager = PanelSettingsDelegate(settingsPanelBuilder)

    val collabPayloadsSetting: String by settingsManager.stringSetting("Collaborator Server Hostname", hostname)
    val collabSecretSetting: String by settingsManager.stringSetting("Collaborator Secret", secret)

    val settingsPanel = settingsManager.buildSettingsPanel()
}

class CollabHelper(private val api : MontoyaApi) {

    private var collaboratorClient: CollaboratorClient = api.collaborator().createClient()
    private var shouldPollForInteactions = true
    private var pollCollabThread : Thread? = null

    public var pollSeconds : Long = 5
    public var interactionObservers = mutableListOf<(Interaction) -> Unit>()

    private val collabHelperPanelSettings : CollabHelperPanelSettings

    companion object {
        const val PLUGIN_NAME: String = "Collab Helper Library"
    }

    init {
        collabHelperPanelSettings = CollabHelperPanelSettings(collaboratorClient.generatePayload().toString(),collaboratorClient.secretKey.toString())
        collaboratorClient = api.collaborator().restoreClient(SecretKey.secretKey(collabHelperPanelSettings.collabSecretSetting))

        api.extension().registerUnloadingHandler(ExtensionUnloadingHandler {
            api.logging().logToOutput("Shutting down virtual thread")
            shouldPollForInteactions=false
            pollCollabThread?.join()
        })

        pollCollabThread = Thread.ofVirtual().name("Poll Collaborator").start {

            val threadId = UUID.randomUUID().toString()
            api.logging().logToOutput("Beginning: $threadId")
            while (shouldPollForInteactions) {
                api.logging().logToOutput("In loop: $threadId")
                val allInteractions = collaboratorClient.allInteractions
                api.logging().logToOutput("${allInteractions.size} Total interactions")

                for(interaction in allInteractions) {
                    api.logging().logToOutput("${interaction.timeStamp()} ${interaction.type()} ${interaction.clientIp()} ${interaction.clientPort()}")

                    for(observer in interactionObservers) {
                        observer(interaction)

                    }
                }
                Thread.sleep(Duration.ofSeconds(pollSeconds))
            }
            api.logging().logToOutput("Exited: $threadId")
        }
    }

    fun shutdown() {
        api.logging().logToOutput("Shutting down virtual thread")
        shouldPollForInteactions=false
        pollCollabThread?.join()
    }
}

