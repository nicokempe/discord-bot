package eu.nicokempe.discordbot.command;

import eu.nicokempe.discordbot.animation.AbstractAnimation;
import eu.nicokempe.discordbot.animation.QuestionListAnimation;
import eu.nicokempe.discordbot.animation.QuestionListEntry;
import eu.nicokempe.discordbot.animation.answer.QuestionAnswerTypeCollection;
import eu.nicokempe.discordbot.animation.answer.QuestionAnswerTypeEmail;
import eu.nicokempe.discordbot.animation.answer.QuestionAnswerTypeInt;
import eu.nicokempe.discordbot.animation.answer.QuestionAnswerTypeString;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCommand extends AbstractCommand {

    public TestCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void onExecute(IDiscordUser user, SlashCommandEvent event) {
        AbstractAnimation animation = new QuestionListAnimation(() -> "BEWERBUNGSSYSTEM", () -> "",  () -> "Herzlich Willkommen", user);
        animation.addEntry(new QuestionListEntry<>(
                "ingame-name",
                "Bitte nenne uns zuerst deinen Minecraft Benutzernamen.",
                new QuestionAnswerTypeString()
        ));
        animation.addEntry(new QuestionListEntry<>(
                "private-name",
                "Anschließend benötigen wir deinen vollen Namen (Vor- und Nachname).",
                new QuestionAnswerTypeString()
        ));
        animation.addEntry(new QuestionListEntry<>(
                "age",
                "Gib jetzt dein Alter an",
                new QuestionAnswerTypeInt().range(16, 0)
        ));
        animation.addEntry(new QuestionListEntry<>(
                "email",
                "Jetzt benötigen wir noch eine E-Mail Adresse um dich über den aktuellen Status deiner Bewerbung außerhalb von Discord zu informieren.",
                new QuestionAnswerTypeEmail()
        ));
        animation.addEntry(new QuestionListEntry<>(
                "apply-type",
                "Wähle anschließend den Bereich, für den du dich bewerben möchtest.",
                new QuestionAnswerTypeCollection(Arrays.asList("Builder", "Content", "Designer", "Developer", "Helper", "Partner")).disallowEmpty()
        ));
        animation.addEntryCompletionListener((entry, o) -> {
            if (entry.getKey().equals("apply-type")) {
                String type = ((List<String>) animation.getResult("apply-type")).get(0);
                List<String> areaas = new ArrayList<>();
                switch (type.toLowerCase()) {
                    case "builder" -> {
                        areaas = Arrays.asList("Terrain", "Organics", "Details", "Einrichtung");
                    }
                    case "content" -> {
                        areaas = Arrays.asList("Community Management", "Event Management", "Quality Management", "Marketing", "Schriftliche Konzeption");
                    }
                    case "designer" -> {
                        areaas = Arrays.asList("Modelling", "Editing", "UI", "UX");
                    }
                    case "developer" -> {
                        areaas = Arrays.asList("Spigot", "Proxy", "Web");
                    }
                    case "helper" -> {
                        areaas = Arrays.asList("Chat Moderation", "TeamSpeak Support", "Discord Support", "Website Support");
                    }
                    case "partner" -> {
                        areaas = Arrays.asList("Promotion", "Software", "Hardware", "Finanziell");
                    }
                }
                animation.addEntry(new QuestionListEntry<>(
                        "desired-area",
                        "Wähle deinen favorisierten Bereich.",
                        new QuestionAnswerTypeCollection(areaas)
                ));
                if (!type.equalsIgnoreCase("partner"))
                    animation.addEntriesBefore("about-you", new QuestionListEntry<>(
                            "why",
                            "Weshalb möchtest du dich speziell für diesen Bereich bei uns bewerben?",
                            new QuestionAnswerTypeString()
                    ));
            }
        });
        animation.addEntry(new QuestionListEntry<>(
                "about-you",
                "Dann erzähle uns zunächst etwas über dich!",
                new QuestionAnswerTypeString()
        ));
        animation.addEntry(new QuestionListEntry<>(
                "time",
                "Wie viel Aufwand und die damit verbundene Zeit kannst du in unser Projekt investieren?",
                new QuestionAnswerTypeString()
        ));
        animation.addFinishHandler(() -> {

            System.out.println(animation.getResults());

            user.sendPrivateMessage(new EmbedBuilder().setDescription("Deine Bewerbung wurde erfolgreich abgeschickt.").setColor(Color.green).build());
        });
        animation.start();
        event.replyEmbeds(new EmbedBuilder().setDescription("Bitte gehe in den Privatchat mit mir.").build()).queue();
    }
}
