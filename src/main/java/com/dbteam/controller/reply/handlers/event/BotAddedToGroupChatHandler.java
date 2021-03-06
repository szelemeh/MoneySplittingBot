package com.dbteam.controller.reply.handlers.event;

import com.dbteam.exception.GroupNotFoundException;
import com.dbteam.model.telegram.CallbackData;
import com.dbteam.model.telegram.Event;
import com.dbteam.model.db.Group;
import com.dbteam.service.GroupService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

@Service
public class BotAddedToGroupChatHandler implements EventHandler {

    private static final String MESSAGE_GREETING =
            "Hello, thanks for adding me to your group. " +
                    "I won't let you down! " +
                    "Please, click the button below to join " +
                    "group expenses management.";

    private final GroupService groupService;

    public BotAddedToGroupChatHandler(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public SendMessage handleEvent(Update update) {
        if (!groupAlreadyExists(update)) {
            groupService.addGroup(new Group(update.getMessage().getChatId(), update.getMessage().getChat().getTitle(),Collections.emptyList()));
        }

        InlineKeyboardButton button = new InlineKeyboardButton();
        button
                .setText("I am in!")
                .setCallbackData(CallbackData.ADD_USER_TO_GROUP.getValue());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(List.of(List.of(button)));

        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(MESSAGE_GREETING)
                .setReplyMarkup(markup);
    }

    private boolean groupAlreadyExists(Update update) {
        try {
            groupService.findGroupById(update.getMessage().getChatId());
            return true;
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Event eventToHandle() {
        return Event.BOT_ADDED_TO_GROUP_CHAT;
    }
}
