package com.quickblox.ui.kit.chatmessage.adapter.listeners;

import com.quickblox.chat.model.QBAttachment;

/**
 * Created by roman on 2/1/17.
 */

public interface QBChatAttachImageClickListener extends QBChatAttachClickListener{

    @Override
    void onLinkClicked(QBAttachment imageAttach, int positionInAdapter);

}
