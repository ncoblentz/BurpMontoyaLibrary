package com.nickcoblentz.montoya

import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.http.message.HttpRequestResponse;

public fun ContextMenuEvent.findRequestResponsesInEitherEvent() : List<HttpRequestResponse> {

    if(this.selectedRequestResponses().isNotEmpty()) {
        return this.selectedRequestResponses()
    }
    else if(this.messageEditorRequestResponse().isPresent && !this.messageEditorRequestResponse().isEmpty) {
        return listOf(this.messageEditorRequestResponse().get().requestResponse())
    }
    return emptyList()
}