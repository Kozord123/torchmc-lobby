package xyz.kozord.torchmclobby.utils;

import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.config.Time;

public class TimeConverterUtil {

    public static Time getTime(Long time) {
        for(Time tim : TorchMCLobby.getInstance().getMessages().getChatTimeConverter().getTimeConv()) {
            if (time >= tim.getMinimumTime()) return tim;
        }
        return null;
    }
}
