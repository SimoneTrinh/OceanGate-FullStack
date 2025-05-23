package CryptoUserInfo.components;

import javax.swing.*;
import java.util.Objects;

public enum IconResources {
    BITCOIN("/icons/bitcoin.png"),
    ETHEREUM("/icons/ethereum.png"),
    USDT("/icons/usdt.png"),
    USER("/icons/user.png"),
    XRP("/icons/xrp.png"),;

    private final String path;

    IconResources(String path) {
        this.path = path;
    }

    public ImageIcon getIcon() {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
    }
}