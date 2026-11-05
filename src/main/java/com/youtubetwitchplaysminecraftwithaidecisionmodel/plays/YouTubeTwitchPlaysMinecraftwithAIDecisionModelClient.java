package com.youtubetwitchplaysminecraftwithaidecisionmodel.plays;

// Decision Models 
// Decision Models 
// Decision Models 
// https://docs.liquid.ai/lfm/models/d1-2b
// https://huggingface.co/LiquidAI/d1-2B
// Decision Models 
// Decision Models 

/*
✅ keyUp / backKey / keyLeft / keyRight — Movement controls (W, S, A, D)
✅ keyJump — Jump
✅ keyShift — Sneak (Crouch)
💜
TODO Hotbar 
keyAttack — Attack / Break block (Left-click)
keySprint — Sprint
keyInventory — Open Inventory
keyChat — Open chat
keyCommand — Open command prompt
keySocialInteractions — Open social interactions screen (P)
keyPlayerList — Player list (Tab)
keyScreenshot — Take screenshot (F2)
keyTogglePerspective — F5 camera view toggle
keySmoothCamera — Cinematic camera toggle
keyFullscreen — Toggle fullscreen (F11)
keySpectatorOutlines — Highlight players in spectator
*/
// TODO mc.options.keyInventory.setDown(true);
// TODO KeyMapping.click(mc.options.keyInventory.getKey()); // Increments the internal click counter
// TODO
// TODO  HOLD SHIELD
// TODO DRAW OVERLAY
// TODO
// TODO CLick COunter
// TODO
// TODO
// TODO  LOOK DOWN
// TODO Block Count - every   block broken increments counter
// TODO
// TODO STOP command
// TODO TURN ARUND command
// TODO /gift bread 3
// TODO when death block randomizes
// TODO Add Death Counter
// TODO Change Loacation command x y coords GOTO TP Telepoprt



// TODO
// TODO Spaw mobs
// TODO
// TODO
// TODO lookup lookright lookleft lookdown
// TODO Movement Votinig - when ppl trigger game command to move, 
// TODO hello youtube and twtich DVD Logo
// TODO add parser to set number of BLOCKS player moves
// TODO ✅ only prune the list when .add occurs
// TODO for loop on playerCommands and execyte all commands at same time
// TODO decremetn playerCommands ticks and then remove at 0 tick remaining
// TODO - idea from quickfix - website where you can control the game

// TODO op.awMelon: Birdflop is a website that shows you which colour corresponds to which symbol. I thought it might be helpful! You should also check out resources for mods, as you can use custom fonts, textures, and even custom emojis.
// TODO Name change dictinoary
// TODO Translate to english
// TODO ✅ per-user color lookup hashmapp

// TODO public static final TextColor GOLD = TextColor.fromRgb(0xD4AF37);
// TODO add NeoForge Logo
// TODO bits - when donos Deena - do somethign in the game

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import net.minecraft.util.GsonHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.client.Options;
import net.minecraft.client.player.KeyboardInput;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Input;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, value = Dist.CLIENT)
public class YouTubeTwitchPlaysMinecraftwithAIDecisionModelClient {
    public YouTubeTwitchPlaysMinecraftwithAIDecisionModelClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("HELLO FROM CLIENT SETUP");
        YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    private static String timetoken = "1000";
    private static boolean subscribed = false;
    private static List<String> chatMessages = new ArrayList<>();
    private static Map<String, String> playerColors = Map.of(
        "@AronDenBasta", "§2",
        "@UnKitty2", "§9",
        "@QuickfixKBPAWER", "§5",
        "@ilovemydogKBPAWER", "§5",
        "@catguyYT", "§a",
        "lecaptnsky", "§3",
        "stephenlb", "§4",
        "@StephenBlum", "§4",
        "@Neon_AI_Lab", "§1",
        "@Codemitsu888", "§7"
    );
    /// @QuickfixKBPAWER §6§lMINECRAFT COLORS: §r§00 §1DarkBlue §2Green §3Cyan §4Red §5Purple §6Gold §7Gray §8DarkGray §9Blue §aLime §bAqua §cRed §dPink §eYellow §fWhite

    private static List<String> ACTION_COMMANDS = List.of(
        "swap", "drop", "use", "pick"
    );
    static void actionCommadns(Minecraft mc, String message) {
        String msg = message.toLowerCase();

        // Action commands
        for (String cmd: ACTION_COMMANDS) {
            if (!msg.contains(cmd)) continue;

            switch (cmd) {
                case "swap" -> KeyMapping.click(mc.options.keyInventory.getKey());
                case "drop" -> playerCommands.put("forward", 0);
                case "use"  -> playerCommands.put("right", 0);
                case "pick" -> playerCommands.put("left", 0);
            }
        }
    }


    static void subscribe() {
        subscribed = true;
        String endpoint = "https://ps.pndsn.com";
        String channel = System.getenv("YOUTUBE_TWITCH_CHANNEL");
        String url = endpoint + "/subscribe/demo/" + channel + "/0/" + timetoken;

        Minecraft mc1 = Minecraft.getInstance();
	HttpClient client = HttpClient.newHttpClient();
	HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(url))
		.GET()
		.build();
        
        // [[{"user":"@QuickfixKBPAWER","message":"erik say hi","platform":"youtube_account"},{"user":"@kailevia","message":"so nice to see sm1 not use AI to code 😊","platform":"youtube_account"}],"17931151713075151"]
	client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
	      .thenAccept(resp -> {
                  subscribed = false;
                  String response = resp.body();
                  JsonArray rootArray = new Gson().fromJson(response, JsonArray.class);
                  JsonArray chatArray = rootArray.get(0).getAsJsonArray();
                  String tt = rootArray.get(1).getAsString();
                  timetoken = tt;

                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.info("Response: {}", response);

                  Minecraft mc = Minecraft.getInstance();
                  mc.execute(() -> {
                      if (mc.player == null) return;
                      for (JsonElement element : chatArray) {
                          JsonObject chatObject = element.getAsJsonObject();
                          String user = chatObject.get("user").getAsString();
                          String message = chatObject.get("message").getAsString();
                          String platform = chatObject.get("platform").getAsString();

                          // Check for player commands
                          youtubeTwitchCommands(message);

                          // Run Slash Commands
                          slashCommands(mc, message);

                          // Action Commands
                          actionCommadns(mc, message);

                          // Colorize YouTube and Twitch chat
                          String color = playerColors.getOrDefault(user, "§c");
                          chatMessages.add(color + user + "§r: §6" + message);

                          // Only keep last 10 messages
                          if (chatMessages.size() >= 10) {
                              chatMessages.remove(0);
                          }

                          mc.player.sendSystemMessage(
                              Component.literal(user).withStyle(ChatFormatting.GOLD)
                              .append(Component.literal(": " + message).withStyle(ChatFormatting.WHITE))
                        );
                      }
                  });
	      })
	      .exceptionally(ex -> {
                  subscribed = false;
                  YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Request FAILED", ex);
                  return null;
	      });
    }

    private static final Set<String> SLASH_COMMANDS = Set.of(
        "/tp", "/weather", "/give",
        "/summon", "/add", "/gamemode",
        "/time", "/effect", 
        "/attribute", "/kill",
        "/seed", "/gamerule", "/particle"
        // "/fill", 
        // "/playsound",
        // "/worldborder"
        // "/setblock",
    );
    static void slashCommands(Minecraft mc, String message) {
        String command = message.trim().split(" ")[0].trim();
        if (!SLASH_COMMANDS.contains(command)) return;
        // Show message on screen is recoginzed by our filter
        mc.player.connection.sendChat(message.trim());
        // Send without slash command
        mc.player.connection.sendCommand(message.trim().substring(1));
    }

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        //LocalPlayer player = mc.player;
        if (!subscribed) subscribe();
        if (mc == null) return;

        for (Map.Entry<String, Integer> command : playerCommands.entrySet()) {
            String action = command.getKey();
            int frames = command.getValue() - 1;
            boolean direction = true;

            //YouTubeTwitchPlaysMinecraftwithAIDecisionModel.LOGGER.error("Command:" + action + "Frames:" + Integer.toString(frames));

            if (frames <= 0) {
                direction = false;
            }
            else {
                command.setValue(frames);
            } 
            KeyMapping key = switch (action) {
                case "perspective" -> mc.options.keyTogglePerspective;
                case "smooth"      -> mc.options.keySmoothCamera;
                case "inventory"   -> mc.options.keyInventory;
                case "attack"      -> mc.options.keyAttack;
                case "sprint"      -> mc.options.keySprint;
                case "jump"        -> mc.options.keyJump;
                case "crouch"      -> mc.options.keyShift;
                case "left"        -> mc.options.keyLeft;
                case "right"       -> mc.options.keyRight;
                case "forward"     -> mc.options.keyUp;
                case "backward"    -> mc.options.keyDown;
                default            -> null;
            };

            if (key != null) {
                key.setDown(direction);
            }
        }
    }

    private static List<String> COMMANDS = List.of(
        "sprint", "jump", "crouch", "right", "left",
        "forward", "backward", "attack", "stop"
    );
    static List parseArgs(String command, String message) {
        String regex = command + " +([a-z0-9]+),? +([a-z0-9]+),? +([a-z0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        List<String> args = new ArrayList<String>();
        while (matcher.find()) {
            args.add(matcher.group(1));
            args.add(matcher.group(2));
            args.add(matcher.group(3));
        }
        return args;
    }
    static void teleport(String command, String message) {
        Integer x = 100;
        Integer y = 100;
        Integer z = 100;
        List args = parseArgs(command, message);
        Minecraft mc = Minecraft.getInstance();

        if (mc != null) {
            mc.player.setPos(x, y, z);
        }
    }
    private static Map<String, Integer> playerCommands = new LinkedHashMap<>();
    static void youtubeTwitchCommands(String message) {
        String msg = message.toLowerCase();

        for (String cmd: COMMANDS) {
            if (msg.contains(cmd)) {
                // Allow for change of direction
                switch (cmd) {
                    case "forward"  -> playerCommands.put("backward", 0);
                    case "backward" -> playerCommands.put("forward", 0);
                    case "left"     -> playerCommands.put("right", 0);
                    case "right"    -> playerCommands.put("left", 0);
                }

                // Stop all movement actions
                if (cmd == "stop") {
                    for (String cmd2: COMMANDS) {
                        playerCommands.put(cmd2, 0);
                    }
                }

                Integer val = playerCommands.getOrDefault(cmd, 0);
                if (val > 0) {
                    playerCommands.put(cmd, 0);
                }
                else {
                    playerCommands.put(cmd, 10000000);
                }
            }
        }
    }

    @SubscribeEvent
    static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            Identifier.fromNamespaceAndPath(YouTubeTwitchPlaysMinecraftwithAIDecisionModel.MODID, "ai_timer"),
            (guiGraphics, deltaTracker) -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.gui.hud.isHidden() || mc.player == null) return;
                String label = "HELLO YOUTUBE and TWITCH Chat";
                int rightMargin = guiGraphics.guiWidth(); 
                int textWidth = mc.font.width(label);
                int xPosition = rightMargin - textWidth - 100;
                guiGraphics.text(mc.font, label, xPosition, 100, 0xFFFF2222, true);

                int xChatPos = 0;
                int yChatPos = 0;
                //List<String> chats = chatMessages.reversed();
                //§cQ§6U§eI§aC§bK§3F§dI§5X§r is the best
                for (String msg : chatMessages) {
                    guiGraphics.text(mc.font, msg, xChatPos, yChatPos, 0xFFFFFFFF, true);
                    yChatPos += 11;
                }
            }
        );
    }
}
