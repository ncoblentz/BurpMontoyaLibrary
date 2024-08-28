package com.nickcoblentz.montoya

import burp.api.montoya.collaborator.DnsQueryType
import burp.api.montoya.collaborator.Interaction
import burp.api.montoya.collaborator.InteractionType
import burp.api.montoya.collaborator.SmtpProtocol
import burp.api.montoya.http.HttpProtocol
import java.net.InetAddress
import java.util.regex.Pattern

class CollabHelperInteractionFilter() {
    var clientIp : String? = null
    var clientPort : String? = null
    var interactionType : InteractionType? = null
    var dnsQueryType : DnsQueryType? = null
    var dnsQueryPattern : String? = null
    var smtpProtocol : SmtpProtocol? = null
    var smtpConversationPattern : String? = null
    var httpProtocol : HttpProtocol? = null
    var httpRequestPattern : String? = null
    var httpUrl : String? = null
    var httpMethod : String? = null
    var interactionHandler : ((Interaction) -> Unit)? = null

    fun withClientIp(clientIp: String) = apply {this.clientIp = clientIp}
    fun withClientPort(clientPort: String) = apply {this.clientPort = clientPort}
    fun withInteractionType(interactionType: InteractionType) = apply {this.interactionType = interactionType}
    fun withDnsQueryType(dnsQueryType: DnsQueryType) = apply {this.dnsQueryType = dnsQueryType}
    fun withDnsQueryPattern(dnsQueryPattern: String) = apply {this.dnsQueryPattern = dnsQueryPattern}
    fun withSmtpProtocol(smtpProtocol: SmtpProtocol) = apply {this.smtpProtocol = smtpProtocol}
    fun withSmtpConversationPattern(smtpConversation: String) = apply {this.smtpConversationPattern = smtpConversation}
    fun withHttpProtocol(httpProtocol: HttpProtocol) = apply {this.httpProtocol = httpProtocol}
    fun withHttpRequestPattern(httpRequestPattern: String) = apply {this.httpRequestPattern = httpRequestPattern}
    fun withHttpUrl(httpUrl: String) = apply {this.httpUrl = httpUrl}
    fun withHttpMethod(httpMethod: String) = apply {this.httpMethod = httpMethod}
    fun withInteractionHandler(interactionHandler : ((Interaction) -> Unit)) = apply {this.interactionHandler = interactionHandler}

    fun handleInteraction(interaction: Interaction) {
        interactionHandler?.let { handler ->
            if(clientIpMatchesOrIsNull(interaction) &&
                clientPortMatchesOrIsNull(interaction) &&
                interactionTypeMatchesOrIsNull(interaction) &&
                dnsQueryTypeMatchesOrIsNull(interaction) &&
                dnsQueryPatternMatchesOrIsNull(interaction) &&
                smtpProtocolMatchesOrIsNull(interaction) &&
                smtpConversationPatternMatchesOrIsNull(interaction) &&
                httpProtocolMatchesOrIsNull(interaction) &&
                httpRequestPatternMatchesOrIsNull(interaction) &&
                httpUrlMatchesOrIsNull(interaction) &&
                httpMethodMatchesOrIsNull(interaction)) {
                handler(interaction)
            }
        }
    }


    fun clientIpMatchesOrIsNull(interaction: Interaction) : Boolean {
        return clientIp==null ||
                interaction.clientIp().equals(InetAddress.getByName(clientIp))
    }

    fun clientPortMatchesOrIsNull(interaction: Interaction) : Boolean {
        return clientPort==null ||
                interaction.clientPort()==clientPort?.toInt()
    }
    fun interactionTypeMatchesOrIsNull(interaction: Interaction) : Boolean {
        return interactionType==null ||
                interaction.type()==interactionType
    }
    fun dnsQueryTypeMatchesOrIsNull(interaction: Interaction) : Boolean {
        return dnsQueryType==null ||
                (interaction.dnsDetails().isPresent && interaction.dnsDetails().get().queryType()==dnsQueryType)
    }
    fun dnsQueryPatternMatchesOrIsNull(interaction: Interaction) : Boolean {
        return dnsQueryPattern==null ||
                (interaction.dnsDetails().isPresent && Pattern.compile(dnsQueryPattern).matcher(interaction.dnsDetails().get().query().toString()).find())
    }
    fun smtpProtocolMatchesOrIsNull(interaction: Interaction) : Boolean {
        return smtpProtocol==null ||
                (interaction.smtpDetails().isPresent && interaction.smtpDetails().get().protocol()==smtpProtocol)
    }
    fun smtpConversationPatternMatchesOrIsNull(interaction: Interaction) : Boolean {
        return smtpConversationPattern==null ||
                (interaction.smtpDetails().isPresent && Pattern.compile(smtpConversationPattern).matcher(interaction.smtpDetails().get().conversation()).find())
    }
    fun httpProtocolMatchesOrIsNull(interaction: Interaction) : Boolean {
        return httpProtocol==null ||
                (interaction.httpDetails().isPresent && interaction.httpDetails().get().protocol() == httpProtocol)
    }
    fun httpRequestPatternMatchesOrIsNull(interaction: Interaction) : Boolean {
        return httpRequestPattern==null ||
                (interaction.httpDetails().isPresent && Pattern.compile(httpRequestPattern).matcher(interaction.httpDetails().get().requestResponse().request().toString()).find())
    }
    fun httpUrlMatchesOrIsNull(interaction: Interaction) : Boolean {
        return httpUrl==null ||
                (interaction.httpDetails().isPresent && interaction.httpDetails().get().requestResponse().request().url() == httpUrl)
    }
    fun httpMethodMatchesOrIsNull(interaction: Interaction) : Boolean {
        return httpMethod==null ||
                (interaction.httpDetails().isPresent && interaction.httpDetails().get().requestResponse().request().method() == httpMethod)
    }
/*

    private fun checkMatch(interaction: Interaction) {
        if ((clientIp == null || clientIp.isNotEmpty()) &&
            (clientPort == null || clientPort.isNotEmpty()) &&
            (interactionType == null || interactionType == InteractionType.SOME_TYPE) &&
            (dnsQueryType == null || dnsQueryType == DnsQueryType.SOME_QUERY) &&
            (dnsQueryPattern == null || dnsQueryPattern.isNotEmpty()) &&
            (smtpProtocol == null || smtpProtocol == SmtpProtocol.SOME_PROTOCOL) &&
            (smtpConversation == null || smtpConversation.isNotEmpty()) &&
            (httpProtocol == null || httpProtocol == HttpProtocol.SOME_PROTOCOL) &&
            (httpRequestPattern == null || httpRequestPattern.isNotEmpty()) &&
            (httpUrl == null || httpUrl.isNotEmpty()) &&
            (httpMethod == null || httpMethod.isNotEmpty())) {
            // All non-null properties meet their respective conditions
            println("All non-null properties meet their respective conditions.")
        }
    }*/
}