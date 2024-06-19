package xyz.kozord.torchmclobby.modules.cooldown;

import xyz.kozord.torchmclobby.TorchMCLobby;

public enum CooldownType {
    DOUBLE_JUMP(TorchMCLobby.getInstance().getConfiguration().getDoubleJumpCooldownDelay()),
    CHAT(TorchMCLobby.getInstance().getConfiguration().getChatCooldown()),
    VISIBILITY(TorchMCLobby.getInstance().getConfiguration().getVisibilityChangeColdown()),
    SELECT_MENU(TorchMCLobby.getInstance().getConfiguration().getSelectMenuCooldown()),
    CHANGE_LOBBY(TorchMCLobby.getInstance().getConfiguration().getLobbyChangeCooldown());

    private final long delay;

    private CooldownType(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return this.delay;
    }
}
