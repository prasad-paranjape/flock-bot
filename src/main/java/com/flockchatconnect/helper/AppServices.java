package com.flockchatconnect.helper;

/**
 * Created by shashwat.ku on 25/9/16.
 */
public enum AppServices {
    APP_INSTALL
            {
                public String toString()
                {
                    return "app.install";
                }
            },
    APP_UNINSTALL
            {
                public String toString()
                {
                    return "app.uninstall";
                }
            },
    CHAT_RECEIVE
            {
                public String toString()
                {
                    return "chat.receiveMessage";
                }
            },
    SLASH_COMMAND
            {
                public String toString()
                {
                    return "client.slashCommand";
                }
            }
}
