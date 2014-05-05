package ru.dlevin.excelmapper.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 05.05.14
 */
public class SimpleProcessMessagesHolder implements ProcessMessagesHolder {

    private List<ProcessMessage> messages = new ArrayList<ProcessMessage>();

    public SimpleProcessMessagesHolder() {
    }

    @Override
    public void add(ProcessMessage processMessage) {
        messages.add(processMessage);
    }

    @Override
    public int count() {
        return messages.size();
    }

    @Override
    public ProcessMessage get(int index) {
        return messages.get(index);
    }
}
