package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface ProcessMessagesHolder {

    ProcessMessagesHolder NOP = new ProcessMessagesHolder() {
        @Override
        public void add(ProcessMessage processMessage) {
            //nop
        }

        @Override
        public int count() {
            return 0;
        }

        @Override
        public ProcessMessage get(int index) {
            return null;
        }
    };

    void add(ProcessMessage processMessage);

    int count();

    ProcessMessage get(int index);
}
