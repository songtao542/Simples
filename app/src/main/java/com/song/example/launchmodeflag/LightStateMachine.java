package com.song.example.launchmodeflag;

import android.os.Message;

import com.android.statemachine.*;

/**
 * Created by le on 12/26/16.
 */

public class LightStateMachine extends com.android.statemachine.StateMachine {


    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;
    public static final int STATE_TURNING_ON = 2;
    public static final int STATE_TURNING_OFF = 3;

    protected LightStateMachine(String name) {
        super("LightStateMachine");
    }


    class OnState extends com.android.statemachine.State {
        @Override
        public void enter() {
            super.enter();
        }

        @Override
        public void exit() {
            super.exit();
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case STATE_OFF:

                case STATE_ON:

                case STATE_TURNING_ON:

                case STATE_TURNING_OFF:
            }
            return true;
        }
    }

    class OffState extends com.android.statemachine.State {
        @Override
        public void enter() {
            super.enter();
        }

        @Override
        public void exit() {
            super.exit();
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case STATE_OFF:

                case STATE_ON:

                case STATE_TURNING_ON:

                case STATE_TURNING_OFF:
            }
            return true;
        }
    }

    class TurningOnState extends com.android.statemachine.State {
        @Override
        public void enter() {
            super.enter();
        }

        @Override
        public void exit() {
            super.exit();
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case STATE_OFF:

                case STATE_ON:

                case STATE_TURNING_ON:

                case STATE_TURNING_OFF:
            }
            return true;
        }
    }

    class TurningOffState extends com.android.statemachine.State {
        @Override
        public void enter() {
            super.enter();
        }

        @Override
        public void exit() {
            super.exit();
        }

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case STATE_OFF:

                case STATE_ON:

                case STATE_TURNING_ON:

                case STATE_TURNING_OFF:
            }
            return true;
        }
    }
}
