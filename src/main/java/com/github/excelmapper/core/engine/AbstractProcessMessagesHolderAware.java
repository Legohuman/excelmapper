package com.github.excelmapper.core.engine;

/**
 * User: Dmitry Levin
 * Date: 05.05.14
 */
public abstract class AbstractProcessMessagesHolderAware implements ProcessMessagesHolderAware {

    private ProcessMessagesHolder messagesHolder;

    @Override
    public void setMessageHolder(ProcessMessagesHolder messagesHolder) {
        this.messagesHolder = messagesHolder;
    }

    @Override
    public ProcessMessagesHolder getMessageHolder() {
        return messagesHolder;
    }
}
