package com.nickcoblentz.montoya

import burp.api.montoya.MontoyaApi
import burp.api.montoya.collaborator.CollaboratorClient
import burp.api.montoya.collaborator.Interaction
import burp.api.montoya.collaborator.InteractionType
import burp.api.montoya.collaborator.SecretKey
import burp.api.montoya.extension.ExtensionUnloadingHandler
import burp.api.montoya.scanner.audit.issues.AuditIssue
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity
import com.nickcoblentz.montoya.settings.ExtensionSettingSaveLocation
import com.nickcoblentz.montoya.settings.GenericExtensionSetting
import com.nickcoblentz.montoya.settings.StringExtensionSetting
import java.time.Duration
import java.util.*
import java.util.regex.Pattern

class CollabHelper(private val api : MontoyaApi) {

    private lateinit var collaboratorClient: CollaboratorClient
    private var shouldPollForInteactions = true
    private var pollCollabThread : Thread? = null


    public lateinit var collabPayloadsSetting : StringExtensionSetting
    public lateinit var collabSecretSetting: StringExtensionSetting
    public lateinit var extensionSettings : List<StringExtensionSetting>
    public var pollSeconds : Long = 5
    public var interactionObservers = mutableListOf<(Interaction) -> Unit>()

    companion object {
        private const val PLUGIN_NAME: String = "Collab Helper Library"
    }

    init {
        collabPayloadsSetting = StringExtensionSetting(
            // pass the montoya API to the setting
            api,
            // Give the setting a name which will show up in the Swing UI Form
            "Collaborator Server",
            // Key for where to save this setting in Burp's persistence store
            "$PLUGIN_NAME.payload",
            // Default value within the Swing UI Form
            "",
            // Whether to save it for this specific "PROJECT" or as a global Burp "PREFERENCE"
            ExtensionSettingSaveLocation.PROJECT,
            true
        )

        collabSecretSetting = StringExtensionSetting(
            // pass the montoya API to the setting
            api,
            // Give the setting a name which will show up in the Swing UI Form
            "Collaborator Secret",
            // Key for where to save this setting in Burp's persistence store
            "$PLUGIN_NAME.secret",
            // Default value within the Swing UI Form
            "",
            // Whether to save it for this specific "PROJECT" or as a global Burp "PREFERENCE"
            ExtensionSettingSaveLocation.PROJECT,
            true
        )

        extensionSettings = listOf(collabPayloadsSetting,collabSecretSetting)

        if(collabSecretSetting.currentValue.isBlank()) {
            collaboratorClient = api.collaborator().createClient()
            collabSecretSetting.currentValue=collaboratorClient.secretKey.toString()
            collabSecretSetting.save()

        }
        else {
            collaboratorClient = api.collaborator().restoreClient(SecretKey.secretKey(collabSecretSetting.currentValue))
        }

        if(collabPayloadsSetting.currentValue.isBlank()) {
            collabPayloadsSetting.currentValue = collaboratorClient.generatePayload().toString()
            collabPayloadsSetting.save()
        }

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

