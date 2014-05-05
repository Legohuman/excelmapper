package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public interface ProcessMessagesHolderAware {

    void setMessageHolder(ProcessMessagesHolder messagesHolder);

    ProcessMessagesHolder getMessageHolder();
}
