package com.jxust.btcexplorer.btcrpc.entity;

public class RpcInfo {
    private String logpath;
    private active_commands active_commands;

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public com.jxust.btcexplorer.btcrpc.entity.active_commands getActive_commands() {
        return active_commands;
    }

    public void setActive_commands(com.jxust.btcexplorer.btcrpc.entity.active_commands active_commands) {
        this.active_commands = active_commands;
    }
}

    class active_commands {
        private String method;
        private int duration;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

