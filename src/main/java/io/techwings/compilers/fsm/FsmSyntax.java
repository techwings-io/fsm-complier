package io.techwings.compilers.fsm;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FsmSyntax {
    public List<Header> headers = new ArrayList<>();
    public List<Transition> transitions = new ArrayList<>();
    public List<SyntaxError> errors = new ArrayList<>();
    public boolean done = false;

    public static class Header {
        public String name;
        public String value;
    }

    public static class Transition {
        public StateSpec state;
        public List<SubTransition> subTransitions = new ArrayList<>();
    }

    public static class StateSpec {
        String name;
        String superState;
        String entryAction;
        String exitAction;
        boolean abstractState;
    }

    public static class SubTransition {
        String event;
        String nextState;
        List<String> actions = new ArrayList<>();

        public SubTransition(String event) {
            this.event = event;
        }
    }

    public static class SyntaxError {
        Type type;
        String msg;
    }
}
