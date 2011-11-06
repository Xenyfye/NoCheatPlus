package cc.co.evenprime.bukkit.nocheat.checks.blockbreak;

import java.util.Locale;

import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.actions.types.ActionWithParameters.WildCard;
import cc.co.evenprime.bukkit.nocheat.checks.BlockBreakCheck;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;
import cc.co.evenprime.bukkit.nocheat.config.cache.CCBlockBreak;
import cc.co.evenprime.bukkit.nocheat.data.BaseData;
import cc.co.evenprime.bukkit.nocheat.data.BlockBreakData;

public class NoswingCheck extends BlockBreakCheck {

    public NoswingCheck(NoCheat plugin) {
        super(plugin, "blockbreak.noswing", Permissions.BLOCKBREAK_NOSWING);
    }

    public boolean check(NoCheatPlayer player, BlockBreakData data, CCBlockBreak cc) {

        boolean cancel = false;

        BaseData d = player.getData();

        // did he swing his arm before?
        if(d.armswung) {
            d.armswung = false;
            data.noswingVL *= 0.90D;
        } else {
            data.noswingVL += 1;

            cancel = executeActions(player, cc.noswingActions.getActions(data.noswingVL));
        }

        return cancel;
    }

    public boolean isEnabled(CCBlockBreak cc) {
        return cc.noswingCheck;
    }
    
    public String getParameter(WildCard wildcard, NoCheatPlayer player) {

        switch (wildcard) {

        case VIOLATIONS:
            return String.format(Locale.US, "%d", player.getData().blockbreak.noswingVL);

        default:
            return super.getParameter(wildcard, player);

        }
    }

}
