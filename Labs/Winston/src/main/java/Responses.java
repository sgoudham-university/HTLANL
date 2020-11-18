import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Responses extends ListenerAdapter {
    List<String> voiceLines = new ArrayList<>();


    public Responses() {
        Collections.addAll(
            voiceLines,
            " Are you with me?",
            " Is this on?",
            " How Embarrassing!",
            " Oh Yeah!"
        );
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        int random = new Random().nextInt(voiceLines.size());

        if (event.getMessage().getContentRaw().toLowerCase().contains("winston")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + voiceLines.get(random)).queue();
        }
    }

}
